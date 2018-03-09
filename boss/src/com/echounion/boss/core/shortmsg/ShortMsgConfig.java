package com.echounion.boss.core.shortmsg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 短信配置类
 * @author 胡礼波
 * 2012-11-17 下午05:37:53
 */
public class ShortMsgConfig implements Serializable{

	/**
	 * @author 胡礼波
	 * 2012-11-17 下午05:38:10
	 */
	private static final long serialVersionUID = 6506155872608406671L;
	
	private List<ShortMsgConfigDTO> configs=new ArrayList<>(2);
	public static int MAXLENGTH=70;			//最大字符长度--默认为70
	
	public List<ShortMsgConfigDTO> getConfigs() {
		return configs;
	}
	public void setConfigs(List<ShortMsgConfigDTO> configs) {
		this.configs = configs;
	}
	
}
