package org.layz.hx.base.entity;

import org.layz.hx.base.annotation.HxColumn;
import org.layz.hx.base.annotation.HxSupperClass;

import java.util.Date;

@HxSupperClass
public class BaseJobEntity extends AutoLongBaseEntity {
    @HxColumn(sort = 1,definition = "bigint(19) COMMENT '父级id'")
    private Long parentJobId;
    @HxColumn(sort = 2,definition = "varchar(128)COMMENT '批次号'")
    private String processNo;
    @HxColumn(sort = 3,definition = "varchar(32) COMMENT '扫描类'")
    private String scanTypeName;
    @HxColumn(sort = 4,definition = "varchar(32) COMMENT '执行类'")
    private String jobService;
    @HxColumn(sort = 5,definition = "tinyint(2) DEFAULT NULL COMMENT 'JobStatusEnum 状态 0:待启动任务,1:待处理,2:处理中,3:处理失败,4:处理成功'")
    private Integer status;
    @HxColumn(sort = 6,definition = "tinyint(2) DEFAULT '0' COMMENT '失败次数'")
    private Integer failCount;
    @HxColumn(sort = 7,definition = "datetime COMMENT '开始执行时间'")
    private Date beginRunTime;
    @HxColumn(sort = 8,definition = "datetime COMMENT '运行开始时间'")
    private Date startRunTime;
    @HxColumn(sort = 9,definition = "datetime COMMENT '运行结束时间'")
    private Date endRunTime;
    @HxColumn(sort = 10,definition = "varchar(2048) COMMENT '执行结果'")
    private String handleResult;

    public Long getParentJobId() {
        return parentJobId;
    }

    public void setParentJobId(Long parentJobId) {
        this.parentJobId = parentJobId;
    }

    public String getProcessNo() {
        return processNo;
    }

    public void setProcessNo(String processNo) {
        this.processNo = processNo;
    }

    public String getScanTypeName() {
        return scanTypeName;
    }

    public void setScanTypeName(String scanTypeName) {
        this.scanTypeName = scanTypeName;
    }

    public String getJobService() {
        return jobService;
    }

    public void setJobService(String jobService) {
        this.jobService = jobService;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getFailCount() {
        return failCount;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    public Date getBeginRunTime() {
        return beginRunTime;
    }

    public void setBeginRunTime(Date beginRunTime) {
        this.beginRunTime = beginRunTime;
    }

    public Date getStartRunTime() {
        return startRunTime;
    }

    public void setStartRunTime(Date startRunTime) {
        this.startRunTime = startRunTime;
    }

    public Date getEndRunTime() {
        return endRunTime;
    }

    public void setEndRunTime(Date endRunTime) {
        this.endRunTime = endRunTime;
    }

    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }
}
