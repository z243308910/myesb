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
import com.echounion.boss.common.constant.Constant;
import com.echounion.boss.common.enums.RecordType;
import com.echounion.boss.common.enums.ShortMsgStatus;
import com.echounion.boss.common.enums.Status;
import com.echounion.boss.common.util.StringUtil;
import com.echounion.boss.common.util.ThreadLocalUtil;
import com.echounion.boss.common.util.ValidateUtil;
import com.echounion.boss.core.SystemConfig;
import com.echounion.boss.core.cache.UrlContainer;
import com.echounion.boss.core.shortmsg.ShortMsg;
import com.echounion.boss.core.shortmsg.service.IShortMsgService;
import com.echounion.boss.entity.dto.Caller;
import com.echounion.boss.entity.dto.Provider;
import com.echounion.boss.entity.dto.Record;
import com.echounion.boss.entity.dto.ResponseMessage;

/**
 * 短信接口Servlet
 * @author 胡礼波
 * 2012-11-19 下午12:27:24
 */
@WebServlet(name="ShortMsgServlet",urlPatterns="/api/message/smsg",asyncSupported=true)
public class ShortMsgServlet extends ServletProxy {

	/**
	 * @author 胡礼波
	 * 2012-11-19 下午12:27:46
	 */
	private static final long serialVersionUID = 4308905025380450749L;

	private Logger logger=Logger.getLogger(ShortMsgServlet.class);
	@Autowired
	private IShortMsgService mobileService;

	public void init() throws ServletException {
		logger.info(".........系统短信接口初始化成功.........");
	}
	
	public ShortMsgServlet() {super();}
	
	public void destroy() {super.destroy();}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding(Constant.ENCODEING_UTF8);
		String action=request.getParameter("action");
		ShortMsgStatus status=doAction(request, action);
		Record record=null;
		if(status.getCode().equals(ShortMsgStatus.SUCCESS.getCode()))		//发送成功
		{
			record=Record.setSuccessRecord("发送成功",false,RecordType.TEXT);
		}
		else
		{
			record=Record.setFailRecord(status.getCode(),status.getName(),RecordType.TEXT);
			logger.info("短信发送失败，系统状态玛"+status.getCode()+"  "+status.getName());
		}
		
		Provider provider=UrlContainer.getServiceProvider("/api/message/smsg");
		provider.setServerIp(SystemConfig.SERVERIP);
		ResponseMessage message=new ResponseMessage(Status.SUCCESS, "HTTP-200",record.getMessage());
		super.addEsbLog(provider, message);
		
		writeJsonDate(response, record);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	public ShortMsgStatus doAction(HttpServletRequest request,String action)
	{
		if(null==action)
		{
			logger.error("短信操作指令为空!");
			return ShortMsgStatus.ERROR_SYS;
		}
		else if(action.equals("resend"))			//管理台重新发送短信
		{
			return reSend(request);
		}else if(action.equals("send"))
		{
			return sendMobile(request);
		}else if(action.equals("regsuccess"))//手机注册成功
		{
			String phone=request.getParameter("receiver");
			if(!ValidateUtil.isInteger(phone))
			{
				logger.warn("手机号码错误:"+phone);
				return ShortMsgStatus.DATA_INVALIDATA;
			}
			int count=mobileService.addRegistMobile(phone);
			if(count>0)
			{
				return ShortMsgStatus.SUCCESS;
			}else
				return ShortMsgStatus.ERROR_SYS;
		}
		return ShortMsgStatus.ERROR_SYS;
	}
	
	/**
	 * 普通短信发送
	 * @param request
	 * @return
	 */
	public ShortMsgStatus sendMobile(HttpServletRequest request)
	{
		ShortMsg msg=assbileShortMsg(request);
		ShortMsgStatus status=mobileService.send(msg);
		return status;
	}
	
	/**
	 * 重新发送短信
	 * @author 胡礼波
	 * 2012-11-20 下午01:39:41
	 * @param request
	 * @return
	 */
	private ShortMsgStatus reSend(HttpServletRequest request)
	{
		String dataStr=request.getParameter("data");
		String datas[]=StringUtils.split(dataStr,",");
		Integer[] shortMsgIds=(Integer[])ConvertUtils.convert(datas,Integer.class);
		ShortMsgStatus status=mobileService.reSendShortMsg(shortMsgIds);
		return status;	
	}
	
	/**
	 * 根据请求组织短信 短信手机号 短信内容
	 * @author 胡礼波
	 * 2012-11-21 上午11:39:22
	 * @param request
	 * @return
	 */
	public ShortMsg assbileShortMsg(HttpServletRequest request)
	{
		ShortMsg msg=new ShortMsg();
		String phone=request.getParameter("receiver");
		String btype=request.getParameter("btype");	//业务类型
		String bcode=request.getParameter("bcode");			//业务代号
		msg.setMobile(phone);
		msg.setBtype(btype);
		msg.setBcode(bcode);
		msg.setContent(StringUtil.chartDecodeToUTF8(request.getMethod(),request.getParameter("content")));
		try
		{
			Caller caller=(Caller)ThreadLocalUtil.getData();
			msg.setChannel(caller.getShortMsgChannel());
		}catch (Exception e) {
		}		
		return msg;
	}

}
