package com.echounion.boss.cargosmart.service;

import java.io.IOException;

import com.echounion.boss.entity.dto.Record;

/**
 * SI业务接口
 * @author 胡礼波
 * 2013-10-14 下午1:46:24
 */
public interface ISiService {

	/**
	 * 提交SI数据 xml格式的
	 * @author 胡礼波
	 * 2013-10-14 下午1:48:04
	 * @param siXml 文件内容
	 * @param fileName 文件名称
	 * @return
	 */
	public Record postSiData(String fileName,String siXml);
	
	/**
	 * 上传到远程SI目录
	 * @author 胡礼波
	 * 2014-3-29 上午11:54:51
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public boolean uploadSiToFtp(String path)throws IOException;
}
