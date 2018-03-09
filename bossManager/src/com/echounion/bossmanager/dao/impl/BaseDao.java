package com.echounion.bossmanager.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.echounion.bossmanager.common.page.Pager;
import com.echounion.bossmanager.common.reflect.ClassUtils;
import com.echounion.bossmanager.dao.IBaseDao;

@Repository
public class BaseDao<T> implements IBaseDao<T> {

	@Autowired
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession()
	{
		return getSessionFactory().getCurrentSession();
	}
	
	public BaseDao(){}

	@SuppressWarnings("unchecked")
	public T getObject(Serializable id)
	{
		try
		{
			Class<?> entityClass=ClassUtils.getSuperClassGenricType(this.getClass());
			Object obj = getSession().get(entityClass, id);
			T t = (T)obj;
			return t;
		}catch (Exception e) {
			throw new HibernateException(e);
		}
	}


	@SuppressWarnings("unchecked")
	private List<T> getObjects(Serializable[] ids,String key)
	{
		List<T> list=null;
		try {
			Class<?> entityClass=ClassUtils.getSuperClassGenricType(this.getClass());
			Criteria c=getSession().createCriteria(entityClass);
			c.add(Restrictions.in(key.trim(), ids));
			list = c.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new HibernateException("find some data is error!");
		}
		return list;
	}
	
	
	public int saveObject(Object obj)
	{
		int id=0;
		try {
			id = (Integer)getSession().save(obj);
		} catch (Exception e) {
			throw new HibernateException("save a data of "+obj.getClass().getName()+" is error!"+e);
		}
		return id;
	}
	
	public int removeObject(Object obj)
	{
		int count=0;
		if(null!=obj)
		{
			try{
			getSession().delete(obj);
			count++;
			}
			catch (Exception e) {
				throw new HibernateException(e);
			}
		}
		return count;
	}
	
	public int removeObjectById(Serializable id)
	{
		T t=getObject(id);
		int count=0;
		if(t!=null)
		{
			count=removeObject(t);
		}
		return count;
	}
	
	public int removeObject(Serializable[] ids,String key)
	{
		
		List<T> list=getObjects(ids, key);
		int count=0;
		for (T t : list) {
			removeObject(t);
			count++;
			if(count%10==0&&count!=0)
			{
				getSession().flush();
			}
		}
		return count;
	}
	
	public int updateObject(Object obj)
	{
		int count=0;
		if(obj!=null)
		{
			try
			{
				getSession().update(obj);
				count++;
			}catch (Exception e) {
				throw new HibernateException(e);
			}
		}
		return count;
	}

	public int getCountByProperty(String property, Object value) {
		Assert.hasText(property);
		Assert.notNull(value);
		return ((Number)(createCriteria(Restrictions.eq(property, value)).setProjection(Projections.rowCount()).uniqueResult())).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<T> getByCriteria(Criterion... criterion)  {
		return createCriteria(criterion).list();
	}

	@SuppressWarnings("unchecked")
	public List<T> getByProperty(String property, Object value)  {
		Assert.hasText(property);
		Class<?> cls=ClassUtils.getSuperClassGenricType(this.getClass());
		Criteria criteria=null;
		if(value.getClass().isArray())			//如果是集合
		{
			criteria=createCriteria(Restrictions.in(property,(Object[])value));
		}else
		{
			criteria=createCriteria(Restrictions.eq(property, value));
		}
		String identity=getIdentity(cls);
		if(StringUtils.isNotEmpty(identity))
		{
			criteria.addOrder(Order.desc(identity));
		}
		try
		{
			Pager pager=Pager.getPager();
			if(pager!=null)
			{
				criteria.setFirstResult((pager.getPageNo()-1)*pager.getPageSize());
				criteria.setMaxResults(pager.getPageSize());
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		finally
		{
			Pager.remove();
		}		
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public T getUniqueByProperty(String property, Object value){
		Assert.hasText(property);
		Assert.notNull(value);
		T t=null;
		try
		{
			t=(T)createCriteria(Restrictions.eq(property, value)).uniqueResult();
		}catch (NonUniqueResultException e) {
			throw new HibernateException("数据库有相同的主键信息，请检查后修改");
		}catch (Exception e) {
			throw new HibernateException(e);
		}
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getAll()
	{
		Class<?> cls=ClassUtils.getSuperClassGenricType(this.getClass());
		Criteria criteria=getSession().createCriteria(cls);
		String identity=getIdentity(cls);
		if(StringUtils.isNotEmpty(identity))
		{
			criteria.addOrder(Order.desc(identity));		//默认按照主键降序排列
		}
		try
		{
			Pager pager=Pager.getPager();
			if(pager!=null)
			{
				criteria.setFirstResult((pager.getPageNo()-1)*pager.getPageSize());
				criteria.setMaxResults(pager.getPageSize());
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new HibernateException(ex);
		}
		finally
		{
			Pager.remove();
		}
		return criteria.list();
	}
	
	/**
	 * 获得主键
	 * @author 胡礼波
	 * 10:36:41 AM Oct 17, 2012 
	 * @param cls
	 * @return
	 */
	private String getIdentity(Class<?> cls)
	{
		ClassMetadata meta=getSessionFactory().getClassMetadata(cls);
		return meta.getIdentifierPropertyName();
	}

	public Criteria createCriteria(Criterion... criterions) {
		Class<?> cls=ClassUtils.getSuperClassGenricType(this.getClass());
		Criteria criteria=null;
		try {
			criteria = getSession().createCriteria(cls);
			Pager pager=Pager.getPager();
			if(pager!=null)
			{
				criteria.setFirstResult((pager.getPageNo()-1)*pager.getPageSize());
				criteria.setMaxResults(pager.getPageSize());
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		finally
		{
			Pager.remove();
		}
		if(criterions!=null)
		{
			for (Criterion criterion : criterions) {
				criteria.add(criterion);
			}
		}
		return criteria;
	}

	public int getCount(){
		int total=((Number)(createCriteria().setProjection(Projections.rowCount()).uniqueResult())).intValue();
		return total;
	}

	public Query getQuery(String hql) {
		Query query=null;
		try
		{
		query=getSession().createQuery(hql);
		Pager pager=Pager.getPager();
		if(pager!=null)
		{
			query.setFirstResult((pager.getPageNo()-1)*pager.getPageSize());
			query.setMaxResults(pager.getPageSize());
		}
		}catch (Exception e) {
			throw new HibernateException(e);
		}
		finally
		{
			Pager.remove();
		}
		return query;
	}
	
	public Query getSqlQuery(String sql)
	{
		Query query=null;
		try
		{
		query=getSession().createSQLQuery(sql);
		}catch (Exception e) {
			throw new HibernateException(e);
		}
		return query;
	}
}
