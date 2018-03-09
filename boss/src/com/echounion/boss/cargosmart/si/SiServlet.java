package com.echounion.boss.cargosmart.si;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import com.echounion.boss.cargosmart.service.ISiService;
import com.echounion.boss.common.enums.Status;
import com.echounion.boss.core.SystemConfig;
import com.echounion.boss.core.cache.UrlContainer;
import com.echounion.boss.entity.dto.Provider;
import com.echounion.boss.entity.dto.Record;
import com.echounion.boss.entity.dto.ResponseMessage;
import com.echounion.boss.servlet.ServletProxy;

/**
 * CargoSmart SI接口
 * @author 胡礼波
 * 2013-10-14 上午11:59:39
 */
@WebServlet(name="SiServlet",urlPatterns="/api/cs/si",asyncSupported=true)
public class SiServlet extends ServletProxy{

	@Autowired
	private ISiService siService;
	
	/**
	 * @author 胡礼波
	 * 2013-10-14 下午12:00:41
	 */
	private static final long serialVersionUID = -1542518476387812592L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName=request.getParameter("fileName");
		String siXml=request.getParameter("siXml");
		Record record=siService.postSiData(fileName,siXml);
		
		Provider provider=UrlContainer.getServiceProvider("/api/cs/si");
		provider.setServerIp(SystemConfig.SERVERIP);
		ResponseMessage message=new ResponseMessage(Status.SUCCESS, "HTTP-200",record.getMessage());
		super.addEsbLog(provider, message);
		
		writeJsonDate(response, record);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
