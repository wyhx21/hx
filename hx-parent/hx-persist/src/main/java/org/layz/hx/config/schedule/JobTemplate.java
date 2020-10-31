package org.layz.hx.config.schedule;

import org.layz.hx.base.inte.ResponseEnum;
import org.layz.hx.base.type.ScheduleStatusEnum;
import org.layz.hx.config.entity.schedule.ScheduleLog;
import org.layz.hx.config.service.schedule.ScheduleLogService;
import org.layz.hx.core.pojo.response.JsonResponse;
import org.layz.hx.core.service.JobExecuteHandler;
import org.layz.hx.core.service.JobResultHandler;
import org.layz.hx.core.util.SnowFlakeUtil;
import org.layz.hx.spring.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public final class JobTemplate implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobTemplate.class);
    private static Integer SINGLE = 0;
    private ScheduleLogService scheduleLogService;
    private ThreadPoolTaskExecutor taskExecutor;
    private JobResultHandler jobResultHandler;
    /**
     * 0同步执行1异步执行
     */
    private Integer singleThread = 0;
    /**
     * 该批次处理的最大数据量 default 10
     */
    private Integer taskLoopCount = 10;
    /**
     * 扫描累
     */
    private String scanTypeName;

    public void setScheduleLogService(ScheduleLogService scheduleLogService) {
        this.scheduleLogService = scheduleLogService;
    }

    public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public void setScanTypeName(String scanTypeName) {
        this.scanTypeName = scanTypeName;
    }

    public void setSingleThread(Integer singleThread) {
        this.singleThread = singleThread;
    }

    public void setTaskLoopCount(Integer taskLoopCount) {
        this.taskLoopCount = taskLoopCount;
    }

    public void setJobResultHandler(JobResultHandler jobResultHandler) {
        this.jobResultHandler = jobResultHandler;
    }

    @Override
    public final void run() {
        long begin = System.currentTimeMillis();
        try {
            LOGGER.debug("task execute begin...");
            execute();
            LOGGER.debug("task execute end, execute time: {}...",System.currentTimeMillis() - begin);
        } catch (Exception e) {
            LOGGER.error("task execute error, execute time: {}...",(System.currentTimeMillis() - begin), e);
        }
    }

    protected void execute(){
        // 扫描系统任务表中是否存在待处理类型的任务数据
        int dataCount = scheduleLogService.findCountByName(scanTypeName);
        LOGGER.debug("dataCount: {}", dataCount);
        if (0 == dataCount) {
            LOGGER.debug("#### scan[{}],no record ####", scanTypeName);
            return;
        }
        if(SINGLE.equals(singleThread)) {
            executeSingle();
        } else {
            executeSync();
        }
    }

    /**
     * 多线程执行
     */
    private final void executeSync() {
        LOGGER.debug("executeSync start");
        try {
            List<ScheduleLog> processList = obtainTaskList();
            if(null == processList || processList.isEmpty()) {
                LOGGER.debug("#### scan[{}],no record ####", scanTypeName);
                return;
            }
            for (ScheduleLog scheduleLog : processList) {
                JobRunnable runnable = new JobRunnable();
                runnable.setScheduleLogService(scheduleLogService);
                runnable.setScheduleLog(scheduleLog);
                runnable.setJobResultHandler(jobResultHandler);
                if(null == taskExecutor) {
                    LOGGER.debug("taskExecutor is not exsist, class: {}", ThreadPoolTaskExecutor.class);
                    return;
                }
                if (taskExecutor.getThreadPoolExecutor().getQueue().remainingCapacity() == 0) {
                    scheduleLog.setProcessNo(null);
                    scheduleLog.setEndRunTime(new Date());
                    scheduleLog.setStatus(ScheduleStatusEnum.WAITE_HANDLE.getValue());
                    scheduleLog.setLastModifiedDate(new Date());
                    scheduleLog.setLastModifiedDate(new Date());
                    scheduleLogService.updateBatch(Collections.singletonList(scheduleLog));
                    continue;
                } else {
                    taskExecutor.execute(runnable);
                }
            }
        } catch (Throwable e) {
            LOGGER.error("#### systemTask handle error [{}] ####", scanTypeName, e);
        }
    }

    /**
     * 单线程执行
     */
    private final void executeSingle() {
        LOGGER.debug("excuteSingleJob start");
        try {
            List<ScheduleLog> processList = obtainTaskList();
            if(null == processList || processList.isEmpty()) {
                LOGGER.debug("#### scan[{}],no record ####", scanTypeName);
                return;
            }
            List<ScheduleLog> errorList = new ArrayList<>(); // 用来保存生成文件时失败的数据
            List<ScheduleLog> successList = new ArrayList<>(); // 保持成功生成的数据
            List<Long> nextTaskList = new ArrayList<>(); // 保持下一个处理
            String taskServiceName; // 任务处理类名称
            for (ScheduleLog scheduleLog : processList) {
                JobExecuteHandler jobExecuteHandler = null;
                try {
                    // 获取具体要处理的业务类
                    taskServiceName = scheduleLog.getJobService();
                    scheduleLog.setStartRunTime(new Date());
                    // 根据处理类获取具体业务实现类
                    jobExecuteHandler = SpringContextUtil.getBean(taskServiceName);
                    // 业务处理前
                    jobExecuteHandler.onBefore();
                    // 具体业务调用的逻辑处理
                    JsonResponse response = jobExecuteHandler.doTask(scheduleLog.getParam1(), scheduleLog.getParam2());
                    if (ResponseEnum.SUCC.equals(response.getSuccess())) {
                        jobResultHandler.jobSuccHandle(scheduleLog, response);
                        successList.add(scheduleLog);
                        nextTaskList.add(scheduleLog.getId());
                    } else {
                        jobResultHandler.jobFailHandle(scheduleLog, response);
                        errorList.add(scheduleLog);
                    }
                } catch (Throwable e) {
                    LOGGER.error("run error, serviceName", scheduleLog.getJobService(), e);
                    jobResultHandler.jobErrorHandle(scheduleLog, e);
                    errorList.add(scheduleLog);
                } finally {
                    if (null != jobExecuteHandler) {
                        jobExecuteHandler.onAfter();
                    }
                }
            }
            // 如果批次数据中存在处理异常的数据
            if (null != errorList && !errorList.isEmpty()) {
                scheduleLogService.updateBatch(errorList);
            }
            // 处理成功的数据
            if (null != successList && !successList.isEmpty()) {
                scheduleLogService.updateBatch(successList);
            }
            if(null != nextTaskList && !nextTaskList.isEmpty()) {
                scheduleLogService.updateNextJob(nextTaskList);
            }
        } catch (Throwable e) {
            LOGGER.error("#### systemTask handle error [{}] ####", scanTypeName, e);
        }
    }

    /**
     * 获取任务执行列表
     * @return
     */
    private List<ScheduleLog> obtainTaskList(){
        // 生成批次号,具体规则待定...
        String processNo = scanTypeName + "_" + SnowFlakeUtil.getSnowFlake().nextId();
        // 更新批次号、任务运行时间、和任务状态
        int count = scheduleLogService.updateProcessNo(processNo, scanTypeName, taskLoopCount);
        LOGGER.debug("update count: {}", count);
        // 重新根据批次号获取实际待处理的数据
        List<ScheduleLog> processList = scheduleLogService.findByProcessNo(processNo);
        return processList;
    }
}
