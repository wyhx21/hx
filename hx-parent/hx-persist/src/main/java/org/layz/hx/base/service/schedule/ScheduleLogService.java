package org.layz.hx.base.service.schedule;

import org.layz.hx.base.entity.schedule.ScheduleLog;
import org.layz.hx.persist.service.BaseService;

import java.util.List;

public interface ScheduleLogService extends BaseService<ScheduleLog> {
    /**
     * 1: 扫描类为 scanTypeName
     * 2：开始执行时间小于当前时间
     * 3：执行状态为 JobStatusEnum.WAITE_HANDLE
     *    or JobStatusEnum.HANDLE_FAIL and failCount <= 5
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
}