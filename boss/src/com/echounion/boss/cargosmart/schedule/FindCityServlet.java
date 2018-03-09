package com.echounion.boss.cargosmart.schedule;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.echounion.boss.common.enums.RecordType;
import com.echounion.boss.entity.dto.Record;
import com.echounion.boss.servlet.ServletProxy;

/**
 * 查找城市
 * @author 胡礼波
 * 2013-10-30 上午9:54:54
 */
@WebServlet(name="FindCityServlet",urlPatterns="/api/track/findport",asyncSupported=true)
public class FindCityServlet extends ServletProxy {

	@Autowired
	private CargosmartSchedule schedule;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String data=schedule.findPort(req);
		Record record=Record.setSuccessRecord(data,false,RecordType.JSON);
		writeJsonDate(resp, record);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	/**
	 * @author 胡礼波
	 * 2013-10-30 上午9:55:07
	 */
	private static final long serialVersionUID = -5136410024158426249L;

}
