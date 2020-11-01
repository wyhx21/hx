package org.layz.hx.config.service.schedule;

import org.layz.hx.config.entity.schedule.ScheduleLog;
import org.layz.hx.core.wrapper.schedule.ScheduleLogWrapper;
import org.layz.hx.persist.service.BaseService;

import java.util.List;

public interface ScheduleLogService extends BaseService<ScheduleLog>, ScheduleLogWrapper {
    /**
     * 更新
     * @param scheduleLog
     * @return
     */
    @Override
    Integer update(ScheduleLog scheduleLog);

    /**
     * 批量更新
     * @param list
     */
    @Override
    void updateBatch(List<ScheduleLog> list);

    @Override
    void persistBatch(List<ScheduleLog> list);

    @Override
    Integer persistEntity(ScheduleLog scheduleLog);
}