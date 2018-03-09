package com.echounion.boss.core.security;


import org.logicalcobwebs.proxool.ProxoolDataSource;
import com.echounion.boss.common.constant.Constant;


/**
 * 连接数据源的密码进行加密
 * @author 胡礼波
 * 2012-6-20 下午02:58:33
 */
public class EncryptDataSource extends ProxoolDataSource {

	@Override
	public void setPassword(String password) {
		SecurityEncrypt se=SecurityEncrypt.getInstance();
		se.setKey(Constant.DATABASE_SS_KEY);		//设置数据库密码的密钥
		String realPwd=se.getDesString(password);
		super.setPassword(realPwd);
		se.setKey(Constant.INTERFACE_SS_KEY);	//把加密单利设置为空 以便重新初始化 默认是数据接口加密
	}
	
	public static void main(String[] args) {
		SecurityEncrypt se=SecurityEncrypt.getInstance();
		se.setKey(Constant.DATABASE_SS_KEY);		//设置数据库密码的密钥
		System.out.println(se.getEncString("echounion_0okm9ijn"));
	}
}
