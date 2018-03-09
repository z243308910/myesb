package com.echounion.boss.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.echounion.boss.common.enums.EmailStatus;
import com.echounion.boss.common.enums.RecordType;
import com.echounion.boss.common.enums.Status;
import com.echounion.boss.common.util.ThreadLocalUtil;
import com.echounion.boss.core.SystemConfig;
import com.echounion.boss.core.cache.UrlContainer;
import com.echounion.boss.core.email.EmailMessage;
import com.echounion.boss.core.email.service.IEmailService;
import com.echounion.boss.entity.dto.Caller;
import com.echounion.boss.entity.dto.Provider;
import com.echounion.boss.entity.dto.Record;
import com.echounion.boss.entity.dto.ResponseMessage;

/**
 * 邮件接口Servlet
 * @author 胡礼波
 * 2012-11-16 下午03:12:24
 */
@WebServlet(name="MailServlet",urlPatterns="/api/message/mail",asyncSupported=true)
public class MailServlet extends ServletProxy {

	/**
	 * @author 胡礼波
	 * 2012-11-16 下午03:12:33
	 */
	private static final long serialVersionUID = -1533784474113058172L;
	private Logger logger=Logger.getLogger(MailServlet.class);
	@Autowired
	private IEmailService emailService;

	public void init() throws ServletException {
		logger.info(".........系统邮件接口初始化成功.........");
	}
	
	public MailServlet() {super();}
	
	public void destroy() {super.destroy();}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action=request.getParameter("action");
		EmailStatus status=doAction(action,request);
		Record record=null;
		if(status.getCode().equals(EmailStatus.SUCCESS.getCode()))							//发送成功
		{
			record=Record.setSuccessRecord("发送成功",false,RecordType.TEXT);
		}
		else
		{
			record=Record.setFailRecord(status.getCode(),status.getName(),RecordType.TEXT);
			logger.info("发送失败,系统状态码"+status.getCode()+"  "+status.getName());
		}
		
		Provider provider=UrlContainer.getServiceProvider("/api/message/mail");
		provider.setServerIp(SystemConfig.SERVERIP);
		ResponseMessage message=new ResponseMessage(Status.SUCCESS, "HTTP-200",record.getMessage());
		super.addEsbLog(provider, message);
		
		writeJsonDate(response,record);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	
	/**
	 * 根据动作名称调用不同的邮件模版
	 * @author 胡礼波
	 * 2012-11-17 上午10:47:50
	 * @param action
	 * @return
	 */
	private EmailStatus doAction(String action,HttpServletRequest request)
	{
		if(null==action)
		{
			logger.warn("邮件服务Action为空");
			return EmailStatus.ACTION_NULL;
		}
		else if(action.equals("resend"))	//再次发送
		{
			return resend(request);
		}
		else if(action.equals("send"))	//发送普通邮件--不带邮件模版
		{
			return sendEmail(request);
		}
		else if(action.equals("regsuccess"))	//邮件注册成功
		{
			String email=request.getParameter("address");
			int count=emailService.addRegistMail(email);
			return count>0 ? EmailStatus.SUCCESS:EmailStatus.ERROR_SYS;
		}
		else
		{
			return EmailStatus.ACTION_ERROR;
		}
	}
	
	/**
	 * 公共的邮件发送不使用模版
	 * @param request
	 * @param subject 主题
	 * @param address 收件人地址
	 * @param content 邮件内容
	 * @return
	 */
	private EmailStatus sendEmail(HttpServletRequest request)
	{
		EmailStatus status=null;
		try
		{
			EmailMessage email=assbileMesssage(request);
			status=emailService.send(email);
		}catch (Exception e) {
			return EmailStatus.ERROR_SYS;
		}
		return status;
	}
	
	/**
	 * 再次发送操作
	 * @author 胡礼波	
	 * 2012-11-20 上午10:49:25
	 * @param request
	 * @return
	 */
	private EmailStatus resend(HttpServletRequest request)
	{
		String dataStr=request.getParameter("data");
		String datas[]=StringUtils.split(dataStr,",");
		Integer[] mailIds=(Integer[])ConvertUtils.convert(datas,Integer.class);
		EmailStatus status=emailService.reSendMail(mailIds);
		return status;
	}
	
	
	/**
	 * 组织邮件基本信息邮件地址，邮件内容
	 * @author 胡礼波
	 * 2012-11-21 上午11:37:54
	 * @param request
	 * @return
	 */
	private EmailMessage assbileMesssage(HttpServletRequest request)
	{
		EmailMessage email=new EmailMessage();
		String subject=request.getParameter("sub");		//邮件主题
		String address=request.getParameter("receiver");	//邮件地址
		String reply=request.getParameter("reply");		//邮件回复地址
		String cc=request.getParameter("cc");			//邮件抄送地址
		String btype=request.getParameter("btype");	//业务类型
		String bcode=request.getParameter("bcode");
		String tpl=request.getParameter("tpl");		//邮件模版
		String linkAttached=request.getParameter("linkAttached");	//附件地址
		String personal=request.getParameter("personal");			//发件人名称
		String split=request.getParameter("split");			//是否拆分发送
		boolean isSplit=true;
		if(split!=null && split.equalsIgnoreCase("false"))
		{
			isSplit=false;
		}
		
		email.setLinkAttached(linkAttached);
		email.setCc(cc);
		email.setReply(reply);
		email.setReceiver(address);
		email.setBtype(btype);
		email.setBcode(bcode);
		email.setTpl(tpl);
		email.setContent((request.getParameter("content")));//邮件内容
		email.setSubject(subject);
		email.setPersonal(personal);
		email.setSplit(isSplit);
		try
		{
			Caller caller=(Caller)ThreadLocalUtil.getData();
			email.setChannel(caller.getMailChannel());
		}catch (Exception e) {
		}
		return email;
	}
}

