package org.layz.hx.spring.config;

import org.layz.hx.base.delegate.BaseJobDelegate;
import org.layz.hx.base.entity.BaseJobEntity;
import org.layz.hx.base.inte.ResponseEnum;
import org.layz.hx.core.pojo.response.JsonResponse;
import org.layz.hx.core.service.JobExcuteHandler;
import org.layz.hx.core.service.JobResultHandler;
import org.layz.hx.spring.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Date;

public class JobRunnable<T extends BaseJobEntity> implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobRunnable.class);
    private BaseJobDelegate<T> baseJobDelegate;
    private T jobEntity;
    private JobResultHandler jobResultHandler;

    @Override
    public void run() {
        String serviceName = jobEntity.getJobService();
        JobExcuteHandler<T> jobService = SpringContextUtil.getBean(serviceName);
        try {
            jobEntity.setStartRunTime(new Date());
            jobService.onBefore(jobEntity);

            JsonResponse response = jobService.doTask(jobEntity);
            String msg = MessageFormat.format("code:{0},msg: {1}", response.getRespCode(), response.getRespMsg());
            if (ResponseEnum.SUCC.equals(response.getSuccess())) {
                jobResultHandler.jobSuccHandle(jobEntity, response);
                baseJobDelegate.updateBatch(Collections.singletonList(jobEntity));
            } else {
                jobResultHandler.jobFailHandle(jobEntity,response);
                baseJobDelegate.updateBatch(Collections.singletonList(jobEntity));
            }
            baseJobDelegate.updateNextJob(Collections.singletonList(jobEntity.getId()));
        } catch (Exception e) {
            LOGGER.error("run error, serviceName", serviceName, e);
            jobResultHandler.jobErrorHandle(jobEntity,e);
            baseJobDelegate.updateBatch(Collections.singletonList(jobEntity));
        } finally {
            jobService.onAfter();
        }
    }

    public void setBaseJobDelegate(BaseJobDelegate<T> baseJobDelegate) {
        this.baseJobDelegate = baseJobDelegate;
    }

    public void setJobEntity(T jobEntity) {
        this.jobEntity = jobEntity;
    }

    public void setJobResultHandler(JobResultHandler jobResultHandler) {
        this.jobResultHandler = jobResultHandler;
    }
}
