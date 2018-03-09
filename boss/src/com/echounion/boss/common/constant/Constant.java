package com.echounion.boss.common.constant;

/**
 * 系统常量类
 * @author 胡礼波
 * 2012-10-30 下午05:50:27
 */
public class Constant {
	
	public static final String ENCODEING_UTF8="UTF-8";
	public static final String ENCODEING_GBK="GBK";
	
	public static final String DATABASE_SS_KEY="echounion!@$*%01";			//数据库密码加密密钥
	public static final String INTERFACE_SS_KEY="echodesk";			//接口数据加密密钥	
	
	//系统公共状态
	public static int SUCCESS=1;		//成功
	public static int FAIL=-1;			//失败
	
	//数据结果类型
	public static int JSON_TYPE=1;			// JSON
	public static int XML_TYPE=2;		//XML类型
	public static int TEXT_TYPE=3;		//文本类型
	
	
	//系统配置类别
	public static final int SYS_TRACKING=1;//采集器
	public static final int SYS_SHORTMSG=2;//短信
	public static final int SYS_EMAIL=3;//邮件
	
	
	public static final String SHORTMSG_CONFIG_SOFREIGHT="BOSS_SHORTMSG_CONFIG_SOFREIGHT";//缓存 短信配置标志
	public static final String SHORTMSG_CONFIG_CARGOON = "BOSS_SHORTMSG_CONFIG_CARGOON";	//缓存 短信配置标志
}
