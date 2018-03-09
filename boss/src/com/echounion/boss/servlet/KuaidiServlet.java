package com.echounion.boss.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.echounion.boss.Tracking.kuaidi.KuaidiAdapter;
import com.echounion.boss.common.enums.RecordType;
import com.echounion.boss.common.enums.Status;
import com.echounion.boss.core.SystemConfig;
import com.echounion.boss.core.cache.UrlContainer;
import com.echounion.boss.entity.dto.Provider;
import com.echounion.boss.entity.dto.Record;
import com.echounion.boss.entity.dto.ResponseMessage;

/**
 * 快递servlet
 * @author 胡礼波
 * 2013-3-4 上午9:29:14
 */
@WebServlet(name="KuaidiServlet",urlPatterns="/api/track/express",asyncSupported=true)
public class KuaidiServlet extends ServletProxy {

	private static final long serialVersionUID = 590281059813712200L;

	@Autowired
	private KuaidiAdapter adapter ;
	

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String data=adapter.execute(request);
		Record record=Record.setSuccessRecord(data,false,RecordType.JSON);
		
		Provider provider=UrlContainer.getServiceProvider("/api/track/express");
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
