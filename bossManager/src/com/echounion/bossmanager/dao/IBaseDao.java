package com.echounion.bossmanager.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;


/**
 * 数据层 访问接口
 * @author 胡礼波
 * 10:34:11 AM Oct 17, 2012 
 * @param <T>
 */
public interface IBaseDao<T>{

	/**
	 * 根据主键返回一个对象
	 * @author 胡礼波
	 * 10:34:23 AM Oct 17, 2012 
	 * @param id
	 * @return
	 */
	public T getObject(Serializable id) ;
	
	/**
	 * 保存一个对象
	 * @author 胡礼波
	 *  10:34:23 AM Oct 17, 2012 
	 * @param obj
	 */
	public int saveObject(Object obj) ;
	
	/**
	 * 删除一个对象
	 * @author 胡礼波
	 *  10:34:23 AM Oct 17, 2012 
	 * @param obj
	 */
	public int removeObject(Object obj) ;
	
	/**
	 * 根据主键删除一个对象
	 * @author 胡礼波
	 *  10:34:23 AM Oct 17, 2012 
	 * @param id
	 */
	public int removeObjectById(Serializable id) ;
		
	/**
	 * 根据主键集合删除一批对象
	 * @author 胡礼波
	 *  10:34:23 AM Oct 17, 2012 
	 * @param ids 值
	 * @param key 一般为标识列或唯一列
	 */
	public int removeObject(Serializable[] ids,String key) ;
	
	/**
	 * 更新一个对象
	 * @author 胡礼波
	 *  10:34:23 AM Oct 17, 2012 
	 * @param obj
	 */
	public int updateObject(Object obj) ;
	
	/**
	 * 按照属性查询对象集合 默认按照主键降序排列
	 * @author 胡礼波
	 *  10:34:23 AM Oct 17, 2012 
	 * @param property
	 * @param value
	 * @return
	 */
	public List<T> getByProperty(String property, Object value) ;
	
	/**
	 * 按照属性查找唯一对象
	 * @author 胡礼波
	 *  10:34:23 AM Oct 17, 2012 
	 * @param property
	 * @param value
	 * @return
	 */
	public T getUniqueByProperty(String property, Object value) ;
	
	/**
	 * 按照属性统计记录数量
	 * @author 胡礼波
	 *  10:34:23 AM Oct 17, 2012 
	 * @param property
	 * @param value
	 * @return
	 */
	public int getCountByProperty(String property, Object value) ;
	
	/**
	 * 按Criterion查询列表数据.
	 * @author 胡礼波
	 * 10:34:23 AM Oct 17, 2012 
	 * @param criterion
	 * @return
	 */
	public List<T> getByCriteria(Criterion... criterion) ;
	
	/**
	 * 查询所有列表数据 默认按照主键降序排列
	 * @author 胡礼波
	 *  10:34:23 AM Oct 17, 2012 
	 * @return
	 */
	public List<T> getAll() ;
	
	/**
	 *  根据Criterion条件创建Criteria,后续可进行更多处理,辅助函数.
	 * @author 胡礼波
	 *  10:34:23 AM Oct 17, 2012 
	 * @param criterions
	 * @return
	 */
	public Criteria createCriteria(Criterion... criterions) ;
	
	/**
	 * 返回query对象
	 * @author 胡礼波
	 *  10:34:23 AM Oct 17, 2012 
	 * @param hql
	 * @return
	 */
	public Query getQuery(String hql);
	
	/**
	 * 查询总记录数
	 * @author 胡礼波
	 * 10:34:23 AM Oct 17, 2012 
	 * @param criterion
	 * @return
	 */
	public int getCount();
}
