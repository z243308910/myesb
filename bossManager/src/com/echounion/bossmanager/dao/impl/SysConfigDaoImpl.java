package com.echounion.bossmanager.dao.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.echounion.bossmanager.dao.ISysConfigDao;
import com.echounion.bossmanager.entity.SysConfig;
import com.echounion.bossmanager.entity.dto.DBTable;

@SuppressWarnings("unchecked")
@Repository
public class SysConfigDaoImpl extends BaseDao<SysConfig> implements ISysConfigDao {

	@Override
	public List<SysConfig> getAll() {
		List<SysConfig> list=super.createCriteria().addOrder(Order.asc("configCode")).list();
		return list;
	}

	public List<DBTable> getDSTables() {
		List<DBTable> list=null;
		Connection con=null;
		ResultSet res=null;
		try
		{
		con=SessionFactoryUtils.getDataSource(super.getSessionFactory()).getConnection();
		DatabaseMetaData databaseMetaData=con.getMetaData();
		res=databaseMetaData.getTables(null,"%","%",new String[]{"TABLE"});
		DBTable table=null;
		list=new ArrayList<DBTable>();
		while(res.next())
			{
				String tableName=res.getString("TABLE_NAME");
				String remark=res.getString("REMARKS");
				int count=getDataCount(con, tableName);
				table=new DBTable(tableName,count,remark);
				list.add(table);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try
			{
			if(res!=null){res.close();}if(con!=null){con.close();}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public int getDataCount(Connection con,String tableName)
	{
		String sql="select count(1) from "+tableName;
		PreparedStatement ps=null;
		ResultSet res=null;
		int count=0;
		try
		{
			ps=con.prepareStatement(sql);
			res=ps.executeQuery();
			if(res.next())
			{
				count=res.getInt(1);	
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(res!=null){res.close();}
				if(ps!=null){ps.close();}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	public int delTableData(String tableName) {
		String sql="delete from "+tableName;
		return super.getSqlQuery(sql).executeUpdate();
	}

	public SysConfig getSysConfig(String configCode, int type) {
			return (SysConfig)super.createCriteria(Restrictions.eq("configCode", configCode),Restrictions.eq("type",type)).uniqueResult();
	}

	@Override
	public SysConfig getSysConfig(String configCode) {
		return (SysConfig)super.createCriteria(Restrictions.eq("configCode", configCode)).uniqueResult();
	}
}
