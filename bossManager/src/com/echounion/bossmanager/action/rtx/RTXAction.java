package com.echounion.bossmanager.action.rtx;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.echounion.bossmanager.action.BaseAction;
import com.echounion.bossmanager.common.constant.Constant;
import com.echounion.bossmanager.common.enums.LoginFlag;
import com.echounion.bossmanager.common.enums.RtxStatus;
import com.echounion.bossmanager.common.json.JsonUtil;
import com.echounion.bossmanager.common.security.Boss;
import com.echounion.bossmanager.common.security.annotation.ActionRightCtl;
import com.echounion.bossmanager.entity.RTXHistory;
import com.echounion.bossmanager.entity.dto.BossProperty;
import com.echounion.bossmanager.entity.dto.Record;
import com.echounion.bossmanager.service.rtx.IRTXHistoryService;

/**
 * RTXAction
 * @author  张霖
 * 2013-3-25 上午10:16:54
 */
@Controller("rtxAction")
@Scope("prototype")
public class RTXAction extends BaseAction
{
	/**
	 * @author 张霖
	 * 2013-3-25 上午11:27:21
	 */
	private static final long serialVersionUID = 5211743587754638179L;

	@Autowired
	private IRTXHistoryService rtxHistoryService; 
	
	private Logger logger=Logger.getLogger(RTXAction.class);
	
	private RTXHistory rtxHistory;
	
	public RTXHistory getRtxHistory() {
		return rtxHistory;
	}

	public void setRtxHistory(RTXHistory rtxHistory) {
		this.rtxHistory = rtxHistory;
	}

	@Override
	public String execute()
	{
		return getAllRTXHistory();
	}
	
	/**
	 * 获取所有的记录列表
	 * @author 张霖
	 * 2013-3-25 上午10:38:53
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	private String getAllRTXHistory() {
		instantPage();
		try {
			String receiver=new String(rtxHistory.getReceiver().getBytes("ISO-8859-1"),Constant.ENCODEING_UTF8);
			rtxHistory.setReceiver(receiver);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<RTXHistory> list = rtxHistoryService.getRTXHistory(rtxHistory);
		int total = rtxHistoryService.getCount(rtxHistory);
		JSONObject jsonData = JsonUtil.toJsonStringFilterPropter(list, total);
		setJsonData(jsonData);
		return JSON;
	}
	
	/**
	 * 删除RTX记录
	 * @author 张霖
	 * 2013-3-25 下午1:42:21
	 * @return 
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String delHistory()
	{
		String dataStr=getServletReqeust().getParameter("data");
		String datas[]=StringUtils.split(dataStr,",");
		Integer[] ids=(Integer[])ConvertUtils.convert(datas,Integer.class);
		int count=rtxHistoryService.delRtxHistory(ids);
		writeData(count);
		return null;
	}
	
	
	/**
	 * 系统测试发送邮件
	 * @author 胡礼波
	 * 2012-11-20 下午03:04:59
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String test()
	{
		String url=BossProperty.getInstance().getRtxTestUrl();
		String content=rtxHistory.getContent();
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("title",rtxHistory.getTitle());
		params.put("receiver",rtxHistory.getReceiver());
		params.put("content",content);
		params.put("btype",rtxHistory.getBtype());
		params.put("bcode",rtxHistory.getBcode());
		String result=null;
		try {
			Record record=new Boss().requestBoss(url, params);
			if(record.getStatus()==Record.SUCCESS)		//成功
			{
				result="RTX已经发出";
			}
			else										//失败
			{
				result=RtxStatus.getSysStatus(record.getErrorCode()).getName();
				result=result==null?"未知异常,发送失败!":result+",发送失败!";
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("测试RTX服务网络异常"+e);
			result="网络异常";
		}catch (Exception e) {
			logger.error("测试RTX服务系统异常,或服务器忙!"+e);
			result="系统异常";
		}
		writeData(result);
		return null;
	}
}
