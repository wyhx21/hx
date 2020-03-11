package org.layz.hx.spring.config;

import org.layz.hx.base.delegate.BaseJobDelegate;
import org.layz.hx.base.entity.BaseJobEntity;
import org.layz.hx.base.inte.ResponseEnum;
import org.layz.hx.base.type.JobStatusEnum;
import org.layz.hx.core.pojo.response.JsonResponse;
import org.layz.hx.core.service.JobExcuteHandler;
import org.layz.hx.core.service.JobResultHandler;
import org.layz.hx.core.service.JobResultHandlerImpl;
import org.layz.hx.core.util.SnowFlakeUtil;
import org.layz.hx.spring.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public abstract class JobTemplate<T extends BaseJobEntity> extends HxSchedulingConfigurer {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobTemplate.class);
    @Autowired
    @Qualifier("jobDelegate")
    private BaseJobDelegate<T> baseJobDelegate;
    @Autowired(required = false)
    @Qualifier("taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    private JobResultHandler jobResultHandler;
    /**
     * true 单线程执行 false 多线程执行
     */
    private boolean singleThread;
    /**
     * 该批次处理的最大数据量
     */
    private Integer taskLoopCount;
    /**
     * 扫描累
     */
    private String scanTypeName;

    public void setScanTypeName(String scanTypeName) {
        this.scanTypeName = scanTypeName;
    }

    public void setSingleThread(boolean singleThread) {
        this.singleThread = singleThread;
    }

    public void setTaskLoopCount(Integer taskLoopCount) {
        this.taskLoopCount = taskLoopCount;
    }

    public void setJobResultHandler(JobResultHandler<T> jobResultHandler) {
        this.jobResultHandler = jobResultHandler;
    }

    @Override
    public void excute(){
        if(null == jobResultHandler) {
            jobResultHandler = new JobResultHandlerImpl();
        }
        // 扫描系统任务表中是否存在待处理类型的任务数据
        int dataCount = baseJobDelegate.findCountByName(scanTypeName);
        LOGGER.debug("dataCount: {}", dataCount);
        if (0 == dataCount) {
            LOGGER.debug("#### scan[{}],no record ####", scanTypeName);
            return;
        }
        if(singleThread) {
            excuteSingleJob();
        } else {
            excuteThreadJob();
        }
    }

    /**
     * 多线程执行
     */
    private void excuteThreadJob() {
        LOGGER.debug("excuteThreadJob start");
        try {
            List<T> processList = obtainTaskList();
            if(null == processList || processList.isEmpty()) {
                LOGGER.debug("#### scan[{}],no record ####", scanTypeName);
                return;
            }
            for (T entity : processList) {
                JobRunnable<T> runnable = new JobRunnable();
                runnable.setBaseJobDelegate(baseJobDelegate);
                runnable.setJobEntity(entity);
                runnable.setJobResultHandler(jobResultHandler);
                if(null == taskExecutor) {
                    LOGGER.debug("taskExecutor is not exsist, class: {}", ThreadPoolTaskExecutor.class);
                    return;
                }
                if (taskExecutor.getThreadPoolExecutor().getQueue().remainingCapacity() == 0) {
                    entity.setProcessNo(null);
                    entity.setEndRunTime(new Date());
                    entity.setStatus(JobStatusEnum.WAITE_HANDLE.getValue());
                    entity.setLastModifiedDate(new Date());
                    entity.setLastModifiedDate(new Date());
                    baseJobDelegate.updateBatch(Collections.singletonList(entity));
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
    private void excuteSingleJob() {
        LOGGER.debug("excuteSingleJob start");
        try {
            List<T> processList = obtainTaskList();
            if(null == processList || processList.isEmpty()) {
                LOGGER.debug("#### scan[{}],no record ####", scanTypeName);
                return;
            }
            List<T> errorList = new ArrayList<>(); // 用来保存生成文件时失败的数据
            List<T> successList = new ArrayList<>(); // 保持成功生成的数据
            List<Long> nextTaskList = new ArrayList<>(); // 保持下一个处理
            String taskServiceName; // 任务处理类名称
            for (T entity : processList) {
                JobExcuteHandler<T> jobService = null;
                try {
                    // 获取具体要处理的业务类
                    taskServiceName = entity.getJobService();
                    entity.setStartRunTime(new Date());
                    // 根据处理类获取具体业务实现类
                    jobService = SpringContextUtil.getBean(taskServiceName);
                    // 业务处理前
                    jobService.onBefore(entity);
                    // 具体业务调用的逻辑处理
                    JsonResponse response = jobService.doTask(entity);
                    if (ResponseEnum.SUCC.equals(response.getSuccess())) {
                        jobResultHandler.jobSuccHandle(entity, response);
                        successList.add(entity);
                        nextTaskList.add(entity.getId());
                    } else {
                        jobResultHandler.jobFailHandle(entity, response);
                        errorList.add(entity);
                    }
                } catch (Throwable e) {
                    LOGGER.error("run error, serviceName", entity.getJobService(), e);
                    jobResultHandler.jobErrorHandle(entity, e);
                    errorList.add(entity);
                } finally {
                    if (null != jobService) {
                        jobService.onAfter();
                    }
                }
            }
            // 如果批次数据中存在处理异常的数据
            if (null != errorList && !errorList.isEmpty()) {
                baseJobDelegate.updateBatch(errorList);
            }
            // 处理成功的数据
            if (null != successList && !successList.isEmpty()) {
                baseJobDelegate.updateBatch(successList);
            }
            if(null != nextTaskList && !nextTaskList.isEmpty()) {
                baseJobDelegate.updateNextJob(nextTaskList);
            }
        } catch (Throwable e) {
            LOGGER.error("#### systemTask handle error [{}] ####", scanTypeName, e);
        }
    }

    /**
     * 获取任务执行列表
     * @return
     */
    private List<T> obtainTaskList(){
        // 生成批次号,具体规则待定...
        String processNo = scanTypeName + "_" + SnowFlakeUtil.getSnowFlake().nextId();
        // 更新批次号、任务运行时间、和任务状态
        int count = baseJobDelegate.updateProcessNo(processNo, scanTypeName, taskLoopCount);
        LOGGER.debug("update count: {}", count);
        // 重新根据批次号获取实际待处理的数据
        List<T> processList = baseJobDelegate.findByProcessNo(processNo);
        return processList;
    }
}
