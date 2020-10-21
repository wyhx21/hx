package org.layz.hx.persist.repository;

import org.layz.hx.base.entity.BaseJobEntity;
import org.layz.hx.base.type.JobStatusEnum;
import org.layz.hx.core.support.HxTableSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

public class BaseJobDaoImpl<T extends BaseJobEntity> extends BaseDaoImpl<T> implements BaseJobDao<T>{
    private String tableName;
    private Integer failCount;
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseJobDaoImpl.class);

    @Override
    public int findCountByName(String scanTypeName, Date currentDate) {
        LOGGER.debug("scanTypeName:{}", scanTypeName);
        String sql = "select count(0) from " + tableName + " where scanTypeName = ? and (`status` = ? or (`status` = ? and failCount < ?)) and beginRunTime <= ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, scanTypeName, JobStatusEnum.WAITE_HANDLE.getValue(),
                JobStatusEnum.HANDLE_FAIL.getValue(), failCount,currentDate);
        return count;
    }

    @Override
    public int updateProcessNo(String processNo, String scanTypeName, Integer taskLoopCount, Date currentDate) {
        LOGGER.debug("scanTypeName: {},taskLoopCount: {},processNo: {}", scanTypeName, taskLoopCount, processNo);
        String sql = "update " + tableName + " set processNo = ?,`status` = ?,lastModifiedDate = ? where scanTypeName = ? and (`status` = ? or (`status` = ? and failCount < ?)) and beginRunTime <= ? LIMIT ?";
        return jdbcTemplate.update(sql, processNo, JobStatusEnum.HANDING.getValue(), currentDate, scanTypeName, JobStatusEnum.WAITE_HANDLE.getValue(),
                JobStatusEnum.HANDLE_FAIL.getValue(), failCount, currentDate, taskLoopCount);
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
            ps.setInt(1,JobStatusEnum.WAITE_START.getValue());
            ps.setDate(2, new java.sql.Date(System.currentTimeMillis()));
            ps.setLong(3, argument);
            ps.setInt(4, JobStatusEnum.WAITE_HANDLE.getValue());
        });
    }

    @Override
    public void setClazz(Class<T> clazz) {
        super.setClazz(clazz);
        this.tableName = HxTableSupport.getTableClassInfo(clazz).getTableName();
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }
}
