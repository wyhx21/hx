package org.layz.hx.config.service.schedule;

import org.layz.hx.base.util.CreateCodeUtil;
import org.layz.hx.config.entity.schedule.ScheduleLog;
import org.layz.hx.config.entity.schedule.ScheduleScan;
import org.layz.hx.config.persist.dao.schedule.ScheduleLogDao;
import org.layz.hx.persist.repository.BaseDao;
import org.layz.hx.persist.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ScheduleLogServiceImpl extends BaseServiceImpl<ScheduleLog> implements ScheduleLogService  {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleLogServiceImpl.class);

    @Autowired
    private ScheduleLogDao scheduleLogDao;

    @Override
    public BaseDao<ScheduleLog> getBaseDao() {
        return scheduleLogDao;
    }

    @Override
    public int findCountByName(String scanTypeName) {
        LOGGER.debug("scanTypeName: {}", scanTypeName);
        int count = scheduleLogDao.findCountByName(scanTypeName,new Date());
        LOGGER.debug("count:{}", count);
        return count;
    }

    @Override
    @Transactional
    public int updateProcessNo(String processNo, String scanTypeName, Integer taskLoopCount) {
        LOGGER.debug("scanTypeName: {},taskLoopCount: {},processNo: {}", scanTypeName, taskLoopCount, processNo);
        int count = scheduleLogDao.updateProcessNo(processNo, scanTypeName, taskLoopCount, new Date());
        LOGGER.debug("count: {}", count);
        return count;
    }

    @Override
    @Transactional
    public void updateNextJob(List<Long> nextJobList) {
        if(null == nextJobList || nextJobList.isEmpty()) {
            return;
        }
        nextJobList = nextJobList.stream().filter(Objects::nonNull).collect(Collectors.toList());
        LOGGER.debug("size: {}", nextJobList.size());
        scheduleLogDao.updateNextJob(nextJobList);
    }

    @Override
    public List<ScheduleLog> findByProcessNo(String processNo) {
        LOGGER.debug("processLog: {}", processNo);
        ScheduleLog scheduleLog = new ScheduleLog();
        scheduleLog.setProcessNo(processNo);
        return this.findByEntity(scheduleLog);
    }
}