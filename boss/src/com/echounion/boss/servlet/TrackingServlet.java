package com.echounion.boss.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.echounion.boss.Tracking.TrackingAdapter;
import com.echounion.boss.common.enums.RecordType;
import com.echounion.boss.common.enums.Status;
import com.echounion.boss.core.SystemConfig;
import com.echounion.boss.core.cache.UrlContainer;
import com.echounion.boss.entity.dto.Provider;
import com.echounion.boss.entity.dto.Record;
import com.echounion.boss.entity.dto.ResponseMessage;

/**
 * 数据采集Servlet
 * @author 胡礼波
 * 2012-11-3 下午04:39:34
 */
@WebServlet(name="TrackingServlet",urlPatterns="/api/track/tracking",asyncSupported=true)
public class TrackingServlet extends ServletProxy {


	/**
	 * @author 胡礼波
	 * 2012-11-3 下午04:40:23
	 */
	private static final long serialVersionUID = -5502319091670023111L;
	
	@Autowired
	private TrackingAdapter adapter;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String data=adapter.execute(request);
		Record record=Record.setSuccessRecord(data,false,RecordType.JSON);
		
		Provider provider=UrlContainer.getServiceProvider("/api/track/tracking");
		provider.setServerIp(SystemConfig.SERVERIP);			//这里需要重构
		ResponseMessage message=new ResponseMessage(Status.SUCCESS, "HTTP-200",record.getMessage());
		super.addEsbLog(provider, message);
		
		writeJsonDate(response, record);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
