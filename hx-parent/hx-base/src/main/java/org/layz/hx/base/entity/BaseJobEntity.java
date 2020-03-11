package org.layz.hx.base.entity;

import org.layz.hx.base.annotation.HxColumn;
import org.layz.hx.base.annotation.HxSupperClass;

import java.util.Date;

@HxSupperClass
public class BaseJobEntity extends AutoLongBaseEntity {
    @HxColumn(sort = 1)
    private Long parentJobId;
    @HxColumn(sort = 2)
    private String processNo;
    @HxColumn(sort = 3)
    private String scanTypeName;
    @HxColumn(sort = 4)
    private String jobService;
    /**
     * 0:待启动任务,1:待处理,2:处理中,3:处理失败,4:处理成功
     */
    @HxColumn(sort = 5)
    private Integer status;
    @HxColumn(sort = 6)
    private Integer failCount;
    @HxColumn(sort = 7)
    private Date beginRunTime;
    @HxColumn(sort = 8)
    private Date startRunTime;
    @HxColumn(sort = 9)
    private Date endRunTime;
    @HxColumn(sort = 10)
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
