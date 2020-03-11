package org.layz.hx.persist.repository;

import org.layz.hx.base.entity.BaseJobEntity;

import java.util.Date;

public interface BaseJobDao<T extends BaseJobEntity> extends BaseDao<T> {
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
     * @param parentJobId
     */
    int updateNextJob(Long parentJobId);
}
