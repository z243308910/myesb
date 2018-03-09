package com.echounion.bossmanager.common.security;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.codec.binary.Hex;
import com.alibaba.fastjson.JSON;
import com.echounion.bossmanager.common.constant.Constant;
import com.echounion.bossmanager.common.enums.SystemStatus;
import com.echounion.bossmanager.common.util.NetUtil;
import com.echounion.bossmanager.entity.dto.BossProperty;
import com.echounion.bossmanager.entity.dto.Record;

/**
 * BOSS服务器类
 * @author 胡礼波
 * 2012-11-22 上午09:50:11
 */
public class Boss implements Serializable{

	/**
	 * @author 胡礼波
	 * 2012-11-22 上午09:50:36
	 */
	private static final long serialVersionUID = 3718379597447722617L;

	/**
	 * 请求Boss服务器，如果没有登录则自动登录一次，登录则请求传入的地址
	 * @author 胡礼波
	 * 2012-11-21 下午07:55:08
	 * @return
	 * @throws Exception
	 */
	public Record requestBoss(String url,Map<String,Object> params)throws Exception
	{
		if(null==params)
		{
			params=new HashMap<String, Object>();
		}
		params.put("authorKey",BossProperty.getInstance().getAuthorKey());
		params.put("clientKey",BossProperty.getInstance().getClientKey());
		String result=NetUtil.doNet(url,params);
		Record record=JSON.parseObject(result,Record.class);				//发起第一次请求
		if(record.getStatus()==Constant.FAIL)
		{
			if(record.getErrorCode().equals(SystemStatus.NO_LOGIN.getCode()))	//未登录 系统则自动登录
			{
				record=loginBoss();
				if(record.getStatus()==Constant.SUCCESS)						//登录成功则重新发起请求
				{
					BossProperty.getInstance().setClientKey(record.getMessage());
					params.put("clientKey",record.getMessage());
					result=NetUtil.doNet(url,params);
					record=JSON.parseObject(result,Record.class);
				}
			}
		}
		return record;
	}
	
	/**
	 * 登录到BOSS系统
	 * @author 胡礼波
	 * 2012-11-22 上午10:02:40
	 * @return
	 * @throws Exception
	 */
	private Record loginBoss()throws Exception
	{
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("authorKey",BossProperty.getInstance().getAuthorKey());
		Record record=JSON.parseObject(NetUtil.doNet(BossProperty.getInstance().getBossLoginUrl(), map),Record.class);
		return record;
	}
	
	/**
	 * 产生授权码
	 * @author 胡礼波
	 * 2013-7-22 下午5:34:40
	 * @param authorKey
	 * @return
	 */
	public static String createAuthorKey(String authorKey)
	{
		authorKey=SecurityEncrypt.getInstance().getEncString(authorKey);
		try {
			authorKey=Hex.encodeHexString(authorKey.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return authorKey;
	}
}