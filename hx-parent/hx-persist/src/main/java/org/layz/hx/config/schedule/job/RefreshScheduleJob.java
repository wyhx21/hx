package org.layz.hx.config.schedule.job;

import org.layz.hx.base.util.StringUtil;
import org.layz.hx.config.schedule.ScheduleCronConfig;
import org.layz.hx.config.schedule.ScheduleScanConfig;
import org.layz.hx.core.pojo.response.JsonResponse;
import org.layz.hx.core.support.schedule.AbstractJobExecuteHandler;
import org.layz.hx.core.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class RefreshScheduleJob extends AbstractJobExecuteHandler {
    @Autowired
    private ScheduleCronConfig scheduleCronConfig;
    @Autowired
    private ScheduleScanConfig scheduleScanConfig;
    @Override
    public JsonResponse doTask(String param1, String param2) throws Exception {
        if(StringUtil.isNotBlank(param1)) {
            scheduleCronConfig.refreshConfig();
        }
        if(StringUtil.isNotBlank(param2)) {
            scheduleScanConfig.refreshConfig();
        }
        return ResponseUtil.getSuccessResponse();
    }
}
