package org.layz.hx.core.wrapper.schedule;

import org.layz.hx.config.entity.schedule.ScheduleLog;

import java.util.List;

public interface ScheduleLogWrapper {
    /**
     * 1: 扫描类为 scanTypeName
     * 2：开始执行时间小于当前时间
     * 3：执行状态为 ScheduleStatusEnum.WAITE_HANDLE
     *    or ScheduleStatusEnum.HANDLE_FAIL and failCount <= 5
     * @param scanTypeName
     * @return
     */
    int findCountByName(String scanTypeName);

    /**
     * 更新批次号
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
}
