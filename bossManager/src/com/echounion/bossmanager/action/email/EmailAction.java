package com.echounion.bossmanager.action.email;

import java.io.File;
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
import com.echounion.bossmanager.common.enums.EmailStatus;
import com.echounion.bossmanager.common.enums.LoginFlag;
import com.echounion.bossmanager.common.json.JsonUtil;
import com.echounion.bossmanager.common.security.Boss;
import com.echounion.bossmanager.common.security.annotation.ActionRightCtl;
import com.echounion.bossmanager.common.util.StringUtil;
import com.echounion.bossmanager.entity.MailHistory;
import com.echounion.bossmanager.entity.MailList;
import com.echounion.bossmanager.entity.dto.BossProperty;
import com.echounion.bossmanager.entity.dto.EmailTplDTO;
import com.echounion.bossmanager.entity.dto.Record;
import com.echounion.bossmanager.service.email.IEmailHistoryService;

/**
 * 邮件Action
 * @author 胡礼波
 * 2012-11-19 下午07:22:28
 */
@Controller("emailAction")
@Scope("prototype")
public class EmailAction extends BaseAction {

	/**
	 * @author 胡礼波
	 * 2012-11-19 下午07:22:34
	 */
	private static final long serialVersionUID = -2697408037080012853L;
	private Logger logger=Logger.getLogger(EmailAction.class);
	private File mailtpl;
	private String mailtplContentType;
	private String mailtplFileName;
	private MailHistory mailHistory;
	
	@Autowired
	private IEmailHistoryService emailHistoryService;
	
	@ActionRightCtl(login=LoginFlag.YES)
	public String execute()
	{
		return getAllHistory();
	}
	
	/**
	 * 获得所有的邮件记录列表
	 * @author 胡礼波
	 * 2012-11-19 下午07:23:35
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String getAllHistory()
	{
		instantPage();
		List<MailHistory> list=emailHistoryService.getMailHistory(mailHistory);
		int total=emailHistoryService.getCount(mailHistory);
		JSONObject jsonData=JsonUtil.toJsonStringFilterPropter(list, total);
		setJsonData(jsonData);
		return JSON;
	}
	
	/**
	 * 删除邮件记录
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
		int count=emailHistoryService.delMailHistory(ids);
		writeData(count);
		return null;
	}
	
	/**
	 * 查看邮件历史
	 * @author 胡礼波
	 * 2012-12-7 下午06:27:24
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String getHistory()
	{
		int historyId=(Integer)ConvertUtils.convert(getServletReqeust().getParameter("data"),Integer.class);
		MailHistory history=emailHistoryService.getMailHistory(historyId);
		getRequest().put("history",history);
		return "email_view";
	}
	
	/**
	 * 重新发送邮件
	 * @author 胡礼波
	 * 2012-11-20 上午10:56:25
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String reSend()
	{
		String dataStr=getServletReqeust().getParameter("data");
		String datas[]=StringUtils.split(dataStr,",");
		Integer[] mailIds=(Integer[])ConvertUtils.convert(datas,Integer.class);
		writeData(emailHistoryService.editReSendMail(mailIds));
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
		String url=BossProperty.getInstance().getMailTestUrl();
		String subject=getServletReqeust().getParameter("subject");		//主题
		String emailAddress=getServletReqeust().getParameter("receiver");//邮件地址
		String content=getServletReqeust().getParameter("content");		//邮件内容
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("sub",subject);
		params.put("receiver",emailAddress);
		params.put("content",content);
		String result=null;
		try {
			Record record=new Boss().requestBoss(url, params);
			if(record.getStatus()==Record.SUCCESS)		//成功
			{
				result="邮件已经发出";
			}
			else										//失败
			{
				result=EmailStatus.getStatus(record.getErrorCode()).getName();
				result=result==null?"未知异常,发送失败!":result+",发送失败!";
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("测试邮件服务网络异常"+e);
			result="网络异常";
		}catch (Exception e) {
			logger.error("测试邮件服务系统异常,或服务器忙!"+e);
			result="系统异常";
		}
		writeData(result);
		return null;
	}
	
	
	/**
	 * 获得邮件模版列表
	 * @author 胡礼波
	 * 2012-11-22 下午12:57:21
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String mailTplList() throws UnsupportedEncodingException
	{
		String url=BossProperty.getInstance().getMailTplUrl();
		try
		{
			Record record=new Boss().requestBoss(url,null);
			if(record.getStatus()==Constant.FAIL)		//请求失败
			{
				return null;
			}
			String resultJson=record.getMessage();
			List<EmailTplDTO> list=(List<EmailTplDTO>)com.alibaba.fastjson.JSON.parseArray(resultJson,EmailTplDTO.class);
			JSONObject jsonData=JsonUtil.toJsonStringFilterPropter(list,200);
			setJsonData(jsonData);
		}catch (Exception e) {
			logger.error("远程获取邮件模版异常"+e);
		}
		return JSON;
	}
	
	
	/**
	 * 删除邮件模版
	 * @author 胡礼波
	 * 2012-11-22 下午04:23:24
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String delMailTpl()
	{
		String paths=getServletReqeust().getParameter("data");
		String url=BossProperty.getInstance().getDelMailTplUrl();
		try
		{
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("path",paths);
			Record record=new Boss().requestBoss(url,map);
			String result=StringUtil.chartDecodeToUTF8(record.getMessage());
			if(record.getStatus()==Constant.FAIL)
			{
				writeData(result+",删除失败!");
				return null;
			}
			writeData(result+"条数据删除成功!");
		}catch (Exception e) {
			logger.error("删除远程邮件模版异常"+e);
		}
		return null;
	}
	
	/**
	 * 获得注册的邮件列表
	 * @author 胡礼波
	 * 2012-12-13 上午11:06:44
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String getRegMail()
	{
		instantPage();
		List<MailList> list=emailHistoryService.getRegMail();
		int total=emailHistoryService.getRegCount();
		JSONObject jsonData=JsonUtil.toJsonStringFilterPropter(list, total);
		setJsonData(jsonData);
		return JSON;
	}
	
	/**
	 * 删除注册邮件
	 * @author 胡礼波
	 * 2012-12-13 下午01:02:32
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String delRegMail()
	{
		String dataStr=getServletReqeust().getParameter("data");
		String datas[]=StringUtils.split(dataStr,",");
		Integer[] mailIds=(Integer[])ConvertUtils.convert(datas,Integer.class);
		writeData(emailHistoryService.delRegMail(mailIds));
		return null;
	}
	
	
	/**
	 * 上传文件
	 * @author 胡礼波
	 * 2012-11-25 下午03:36:51
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String uploadMailTpl()
	{
		return null;
	}

	public File getMailtpl() {
		return mailtpl;
	}

	public void setMailtpl(File mailtpl) {
		this.mailtpl = mailtpl;
	}

	public String getMailtplContentType() {
		return mailtplContentType;
	}

	public void setMailtplContentType(String mailtplContentType) {
		this.mailtplContentType = mailtplContentType;
	}

	public String getMailtplFileName() {
		return mailtplFileName;
	}

	public void setMailtplFileName(String mailtplFileName) {
		this.mailtplFileName = mailtplFileName;
	}
	
	public MailHistory getMailHistory() {
		return mailHistory;
	}

	public void setMailHistory(MailHistory mailHistory) {
		this.mailHistory = mailHistory;
	}
}
