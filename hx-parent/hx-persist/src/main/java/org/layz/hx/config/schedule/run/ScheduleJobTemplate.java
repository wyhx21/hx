package org.layz.hx.config.schedule.run;

import org.layz.hx.base.inte.ResponseEnum;
import org.layz.hx.base.type.ScheduleStatusEnum;
import org.layz.hx.config.entity.schedule.ScheduleLog;
import org.layz.hx.core.pojo.response.JsonResponse;
import org.layz.hx.core.support.schedule.JobExecuteHandler;
import org.layz.hx.core.support.schedule.JobResultHandler;
import org.layz.hx.core.wrapper.schedule.ScheduleLogWrapper;
import org.layz.hx.spring.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public abstract class ScheduleJobTemplate implements Runnable{
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleJobTemplate.class);
    private static Integer SINGLE = 0;
    private ThreadPoolTaskExecutor taskExecutor;
    private JobResultHandler jobResultHandler;
    private Integer singleThread = 0;
    protected ScheduleLogWrapper scheduleLogWrapper;
    protected Long lastModifiedBy;
    /**
     * 扫描类
     */
    protected String scanTypeName;

    public void setScheduleLogWrapper(ScheduleLogWrapper scheduleLogWrapper) {
        this.scheduleLogWrapper = scheduleLogWrapper;
    }

    public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public void setJobResultHandler(JobResultHandler jobResultHandler) {
        this.jobResultHandler = jobResultHandler;
    }

    public void setSingleThread(Integer singleThread) {
        this.singleThread = singleThread;
    }

    public void setScanTypeName(String scanTypeName) {
        this.scanTypeName = scanTypeName;
    }

    public void setLastModifiedBy(Long lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public final void run() {
        long begin = System.currentTimeMillis();
        try {
            LOGGER.debug("task execute begin...");
            List<ScheduleLog> list = onBefore();
            if(null == list || list.isEmpty()) {
                return;
            }
            if(SINGLE.equals(singleThread)) {
                executeSynchronized(list);
            } else {
                executeAsync(list);
            }
            LOGGER.debug("task execute end, execute time: {}...",System.currentTimeMillis() - begin);
        } catch (Exception e) {
            LOGGER.error("task execute error, execute time: {}...",(System.currentTimeMillis() - begin), e);
        }
    }

    /**
     *异步执行
     */
    private void executeAsync(List<ScheduleLog> list) {
        LOGGER.debug("executeAsync start");
        for (ScheduleLog scheduleLog : list) {
            scheduleLog.setLastModifiedBy(lastModifiedBy);
            JobRunnable runnable = new JobRunnable();
            runnable.setScheduleLogWrapper(this.scheduleLogWrapper);
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
                this.scheduleLogWrapper.updateBatch(Collections.singletonList(scheduleLog));
                continue;
            } else {
                taskExecutor.execute(runnable);
            }
        }
    }

    /**
     * 同步执行
     */
    private void executeSynchronized(List<ScheduleLog> list) {
        LOGGER.debug("executeSynchronized start");
        List<ScheduleLog> errorList = new ArrayList<>(); // 用来保存生成文件时失败的数据
        List<ScheduleLog> successList = new ArrayList<>(); // 保持成功生成的数据
        List<Long> nextTaskList = new ArrayList<>(); // 保持下一个处理
        String serviceName; // 任务处理类名称
        for (ScheduleLog scheduleLog : list) {
            scheduleLog.setLastModifiedBy(lastModifiedBy);
            JobExecuteHandler jobExecuteHandler = null;
            serviceName = scheduleLog.getJobService();
            Long id = scheduleLog.getId();
            String param1 = scheduleLog.getParam1();
            String param2 = scheduleLog.getParam2();
            String remark = scheduleLog.getRemark();
            long begin = System.currentTimeMillis();
            try {
                // 获取具体要处理的业务类
                scheduleLog.setStartRunTime(new Date());
                // 根据处理类获取具体业务实现类
                jobExecuteHandler = SpringContextUtil.getBean(serviceName);
                LOGGER.info("{} execute begin, param1: {}: param2: {}, remark:{}, id:{}", serviceName, param1, param2, remark, id);
                // 业务处理前
                jobExecuteHandler.onBefore();
                // 具体业务调用的逻辑处理
                JsonResponse response = jobExecuteHandler.doTask(param1, param2);
                LOGGER.info("{} execute end, id:{}, time: {} ms...", serviceName, id, (System.currentTimeMillis() - begin));
                if (ResponseEnum.SUCC.equals(response.getSuccess())) {
                    jobResultHandler.jobSuccHandle(scheduleLog, response);
                    successList.add(scheduleLog);
                    nextTaskList.add(scheduleLog.getId());
                } else {
                    jobResultHandler.jobFailHandle(scheduleLog, response);
                    errorList.add(scheduleLog);
                }
            } catch (Throwable e) {
                LOGGER.error("{} execute error, id:{}, time: {} ms...", serviceName, id, (System.currentTimeMillis() - begin), e);
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
            this.scheduleLogWrapper.updateBatch(errorList);
        }
        // 处理成功的数据
        if (null != successList && !successList.isEmpty()) {
            this.scheduleLogWrapper.updateBatch(successList);
        }
        if(null != nextTaskList && !nextTaskList.isEmpty()) {
            this.scheduleLogWrapper.updateNextJob(nextTaskList);
        }
    }


    /**
     * 获取执行记录
     * @return
     */
    protected abstract List<ScheduleLog> onBefore();
}
