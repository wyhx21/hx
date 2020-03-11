package org.layz.hx.persist.service;

import org.layz.hx.base.pojo.Page;
import org.layz.hx.base.pojo.Pageable;
import org.layz.hx.persist.repository.BaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class BaseServiceImpl<T> implements BaseService<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseServiceImpl.class);

    public abstract BaseDao<T> getBaseDao();

    @Override
    @Transactional
    public Integer persistEntity(T t) {
        LOGGER.debug(">>> persistEntity <<<");
        return getBaseDao().persistEntity(t);
    }

    @Override
    @Transactional
    public Integer deleteByEntity(T t) {
        LOGGER.debug(">>> deleteByEntity <<<");
        return getBaseDao().deleteByEntity(t);
    }

    @Override
    @Transactional
    public Integer update(T t) {
        LOGGER.debug(">>> update <<<");
        return getBaseDao().update(t);
    }

    @Override
    @Transactional
    public Integer updateNotnull(T t) {
        LOGGER.debug(">>> updateNotnull <<<");
        return getBaseDao().updateNotnull(t);
    }

    @Override
    @Transactional
    public Integer deleteById(Long id) {
        LOGGER.debug(">>> deleteById, id: {} <<<", id);
        return getBaseDao().deleteById(id);
    }

    @Override
    public T findById(Long id) {
        LOGGER.debug(">>> findById, id: {} <<<", id);
        return getBaseDao().findById(id);
    }

    @Override
    public List<T> findByIds(List<Long> ids) {
        LOGGER.debug(">>> findByIds, id: {} <<<", ids);
        return getBaseDao().findByIds(ids);
    }

    @Override
    public List<T> findByEntity(T t) {
        LOGGER.debug(">>> findByEntity <<<");
        return getBaseDao().findByEntity(t);
    }

    @Override
    public List<T> findByEntity(T t, String orderBy) {
        LOGGER.debug(">>> findByEntity, orderBy: {} <<<", orderBy);
        return getBaseDao().findByEntity(t,orderBy);
    }

    @Override
    public Page<T> findPage(T t, Pageable pageable) {
        LOGGER.debug(">>> findPage <<<");
        return getBaseDao().findPage(t,pageable);
    }

    @Override
    public List<T> findAll() {
        LOGGER.debug(">>> findAll <<<");
        return getBaseDao().findAll();
    }
}
