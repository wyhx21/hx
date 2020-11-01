package org.layz.hx.config.persist.dao.schedule;

import org.layz.hx.config.entity.schedule.ScheduleLog;
import org.layz.hx.persist.repository.BaseDao;

import java.util.Date;
import java.util.List;

public interface ScheduleLogDao extends BaseDao<ScheduleLog> {
    /**
     * 更新批次号
     * @param processNo
     * @param scanTypeName
     * @param taskLoopCount
     * @param currentDate
     */
    int updateProcessNo(String processNo, String scanTypeName, Integer taskLoopCount, Date currentDate);

    /**
     * 更新下一个批次
     * @param nextJobList
     */
    void updateNextJob(List<Long> nextJobList);
}