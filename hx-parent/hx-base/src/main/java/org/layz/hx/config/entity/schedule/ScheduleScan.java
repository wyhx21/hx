package org.layz.hx.config.entity.schedule;

import org.layz.hx.base.annotation.HxColumn;
import org.layz.hx.base.annotation.HxTable;
import org.layz.hx.base.entity.ScheduleEntity;

@HxTable(value = "schedule_scan", definition = "COMMENT='定时任务扫描配置'")
public class ScheduleScan extends ScheduleEntity {
    @HxColumn(sort = 3,definition = "int(8) COMMENT '每次扫描日志条数'")
    private Integer taskLoopCount;

    public Integer getTaskLoopCount() {
        return taskLoopCount;
    }

    public void setTaskLoopCount(Integer taskLoopCount) {
        this.taskLoopCount = taskLoopCount;
    }
}
