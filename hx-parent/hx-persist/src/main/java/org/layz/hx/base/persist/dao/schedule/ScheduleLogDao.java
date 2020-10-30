package org.layz.hx.base.persist.dao.schedule;

import org.layz.hx.base.entity.schedule.ScheduleLog;
import org.layz.hx.persist.repository.BaseDao;

import java.util.Date;
import java.util.List;

public interface ScheduleLogDao extends BaseDao<ScheduleLog> {
    /**
     * 执行记录查询
     * @param scanTypeName
     * @param currentDate
     * @return
     */
    int findCountByName(String scanTypeName, Date currentDate);

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