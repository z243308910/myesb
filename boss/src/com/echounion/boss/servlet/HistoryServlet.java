package com.echounion.boss.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.echounion.boss.common.enums.RecordType;
import com.echounion.boss.common.enums.Status;
import com.echounion.boss.common.enums.SystemStatus;
import com.echounion.boss.common.json.JsonUtil;
import com.echounion.boss.core.SystemConfig;
import com.echounion.boss.core.cache.UrlContainer;
import com.echounion.boss.core.history.service.IDataHistoryService;
import com.echounion.boss.entity.dto.DataHistoryDTO;
import com.echounion.boss.entity.dto.Provider;
import com.echounion.boss.entity.dto.Record;
import com.echounion.boss.entity.dto.ResponseMessage;

/**
 * 业务记录Servlet
 * @author 胡礼波
 * 2013-3-30 下午3:23:40
 */
@WebServlet(name="HistoryServlet",urlPatterns="/api/message/history",asyncSupported=true)
public class HistoryServlet  extends ServletProxy {

	/**
	 * @author 胡礼波
	 * 2013-3-25 下午2:07:38
	 */
	private static final long serialVersionUID = -4944181380243356858L;
	private Logger logger=Logger.getLogger(HistoryServlet.class);
	

	@Autowired
	private IDataHistoryService dataHistory;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action=request.getParameter("action");
		SystemStatus status=doAction(action,request);
		Record record=null;
		if(status.getCode().equals(SystemStatus.SUCCESS.getCode()))							//发送成功
		{
			String data=execute(request);
			record=Record.setSuccessRecord(data,false,RecordType.JSON);
		}
		else
		{
			record=Record.setFailRecord(status.getCode(),status.getName(),RecordType.TEXT);
			logger.info("历史记录提取失败，系统状态码"+status.getCode()+"   "+status.getName());
		}
		
		Provider provider=UrlContainer.getServiceProvider("/api/message/history");
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
	 * 执行操作
	 * @author 胡礼波
	 * 2013-3-25 下午2:19:43
	 * @param request
	 * @return
	 */
	public String execute(HttpServletRequest request)
	{
		String btype=request.getParameter("btype");
		String bcode=request.getParameter("bcode");
		List<DataHistoryDTO> list=dataHistory.getInfoHistory(btype, bcode);
		String data=JsonUtil.toJsonStringFilterPropterForArray(list).toJSONString();
		return data;
	}
	
	/**
	 * 根据动作名称调用不同的邮件模版
	 * @author 胡礼波
	 * 2012-11-17 上午10:47:50
	 * @param action
	 * @return
	 */
	private SystemStatus doAction(String action,HttpServletRequest request)
	{
		if(null==action)
		{
			logger.warn("业务历史记录Action为空");
			return SystemStatus.DATA_ERROR;
		}
		if(action.equals("business"))			//业务数据查询
		{
			String btype=request.getParameter("btype");		//业务类型
			String bcode=request.getParameter("bcode");		//业务号
			if(btype==null)
			{
				logger.warn("查询的业务类型数据不存在");
				return SystemStatus.DATA_ERROR;
			}
			if(StringUtils.isEmpty(bcode))
			{
				logger.warn("查询的业务数据号为空!");
				return SystemStatus.DATA_ERROR;
			}
			return SystemStatus.SUCCESS;
		}else
		{
			return SystemStatus.SYS_UNKNOW_ERROR;
		}
	}
}
