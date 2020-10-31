package org.layz.hx.config.entity.schedule;

import org.layz.hx.base.annotation.HxColumn;
import org.layz.hx.base.annotation.HxTable;
import org.layz.hx.base.entity.AutoLongBaseEntity;

@HxTable(value = "schedule_scan", definition = "COMMENT='定时任务扫描配置'")
public class ScheduleScan extends AutoLongBaseEntity {
    @HxColumn(sort = 1,definition = "varchar(32) COMMENT '扫描字段'")
    private String scanTypeName;
    @HxColumn(sort = 2,definition = "varchar(32) COMMENT '表达式'")
    private String cron;
    @HxColumn(sort = 3,definition = "int(8) COMMENT '每次扫描日志条数'")
    private Integer taskLoopCount;
    @HxColumn(sort = 4,definition = "tinyint(2) COMMENT '0同步执行1异步执行'")
    private Integer single;
    @HxColumn(sort = 5,definition = "varchar(64) COMMENT '备注'")
    private String remark;

    public String getScanTypeName() {
        return scanTypeName;
    }

    public void setScanTypeName(String scanTypeName) {
        this.scanTypeName = scanTypeName;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public Integer getTaskLoopCount() {
        return taskLoopCount;
    }

    public void setTaskLoopCount(Integer taskLoopCount) {
        this.taskLoopCount = taskLoopCount;
    }

    public Integer getSingle() {
        return single;
    }

    public void setSingle(Integer single) {
        this.single = single;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
