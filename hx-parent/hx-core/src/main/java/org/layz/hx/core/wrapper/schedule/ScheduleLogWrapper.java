package org.layz.hx.core.wrapper.schedule;

import org.layz.hx.config.entity.schedule.ScheduleLog;

import java.util.List;

public interface ScheduleLogWrapper {
    /**
     * 更新批次号
     * ScheduleStatusEnum.WAITE_HANDLE
     *    or ScheduleStatusEnum.HANDLE_FAIL and failCount <= 5
     * @param processNo
     * @param scanTypeName
     * @param taskLoopCount
     */
    int updateProcessNo(String processNo, String scanTypeName, Integer taskLoopCount);

    /**
     * 更新下一个批次号
     * @param nextJobList
     */
    void updateNextJob(List<Long> nextJobList);
    /**
     * 查询定时任务执行的记录
     * @param processNo
     * @return
     */
    List<ScheduleLog> findByProcessNo(String processNo);

    /**
     * 更新
     * @param scheduleLog
     * @return
     */
    Integer update(ScheduleLog scheduleLog);

    /**
     * 批量新增
     * @param list
     */
    void updateBatch(List<ScheduleLog> list);

    /**
     * 保存记录
     * @param list
     */
    void persistBatch(List<ScheduleLog> list);

    /**
     * 批量保存记录
     * @param scheduleLog
     * @return
     */
    Integer persistEntity(ScheduleLog scheduleLog);
}
