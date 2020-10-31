package org.layz.hx.config.service.schedule;

import org.layz.hx.config.entity.schedule.ScheduleScan;
import org.layz.hx.config.persist.dao.schedule.ScheduleScanDao;
import org.layz.hx.persist.repository.BaseDao;
import org.layz.hx.persist.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ScheduleScanServiceImpl extends BaseServiceImpl<ScheduleScan> implements ScheduleScanService  {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleScanServiceImpl.class);

    @Autowired
    private ScheduleScanDao scheduleScanDao;

    @Override
    public BaseDao<ScheduleScan> getBaseDao() {
        return scheduleScanDao;
    }
}