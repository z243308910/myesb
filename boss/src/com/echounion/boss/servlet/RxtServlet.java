package com.echounion.boss.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.echounion.boss.common.constant.Constant;
import com.echounion.boss.common.enums.RecordType;
import com.echounion.boss.common.enums.RtxStatus;
import com.echounion.boss.common.enums.Status;
import com.echounion.boss.core.SystemConfig;
import com.echounion.boss.core.cache.UrlContainer;
import com.echounion.boss.core.rtx.Rtx;
import com.echounion.boss.core.rtx.service.IRtxService;
import com.echounion.boss.entity.dto.Provider;
import com.echounion.boss.entity.dto.Record;
import com.echounion.boss.entity.dto.ResponseMessage;

/**
 * RTX接口SERVLET
 * @author 胡礼波
 * 2012-11-19 下午12:27:24
 */
@WebServlet(name="RxtServlet",urlPatterns="/api/message/rtx",asyncSupported=true)
public class RxtServlet extends ServletProxy {

	private static final long serialVersionUID = 2444783366487438503L;
	
	private Logger logger=Logger.getLogger(RxtServlet.class);
	
	@Autowired
	private IRtxService rtxService;

	public void init() throws ServletException {
		logger.info(".........系统RTX接口初始化成功.........");
	}
	
	public RxtServlet() {super();}
	
	public void destroy() {super.destroy();}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding(Constant.ENCODEING_UTF8);
		String action=request.getParameter("action");
		RtxStatus status=doAction(request, action);
		Record record=null;
		if(status.getCode().equals(RtxStatus.SUCCESS.getCode()))		//发送成功
		{
			record=Record.setSuccessRecord("发送成功",false,RecordType.TEXT);
		}
		else
		{
			record=Record.setFailRecord(status.getCode(), status.getName(),RecordType.TEXT);
			logger.info("RTX发送失败，系统状态玛"+status.getCode()+"  "+status.getName());
		}
		
		Provider provider=UrlContainer.getServiceProvider("/api/message/rtx");
		provider.setServerIp(SystemConfig.SERVERIP);			//这里需要重构
		ResponseMessage message=new ResponseMessage(Status.SUCCESS, "HTTP-200",record.getMessage());
		super.addEsbLog(provider, message);
		
		writeJsonDate(response, record);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	/**
	 * 公共的邮件发送不使用模版
	 * @param request
	 * @param subject 主题
	 * @param address 收件人地址
	 * @param content 邮件内容
	 * @return
	 */
	private RtxStatus sendRtx(HttpServletRequest request)
	{
		RtxStatus status=null;
		try
		{
			Rtx rtx=assbileMesssage(request);
			status=rtxService.send(rtx);
		}catch (Exception e) {
			return RtxStatus.SYS_UNKNOW_ERROR;
		}
		return status;
	}
	
	/**
	 * 组织RTX基本信息
	 * @author 胡礼波
	 * 2013-3-14 下午2:07:25
	 * @param request
	 * @return
	 */
	private Rtx assbileMesssage(HttpServletRequest request)
	{
		Rtx rtx=new Rtx();
		rtx.setTitle(request.getParameter("title"));
		rtx.setContent(request.getParameter("content"));
		rtx.setBcode(request.getParameter("bcode"));
		rtx.setBtype(request.getParameter("btype"));
		rtx.setReceiver(request.getParameter("receiver"));
		rtx.setPhone(request.getParameter("phone"));
		return rtx;
	}
	
	
	public RtxStatus doAction(HttpServletRequest request,String action)
	{
		if(null==action)
		{
			return RtxStatus.ACTION_NULL;
		}
		else if(action.equals("send"))		//发送RTX通知
		{
			return sendRtx(request);
		}
		return RtxStatus.ACTION_NULL;
	}

}
