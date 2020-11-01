package org.layz.hx.config.schedule.run;

import org.layz.hx.config.entity.schedule.ScheduleLog;
import org.layz.hx.core.util.SnowFlakeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public final class ScheduleScanJobTemplate extends ScheduleJobTemplate {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleScanJobTemplate.class);
    /**
     * 该批次处理的最大数据量 default 10
     */
    private Integer taskLoopCount = 10;

    public void setTaskLoopCount(Integer taskLoopCount) {
        this.taskLoopCount = taskLoopCount;
    }

    @Override
    protected List<ScheduleLog> onBefore() {
        // 生成批次号,具体规则待定...
        String processNo = super.scanTypeName + "_" + SnowFlakeUtil.getSnowFlake().nextId();
        // 更新批次号、任务运行时间、和任务状态
        int count = super.scheduleLogWrapper.updateProcessNo(processNo, super.scanTypeName, this.taskLoopCount);
        LOGGER.debug("update count: {}", count);
        if(count < 1) {
            LOGGER.debug("#### scan[{}],no record ####", super.scanTypeName);
            return null;
        }
        // 重新根据批次号获取实际待处理的数据
        List<ScheduleLog> processList = super.scheduleLogWrapper.findByProcessNo(processNo);
        if(null == processList || processList.isEmpty()) {
            LOGGER.debug("#### scan[{}],no record ####", super.scanTypeName);
        }
        return processList;
    }

}