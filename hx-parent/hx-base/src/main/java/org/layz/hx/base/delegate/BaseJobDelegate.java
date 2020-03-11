package org.layz.hx.base.delegate;

import org.layz.hx.base.entity.BaseJobEntity;

import java.util.List;

public interface BaseJobDelegate<T extends BaseJobEntity> {
    /**
     * 扫描系统任务表中是否存在待处理类型的任务数据
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
     * 查询定时任务执行的记录
     * @param processNo
     * @return
     */
    List<T> findByProcessNo(String processNo);

    /**
     * 批量更新
     * @param list
     */
    void updateBatch(List<T> list);
    /**
     * 更新下一批
     * @param nextJobList
     */
    void updateNextJob(List<Long> nextJobList);
}
