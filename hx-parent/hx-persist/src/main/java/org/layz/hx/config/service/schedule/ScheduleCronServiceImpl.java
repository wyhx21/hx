package org.layz.hx.config.service.schedule;

import org.layz.hx.config.entity.schedule.ScheduleCron;
import org.layz.hx.config.persist.dao.schedule.ScheduleCronDao;
import org.layz.hx.persist.repository.BaseDao;
import org.layz.hx.persist.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ScheduleCronServiceImpl extends BaseServiceImpl<ScheduleCron> implements ScheduleCronService  {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleCronServiceImpl.class);

    @Autowired
    private ScheduleCronDao scheduleCronDao;

    @Override
    public BaseDao<ScheduleCron> getBaseDao() {
        return scheduleCronDao;
    }
}