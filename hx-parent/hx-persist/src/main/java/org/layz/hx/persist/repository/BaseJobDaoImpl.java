package org.layz.hx.persist.repository;

import org.layz.hx.base.entity.BaseJobEntity;
import org.layz.hx.base.type.JobStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class BaseJobDaoImpl<T extends BaseJobEntity> extends BaseDaoImpl<T> implements BaseJobDao<T>{
    private String tableName;
    private Integer failCount;
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseJobDaoImpl.class);

    @Override
    public int findCountByName(String scanTypeName, Date currentDate) {
        LOGGER.debug("scanTypeName:{}", scanTypeName);
        String sql = "select count(0) from " + tableName + " where scanTypeName = ? and (`status` = ? or (`status` = ? and failCount < ?)) and beginRunTime <= ?";
        int count = getJdbcTemplate().queryForObject(sql, Integer.class, scanTypeName, JobStatusEnum.WAITE_HANDLE.getValue(),
                JobStatusEnum.HANDLE_FAIL.getValue(), failCount,currentDate);
        return count;
    }

    @Override
    public int updateProcessNo(String processNo, String scanTypeName, Integer taskLoopCount, Date currentDate) {
        LOGGER.debug("scanTypeName: {},taskLoopCount: {},processNo: {}", scanTypeName, taskLoopCount, processNo);
        String sql = "update " + tableName + " set processNo = ?,`status` = ?,lastModifiedDate = ? where scanTypeName = ? and (`status` = ? or (`status` = ? and failCount < ?)) and beginRunTime <= ? LIMIT ?";
        return getJdbcTemplate().update(sql, processNo, JobStatusEnum.HANDING.getValue(), currentDate, scanTypeName, JobStatusEnum.WAITE_HANDLE.getValue(),
                JobStatusEnum.HANDLE_FAIL.getValue(), failCount, currentDate, taskLoopCount);
    }

    @Override
    public int updateNextJob(Long parentJobId) {
        LOGGER.debug("parentJobId: {}", parentJobId);
        String sql = "update " + tableName + " set `status` = ?,lastModifiedDate = ? where parentJobId = ? and `status` = ?";
        return getJdbcTemplate().update(sql, JobStatusEnum.WAITE_START.getValue(), new Date(), parentJobId,JobStatusEnum.WAITE_HANDLE.getValue());
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }
}
