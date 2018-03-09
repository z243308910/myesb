package com.echounion.boss.servlet;

import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import com.echounion.boss.common.constant.Constant;
import com.echounion.boss.common.json.JsonUtil;
import com.echounion.boss.entity.EsbApiLog;
import com.echounion.boss.entity.dto.Provider;
import com.echounion.boss.entity.dto.ResponseMessage;
import com.echounion.boss.logs.service.ILogService;

/**
 * 该Servlet主要是把ServletContext 添加到SpringContext中交给spring容器管理，从而可以通过依赖注入的方式在Servlet中获得Spring管理的对象
 * @author 胡礼波
 * 5:18:16 PM Sep 28, 2012
 */
public class ServletProxy extends HttpServlet {

	/**
	 * @author 胡礼波
	 * 5:19:42 PM Sep 28, 2012
	 */
	private static final long serialVersionUID = 7608314957180144281L;
	
	@Autowired
	@Qualifier("EsbApiLogServiceImpl")
	private ILogService<EsbApiLog> logService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,config.getServletContext());
	}
	
	/**
	 * 添加日志
	 * @author 胡礼波
	 * 2013-9-4 下午3:05:27
	 */
	protected void addEsbLog(final Provider provider,final ResponseMessage message)
	{
		EsbApiLog log=new EsbApiLog(provider,message.getCode(),message.getMessage());
		logService.addLog(log);	
	}
	
	/**
	 * 输出字符串数据
	 * @author 胡礼波
	 * 2012-11-5 下午01:57:53
	 * @param response
	 * @param data
	 */
	public void writeDate(ServletResponse response, Object obj)
	{
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding(Constant.ENCODEING_UTF8);
		PrintWriter out=null;
		try
		{
		out=response.getWriter();
		String dataStr=String.valueOf(obj);
		out.write(dataStr);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(out!=null)
			{
				out.flush();
				out.close();
			}
		}
	}
	
	/**
	 * 将对象转换为JSON格式输出
	 * @author 胡礼波
	 * 2012-11-5 下午01:58:39
	 * @param response
	 * @param data
	 */
	public void writeJsonDate(ServletResponse response, Object data)
	{
		String dataStr=JsonUtil.toJsonStringFilterPropter(data).toJSONString();
		writeDate(response,dataStr);
	}
	
}
