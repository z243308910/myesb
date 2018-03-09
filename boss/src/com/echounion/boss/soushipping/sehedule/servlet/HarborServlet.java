package com.echounion.boss.soushipping.sehedule.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import com.echounion.boss.common.enums.RecordType;
import com.echounion.boss.entity.dto.Record;
import com.echounion.boss.servlet.ServletProxy;
import com.echounion.boss.soushipping.sehedule.SouShipingSchedule;

/**
 * 船期港口servlet
 * @author 胡礼波
 * 2013-10-17 下午12:58:11
 */
@WebServlet(name="HarborServlet",urlPatterns="/api/track/harbor",asyncSupported=true)
public class HarborServlet extends ServletProxy {

	private static final long serialVersionUID = 3057491738529998399L;

	@Autowired
	private SouShipingSchedule schedule;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException { 
		String data=schedule.findHarbor(request);
		Record record=Record.setSuccessRecord(data,false,RecordType.JSON);
		writeJsonDate(response, record);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
