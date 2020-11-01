package org.layz.hx.core.support.schedule;

import org.layz.hx.core.pojo.response.JsonResponse;

public interface JobExecuteHandler {
    /**
     * 业务处理前执行逻辑
     */
    void onBefore();

    /**
     * 定时任务执行
     * @param param1
     * @param param2
     * @throws Exception
     * @return
     */
    JsonResponse doTask(String param1, String param2) throws Exception;

    /**
     * 业务处理后逻辑
     */
    void onAfter();
}
