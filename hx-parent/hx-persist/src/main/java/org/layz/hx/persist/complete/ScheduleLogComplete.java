package org.layz.hx.persist.complete;

import org.layz.hx.base.inte.entity.Complete;
import org.layz.hx.base.type.JobStatusEnum;
import org.layz.hx.config.entity.schedule.ScheduleLog;

import java.util.Date;

public class ScheduleLogComplete implements Complete {
    @Override
    public boolean support(Object obj) {
        return ScheduleLog.class.isInstance(obj);
    }

    @Override
    public void complete(Object obj) {
        ScheduleLog entity = (ScheduleLog) obj;
        Integer status = entity.getStatus();
        if(null == status) {
            entity.setStatus(JobStatusEnum.WAITE_HANDLE.getValue());
        }
        Integer failCount = entity.getFailCount();
        if(null == failCount) {
            entity.setFailCount(0);
        }
        Date beginRunTime = entity.getBeginRunTime();
        if(null == beginRunTime) {
            entity.setBeginRunTime(new Date());
        }
    }
}
