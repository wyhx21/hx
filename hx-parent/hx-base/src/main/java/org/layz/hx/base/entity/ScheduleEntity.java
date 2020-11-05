package org.layz.hx.base.entity;

import org.layz.hx.base.annotation.HxColumn;

public class ScheduleEntity extends AutoLongBaseEntity {
    @HxColumn(sort = 1,definition = "varchar(32) COMMENT '扫描字段,用于失败重复执行(需配置schedule_scan)'")
    private String scanTypeName;
    @HxColumn(sort = 2,definition = "varchar(32) COMMENT '表达式'")
    private String cron;
    @HxColumn(sort = 3,definition = "tinyint(2) COMMENT '1同步执行0异步执行'")
    private Integer single;
    @HxColumn(sort = 99,definition = "varchar(64) COMMENT '备注'")
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
