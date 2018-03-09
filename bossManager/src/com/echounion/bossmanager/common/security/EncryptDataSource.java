package com.echounion.bossmanager.common.security;

import org.logicalcobwebs.proxool.ProxoolDataSource;

import com.echounion.bossmanager.common.constant.Constant;
import com.echounion.bossmanager.common.security.encoder.BASE64;


/**
 * 连接数据源的密码进行加密
 * @author 胡礼波
 * 10:31:12 AM Oct 17, 2012
 */
public class EncryptDataSource extends ProxoolDataSource {

	@Override
	public void setPassword(String password) {
		SecurityEncrypt se=SecurityEncrypt.getInstance();
		se.setKey(Constant.DATABASE_SS_KEY);		//设置数据库密码的密钥
		String realPwd=se.getDesString(password);
		super.setPassword(realPwd);
		se.setKey(Constant.INTERFACE_SS_KEY);	//把加密单利设置数据接口加密
	}
	
	public static void main(String[] args) {
		SecurityEncrypt se=SecurityEncrypt.getInstance();
		se.setKey(Constant.DATABASE_SS_KEY);		//设置数据库密码的密钥
		System.out.println(se.getEncString("123456"));
	}
}
