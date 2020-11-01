package org.layz.hx.config.persist.dao.schedule;

import org.layz.hx.base.type.ScheduleStatusEnum;
import org.layz.hx.config.entity.schedule.ScheduleLog;
import org.layz.hx.core.support.HxTableSupport;
import org.layz.hx.persist.repository.BaseDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

public class ScheduleLogDaoImpl extends BaseDaoImpl<ScheduleLog> implements ScheduleLogDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleLogDaoImpl.class);
    private String tableName;
    @Value("${hx.schedule.log.fail.count:5}")
    private Integer failCount;

    @Override
    public int updateProcessNo(String processNo, String scanTypeName, Integer taskLoopCount, Date currentDate) {
        LOGGER.debug("scanTypeName: {},taskLoopCount: {},processNo: {}", scanTypeName, taskLoopCount, processNo);
        String sql = "update " + tableName + " set processNo = ?,`status` = ?,lastModifiedDate = ? where scanTypeName = ? and (`status` = ? or (`status` = ? and failCount < ?)) and beginRunTime <= ? LIMIT ?";
        return jdbcTemplate.update(sql, processNo, ScheduleStatusEnum.HANDING.getValue(), currentDate, scanTypeName, ScheduleStatusEnum.WAITE_HANDLE.getValue(),
                ScheduleStatusEnum.HANDLE_FAIL.getValue(), failCount, currentDate, taskLoopCount);
    }

    @Override
    public void updateNextJob(List<Long> nextJobList) {
        LOGGER.debug("updateNextJob");
        if(CollectionUtils.isEmpty(nextJobList)) {
            LOGGER.info("nextJobIdList is empty");
            return;
        }
        String sql = "update " + tableName + " set `status` = ?,lastModifiedDate = ? where parentJobId = ? and `status` = ?";
        jdbcTemplate.batchUpdate(sql, nextJobList,super.batchSize, (ps, argument) -> {
            ps.setInt(1,ScheduleStatusEnum.WAITE_START.getValue());
            ps.setDate(2, new java.sql.Date(System.currentTimeMillis()));
            ps.setLong(3, argument);
            ps.setInt(4, ScheduleStatusEnum.WAITE_HANDLE.getValue());
        });
    }

    @Override
    public void setClazz(Class<ScheduleLog> clazz) {
        super.setClazz(clazz);
        this.tableName = HxTableSupport.getTableClassInfo(clazz).getTableName();
    }
}