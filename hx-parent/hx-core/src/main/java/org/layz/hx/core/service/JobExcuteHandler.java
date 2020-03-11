package org.layz.hx.core.service;

import org.layz.hx.base.entity.BaseJobEntity;
import org.layz.hx.core.pojo.response.JsonResponse;

public interface JobExcuteHandler<T extends BaseJobEntity> {
    /**
     * 业务处理前执行逻辑
     * @param entity
     */
    void onBefore(T entity);

    /**
     * 定时任务执行
     * @param entity
     * @return
     */
    JsonResponse doTask(T entity);

    /**
     * 业务处理后逻辑
     */
    void onAfter();
}
