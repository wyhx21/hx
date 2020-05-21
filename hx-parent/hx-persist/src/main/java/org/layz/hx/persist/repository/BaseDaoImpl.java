package org.layz.hx.persist.repository;

import org.layz.hx.base.entity.AutoLongBaseEntity;
import org.layz.hx.base.inte.entity.AutoKeyEntity;
import org.layz.hx.base.pojo.Page;
import org.layz.hx.base.pojo.Pageable;
import org.layz.hx.base.util.Assert;
import org.layz.hx.core.pojo.info.TableClassInfo;
import org.layz.hx.core.support.HxTableSupport;
import org.layz.hx.persist.complete.CompleteFactory;
import org.layz.hx.persist.factory.RowMapperFactory;
import org.layz.hx.persist.factory.SqlBuildFactory;
import org.layz.hx.persist.inte.Const;
import org.layz.hx.persist.sqlBuilder.SqlBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class BaseDaoImpl<T> implements BaseDao<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseDaoImpl.class);
	protected Class<T> clazz;
	private TableClassInfo tableClassInfo;
	@Value("${hx.persist.batch.size:200}")
	private int batchSize;
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	@PostConstruct
	public void init() {
		initDao();
	}

	protected void initDao() {
		// 获取泛型
		Type type = getParameterizedType(getClass());
		Assert.isNotNull(type,"class resolve error");
		Type[] typeArr = ((ParameterizedType) type).getActualTypeArguments();
		setClazz((Class<T>) typeArr[0]);
	}

	@Override
	public Integer persistEntity(T t) {
		if(null == t) {
			throw new NullPointerException();
		}
		CompleteFactory.complete(t);
		SqlBuilder builder = SqlBuildFactory.getSqlBuilder(Const.PERSIST_ENTITY);
		final String sql = builder.buildSql(tableClassInfo, t);
		final Object[] args = builder.buildArgs(tableClassInfo, t);
		if(!AutoKeyEntity.class.isInstance(t)) {
			return jdbcTemplate.update(sql, args);
		}
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int update = jdbcTemplate.update((con) ->{
			PreparedStatement ps = con.prepareStatement(sql, new String[]{tableClassInfo.getId()});
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
			return ps;
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		if(t instanceof AutoLongBaseEntity) {
			((AutoLongBaseEntity)t).setId(id);
		}
		return update;
	}

	@Override
	public void persistBatch(List<T> list){
		if(CollectionUtils.isEmpty(list)) {
			return;
		}
		SqlBuilder builder = SqlBuildFactory.getSqlBuilder(Const.PERSIST_ENTITY);
		final String sql = builder.buildSql(tableClassInfo, list.get(0));
		this.jdbcTemplate.batchUpdate(sql, list, batchSize, (ps, argument) -> {
			CompleteFactory.complete(argument);
			final Object[] args = builder.buildArgs(tableClassInfo, argument);
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
		});
	}

	@Override
	public Integer update(T t) {
		if(null == t) {
			throw new NullPointerException();
		}
		setBaseUpdateEntity(t);
		SqlBuilder builder = SqlBuildFactory.getSqlBuilder(Const.UPDATE);
		final String sql = builder.buildSql(tableClassInfo);
		final Object[] args = builder.buildArgs(tableClassInfo, t);
		return jdbcTemplate.update(sql, args);
	}

	@Override
	public void updateBatch(List<T> list){
		if(CollectionUtils.isEmpty(list)) {
			return;
		}
		SqlBuilder builder = SqlBuildFactory.getSqlBuilder(Const.UPDATE);
		final String sql = builder.buildSql(tableClassInfo);
		this.jdbcTemplate.batchUpdate(sql, list, batchSize, (ps, argument) -> {
			setBaseUpdateEntity(argument);
			final Object[] args = builder.buildArgs(tableClassInfo, argument);
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
		});
	}

	@Override
	public Integer deleteByEntity(T t) {
		SqlBuilder builder = SqlBuildFactory.getSqlBuilder(Const.DELETE_BY_ENTITY);
		final String sql = builder.buildSql(tableClassInfo, t);
		final Object[] args = builder.buildArgs(tableClassInfo, t);
		return jdbcTemplate.update(sql, args);
	}
	
	@Override
	public Integer deleteById(Long id) {
		SqlBuilder builder = SqlBuildFactory.getSqlBuilder(Const.DELETE_BY_ID);
		final String sql = builder.buildSql(tableClassInfo);
		LOGGER.debug("hx persist args ==> {}", id);
		return jdbcTemplate.update(sql, id);
	}
	
	public T findById(Long id) {
		try {
			Assert.isNotNull(id,"please select record!");
			SqlBuilder builder = SqlBuildFactory.getSqlBuilder(Const.FIND_BY_ID);
			final String sql = builder.buildSql(tableClassInfo);
			LOGGER.debug("hx persist args ==> {}", id);
			RowMapper<T> rowMapper = RowMapperFactory.getRowMapper(clazz);
			T t = jdbcTemplate.queryForObject(sql, rowMapper, id);
			return t;
		} catch (EmptyResultDataAccessException e) {
			LOGGER.debug("result is empty");
		}
		return null;
	}

	@Override
	public List<T> findByIds(List<Long> ids) {
		if(null == ids || ids.isEmpty()) {
			return Collections.emptyList();
		}
		SqlBuilder builder = SqlBuildFactory.getSqlBuilder(Const.FIND_BY_IDS);
		final String sql = builder.buildSql(tableClassInfo, ids);
		final Object[] args = builder.buildArgs(tableClassInfo, ids);

		RowMapper<T> rowMapper = RowMapperFactory.getRowMapper(clazz);
		List<T> list = jdbcTemplate.query(sql, args, rowMapper);
		return list;
	}

	@Override
	public List<T> findByEntity(T t) {
		return findListByEntity(t, null);
	}
	
	@Override
	public List<T> findByEntity(T t, String orderBy) {
		return findListByEntity(t,new Pageable(orderBy));
	}
	
	@SuppressWarnings("unchecked")
	public Page<T> findPage(T t, Pageable pageable){
		pageable = resetPageable(pageable);
		Long total = findCountByEntity(t);
		
		Page<T> pageResult = new Page<T>();
		pageResult.setTotal(total);
		pageResult.setPage(pageable.getPage());
		pageResult.setSize(pageable.getSize());
		
		if(total <= pageable.getOffset()) {
			pageResult.setData(Collections.EMPTY_LIST);
		} else {
			List<T> list = findListByEntity(t,pageable);
			pageResult.setData(list);
		}
		return pageResult;
	}
	@Override
	public Long findCountByEntity(T t){
		SqlBuilder builder = SqlBuildFactory.getSqlBuilder(Const.COUNT_BY_ENTITY);
		final String sql = builder.buildSql(tableClassInfo, t);
		final Object[] args = builder.buildArgs(tableClassInfo, t);
		Long count = jdbcTemplate.queryForObject(sql, args, Long.class);
		return count;
	}

    @Override
    public List<T> findAll() {
		SqlBuilder builder = SqlBuildFactory.getSqlBuilder(Const.FIND_ALL);
		final String sql = builder.buildSql(tableClassInfo);
		RowMapper<T> rowMapper = RowMapperFactory.getRowMapper(clazz);
        return jdbcTemplate.query(sql, rowMapper);
    }

	public void setClazz(Class<T> clazz){
		this.clazz = clazz;
		tableClassInfo = HxTableSupport.getTableClassInfo(clazz);
	}

	/**
	 * @param pageable
	 * @return
	 */
	protected Pageable resetPageable(Pageable pageable) {
		if(null == pageable) {
			pageable = new Pageable();
		}
		if(null == pageable.getPage()) {
			pageable.setPage(1);
		}
		if(null == pageable.getSize()) {
			pageable.setSize(10);
		}
		return pageable;
	}

	/**
	 * @param t
	 * @param pageable
	 * @return
	 */
	private List<T> findListByEntity(T t, Pageable pageable){
		SqlBuilder builder = SqlBuildFactory.getSqlBuilder(Const.FIND_BY_ENTITY);
		final String sql = builder.buildSql(tableClassInfo, t, pageable);
		final Object[] args = builder.buildArgs(tableClassInfo, t, pageable);
		RowMapper<T> rowMapper = RowMapperFactory.getRowMapper(clazz);
		List<T> list = jdbcTemplate.query(sql, args, rowMapper);
		return list;
	}

	/**
	 * @param t
	 */
	private void setBaseUpdateEntity(T t) {
		if(!(t instanceof AutoLongBaseEntity)) {
			return;
		}
		AutoLongBaseEntity baseEntity = (AutoLongBaseEntity) t;
		if(null == baseEntity.getLastModifiedDate()) {
			baseEntity.setLastModifiedDate(new Date());
		}
	}

	/**
	 * @param type
	 * @return
	 */
	private Type getParameterizedType(Type type){
        if(type instanceof Class) {
            return getParameterizedType(((Class<?>) type).getGenericSuperclass());
        } else if (type instanceof ParameterizedType){
            String typeName = type.getTypeName();
            if(typeName.startsWith("org.layz.hx.persist.repository.BaseDaoImpl<")){
                return type;
            }
            return getParameterizedType(((ParameterizedType) type).getRawType());
        }
        return null;
    }
}
