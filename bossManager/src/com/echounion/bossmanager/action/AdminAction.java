package com.echounion.bossmanager.action;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.echounion.bossmanager.common.constant.Constant;
import com.echounion.bossmanager.common.enums.LoginFlag;
import com.echounion.bossmanager.common.json.JsonUtil;
import com.echounion.bossmanager.common.security.annotation.ActionRightCtl;
import com.echounion.bossmanager.common.security.encoder.PwdEncoder;
import com.echounion.bossmanager.common.util.DateUtil;
import com.echounion.bossmanager.entity.SysUser;
import com.echounion.bossmanager.service.IAdminService;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * 管理员相关功能Action
 * @author 胡礼波
 * 10:30:35 AM Oct 22, 2012
 */
@Controller("AdminAction")
@Scope("prototype")
public class AdminAction extends BaseAction {

	/**
	 * @author 胡礼波
	 * 10:30:58 AM Oct 22, 2012 
	 */
	private static final long serialVersionUID = -1942937928494625662L;
	private Logger logger=Logger.getLogger(AdminAction.class);

	private SysUser adminUser;
	@Autowired
	private ImageCaptchaService imageCaptchaService;
	@Autowired
	private IAdminService adminService;
	@Autowired
	private PwdEncoder pwdEncoder;
	
	
	@ActionRightCtl(login=LoginFlag.YES)
	public String getAllAdmin()
	{
		List<SysUser> logsList=adminService.getAllAdmin();
		JSONObject jsonObject=JsonUtil.toJsonStringFilterPropter(logsList,200);
		setJsonData(jsonObject);
		return JSON;
	}
	
	/**
	 * 检查用户名是否存在
	 * @author 胡礼波
	 * 2012-12-14 下午03:32:38
	 * @return
	 */
	public String checkExist()
	{
		boolean flag=false;
		String username=getServletReqeust().getParameter("code");
		try
		{
			flag=adminService.usernameExist(username);
		}
		catch (Exception e) {
			logger.error("检查用户名失败，"+e);
		}
		writeData(flag);
		return null;
	}
	
	/**
	 * 删除管理员
	 * @author 胡礼波
	 * 2012-12-10 下午03:07:40
	 * @return
	 */
	public String delAdmins()
	{
		String dataStr=getServletReqeust().getParameter("data");
		String datas[]=StringUtils.split(dataStr,",");
		Integer[] ids=(Integer[])ConvertUtils.convert(datas,Integer.class);
		int count=adminService.delAdmins(ids);
		writeData(count);
		return null;
	}
	
	/**
	 * 添加管理员
	 * @author 胡礼波
	 * 2012-12-10 下午03:22:36
	 * @return
	 */
	public String addAdmin()
	{
		boolean flag=false;
		try
		{
			flag=adminService.addAdmin(adminUser);
		}catch (Exception e) {
			logger.error("添加管理员失败!"+e);
		}
		writeData(flag?Constant.OP_SUCCESS:Constant.OP_FAIL);
		return null; 
	}
	
	/**
	 * 管理员登录
	 * @author 胡礼波
	 * 10:32:34 AM Oct 22, 2012 
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.NO)
	public String submit()
	{
		String captcha=getServletReqeust().getParameter("captcha");
		String userName=getServletReqeust().getParameter("userName");
		String password=getServletReqeust().getParameter("password");
		String sessionId=getServletSession().getId();
		if(!imageCaptchaService.validateResponseForID(sessionId,captcha))
		{
			getRequest().put("loginerror","<font color='red' >验证码错误!</font>");
			return LOGIN;
		}
		SysUser admin=adminService.login(userName, password);
		if(null==admin)
		{
			getRequest().put("loginerror", "<font color='red' >用户名或密码错误!</font>");
			return LOGIN;
		}
		getSession().put("adminuser",admin);
		logger.info(getLoginedUser().getUserName()+" 在"+DateUtil.formatterDateTime(new Date())+" 成功登录!");
		return SUCCESS;
	}
	
	/**
	 * 管理员安全退出
	 * @author 胡礼波
	 * 10:32:55 AM Oct 22, 2012 
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.NO)
	public String logOut()
	{
		SysUser admin=getLoginedUser();
		if(admin!=null)
		{
		logger.info(getLoginedUser().getUserName()+" 在"+DateUtil.formatterDateTime(new Date())+" 成功登出!");
		}
		getServletSession().invalidate();
		return LOGIN;
	}

	/**
	 * 查看管理员信息
	 * @author 张霖
	 * 2013-2-1下午4:50:36
	 * @return
	 */
	public String viewAdmin()
	{
		String userName = ConvertUtils.convert(getServletReqeust().getParameter("userName"));
		 SysUser adminInfo = adminService.getAdminByUserName(userName); 
		 getRequest().put("adminUser", adminInfo);
		return "admin_view";
	}
	
	/**
	 * 编辑管理员信息
	 * @author 张霖
	 * 2013-2-1下午5:25:19
	 * @return
	 */
	public String editAdmin()
	{
		int result = 0;
		String pwd = ConvertUtils.convert(getServletReqeust().getParameter("password"));
		try{
			pwd=StringUtils.isEmpty(pwd)?adminUser.getPassword():pwdEncoder.encodePassword(pwd);
			adminUser.setPassword(pwd);
			result = adminService.editAdmin(adminUser);
		}
		catch(Exception e)
		{
			logger.error("编辑管理员信息出错："+e);
		}
		writeData(result>0?Constant.OP_SUCCESS:Constant.OP_FAIL);
		return null;
	}
	
	@Override
	@ActionRightCtl(login=LoginFlag.NO)
	public String execute() throws Exception {
		return submit();
	}

	public SysUser getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(SysUser adminUser) {
		this.adminUser = adminUser;
	}
}
