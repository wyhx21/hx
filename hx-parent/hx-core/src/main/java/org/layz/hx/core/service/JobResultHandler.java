package org.layz.hx.core.service;

import org.layz.hx.base.entity.BaseJobEntity;
import org.layz.hx.core.pojo.response.JsonResponse;

public interface JobResultHandler<T extends BaseJobEntity> {
    /**
     * @param entity
     * @param result
     */
    void jobSuccHandle(T entity,JsonResponse result);

    /**
     * @param entity
     * @param result
     */
    void jobFailHandle(T entity,Object result);

    /**
     *
     * @param entity
     * @param e
     */
    void jobErrorHandle(T entity, Throwable e);
}
