package com.echounion.bossmanager.action;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.echounion.bossmanager.common.enums.LoginFlag;
import com.echounion.bossmanager.common.security.annotation.ActionRightCtl;
import com.echounion.bossmanager.service.email.IEmailHistoryService;
import com.echounion.bossmanager.service.shortmsg.IShortMsgHistoryService;
import com.echounion.bossmanager.service.softserver.IServerService;
import com.echounion.bossmanager.service.softserver.ISoftwareService;

/**
 * 欢迎页Action
 * @author 胡礼波
 * 2012-12-13 下午02:22:56
 */
@Controller("indexAction")
@Scope("prototype")
public class IndexAction extends BaseAction {

	/**
	 * @author 胡礼波
	 * 2012-12-13 下午02:23:26
	 */
	private static final long serialVersionUID = 3705530267980894429L;
	
	@Autowired
	private ISoftwareService softWareService;
	@Autowired
	private IServerService serverService;
	@Autowired
	private IShortMsgHistoryService mobileService;
	@Autowired
	private IEmailHistoryService emailService;

	@ActionRightCtl(login=LoginFlag.YES)
	public String execute()
	{
		int softCount=softWareService.getCount();
		int serverCount=serverService.getCount();
		int emailCount=emailService.getRegCount();
		int mobileCount=mobileService.getRegCount();
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("softCount", softCount);
		map.put("serverCount", serverCount);
		map.put("emailCount", emailCount);
		map.put("mobileCount", mobileCount);
		getRequest().put("mapData",map);
		return "welcome";
	}
}
