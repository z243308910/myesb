package com.echounion.bossmanager.action.shortmsg;

import java.io.IOException;
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
import com.echounion.bossmanager.common.enums.LoginFlag;
import com.echounion.bossmanager.common.enums.ShortMsgStatus;
import com.echounion.bossmanager.common.json.JsonUtil;
import com.echounion.bossmanager.common.security.Boss;
import com.echounion.bossmanager.common.security.annotation.ActionRightCtl;
import com.echounion.bossmanager.entity.MobileHistory;
import com.echounion.bossmanager.entity.MobileList;
import com.echounion.bossmanager.entity.dto.BossProperty;
import com.echounion.bossmanager.entity.dto.Record;
import com.echounion.bossmanager.service.shortmsg.IShortMsgHistoryService;

/**
 * 短信Action
 * @author 胡礼波
 * 2012-11-19 下午07:25:11
 */
@Controller("shortMsgAction")
@Scope("prototype")
public class ShortMsgAction extends BaseAction {

	/**
	 * @author 胡礼波
	 * 2012-11-19 下午07:25:40
	 */
	private static final long serialVersionUID = 8877585324991854256L;
	
	private Logger logger=Logger.getLogger(ShortMsgAction.class);

	@Autowired
	private IShortMsgHistoryService shortMsgService;
	private MobileHistory mobileHistory;

	public MobileHistory getMobileHistory() {
		return mobileHistory;
	}

	public void setMobileHistory(MobileHistory mobileHistory) {
		this.mobileHistory = mobileHistory;
	}

	@ActionRightCtl(login=LoginFlag.YES)
	public String execute()
	{
		return getAllHistory();
	}
	
	/**
	 * 获得所有的短信记录列表
	 * @author 胡礼波
	 * 2012-11-19 下午07:23:35
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String getAllHistory()
	{
		instantPage();
		List<MobileHistory> list=shortMsgService.getMobileHistory(mobileHistory);
		int total=shortMsgService.getCount(mobileHistory);
		JSONObject jsonData=JsonUtil.toJsonStringFilterPropter(list, total);
		setJsonData(jsonData);
		return JSON;
	}
	
	/**
	 * 删除短信记录
	 * @author 胡礼波
	 * 2012-11-19 下午07:31:54
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String delHistory()
	{
		String dataStr=getServletReqeust().getParameter("data");
		String datas[]=StringUtils.split(dataStr,",");
		Integer[] ids=(Integer[])ConvertUtils.convert(datas,Integer.class);
		int count=shortMsgService.delMobileHistory(ids);
		writeData(count);
		return null;
	}
	
	/**
	 * 重新发送短信
	 * @author 胡礼波
	 * 2012-11-20 上午10:56:25
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String reSend()
	{
		String dataStr=getServletReqeust().getParameter("data");
		String datas[]=StringUtils.split(dataStr,",");
		Integer[] msgIds=(Integer[])ConvertUtils.convert(datas,Integer.class);
		writeData(shortMsgService.editReSendShortMsg(msgIds));
		return null;
	}
	
	/**
	 * 系统测试发送短信
	 * @author 胡礼波
	 * 2012-11-20 下午03:04:59
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String test()
	{
		String url=BossProperty.getInstance().getShortMsgTestUrl();
		String phones=getServletReqeust().getParameter("receiver");//邮件地址
		String content=getServletReqeust().getParameter("content");		//邮件内容
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("receiver",phones);
		params.put("content",content);
		String result=null;
		try {
			Record record=new Boss().requestBoss(url, params);
			if(record.getStatus()==Record.SUCCESS)		//成功
			{
				result="短信已经发出";
			}
			else										//失败
			{
				record.getErrorCode();
				result=ShortMsgStatus.getStatus(record.getErrorCode()).getName();
				result=result==null?"未知异常,发送失败!":result+",发送失败!";
			}			
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("测试短信服务网络异常"+e);
			result="网络异常";
		}catch (Exception e) {
			logger.error("测试短信服务系统异常"+e);
			result="系统异常";
		}
		writeData(result);
		return null;
	}
	
	/**
	 * 获得注册手机列表
	 * @author 胡礼波
	 * 2012-12-13 下午01:40:24
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String getRegMobile()
	{
		instantPage();
		List<MobileList> list=shortMsgService.getRegMobile();
		int total=shortMsgService.getRegCount();
		JSONObject jsonData=JsonUtil.toJsonStringFilterPropter(list, total);
		setJsonData(jsonData);
		return JSON;
	}
	
	/**
	 * 删除注册手机
	 * @author 胡礼波
	 * 2012-12-13 下午01:40:44
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String delRegMobile()
	{
		String dataStr=getServletReqeust().getParameter("data");
		String datas[]=StringUtils.split(dataStr,",");
		Integer[] ids=(Integer[])ConvertUtils.convert(datas,Integer.class);
		int count=shortMsgService.delRegMobile(ids);
		writeData(count);
		return null;
	}
}
