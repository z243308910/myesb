package com.echounion.bossmanager.action;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.alibaba.fastjson.JSONObject;
import com.echounion.bossmanager.common.enums.LoginFlag;
import com.echounion.bossmanager.common.page.Pager;
import com.echounion.bossmanager.common.security.annotation.ActionRightCtl;
import com.echounion.bossmanager.entity.SysUser;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 父类Action
 * @author 胡礼波
 * 2012-4-25 下午06:26:17
 */
@Controller("BaseAction")
@Scope("prototype")
public class BaseAction extends ActionSupport implements RequestAware,SessionAware{

	/**
	 * @author 胡礼波
	 * 2012-4-25 下午06:28:13
	 */
	private static final long serialVersionUID = 1L;
	public final static String JSON="json";
	public final static String REDIRECTLOGN="dir_login";
	private Map<String,Object> request;
	private Map<String,Object> session;
	private HttpServletRequest servletRequest;
	private HttpServletResponse servletResponse;
	private int rows;			//每页显示记录数
	private int page;			//当前页数
	private JSONObject jsonData;	//返回的数据---一般是列表用到
	
	
	public JSONObject getJsonData() {
		return jsonData;
	}

	public void setJsonData(JSONObject jsonData) {
		this.jsonData = jsonData;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	@Override
	@ActionRightCtl(login=LoginFlag.YES)
	public String execute() throws Exception {
		return SUCCESS;
	}


	/**
	 * 输出数据 通常是Ajax调用
	 * @author 胡礼波
	 * 10:27:05 AM Oct 17, 2012 
	 * @param data
	 * @throws Exception
	 */
	public void writeData(Object data)
	{
		PrintWriter out=null;
		try
		{
		out=getServletResponse().getWriter();
		String dataStr=String.valueOf(data);
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
	 * 实例化Pager对象---默认为最大Integer条数据
	 * @author 胡礼波
	 * 10:27:14 AM Oct 17, 2012
	 */
	public void instantPage()
	{
		Pager.setPager(new Pager(getPage(),getRows(),Integer.MAX_VALUE));
	}
	
	/**
	 * 没有资源
	 * @author 胡礼波
	 * 10:27:28 AM Oct 17, 2012 
	 * @return
	 */
	public String none()
	{
		return NONE;
	}
	
	/**
	 *  没有访问权限
	 * @author 胡礼波
	 * 10:27:34 AM Oct 17, 2012 
	 * @return
	 */
	public String noright()
	{
		return "noright";
	}
	
	public String login()
	{
		return LOGIN;
	}
	
	/**
	 * 获得servlet容器型的request
	 * @author 胡礼波
	 * 10:27:40 AM Oct 17, 2012 
	 * @return
	 */
	public HttpServletRequest getServletReqeust()
	{
		servletRequest=ServletActionContext.getRequest();
		try {
			servletRequest.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return servletRequest;
	}
	
	/**
	 * 获得ServletResponse
	 * @author 胡礼波
	 * 10:27:45 AM Oct 17, 2012 
	 * @return
	 */
	public HttpServletResponse getServletResponse()
	{
		servletResponse=ServletActionContext.getResponse();
		servletResponse.setContentType("text/html;charset=UTF-8");
		servletResponse.setCharacterEncoding("UTF-8");
		return servletResponse;
	}
	
	/**
	 * 获得servlet容器型的session
	 * @author 胡礼波
	 * 10:27:51 AM Oct 17, 2012 
	 * @return
	 */
	public HttpSession getServletSession()
	{
		HttpSession session=getServletReqeust().getSession(true);
		return session;
	}
	
	/**
	 * 获得Map类型的request
	 * @author 胡礼波
	 * 10:27:57 AM Oct 17, 2012 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getRequest() {
		request=request==null?(Map<String,Object>)ActionContext.getContext().get("request"):request;
		return request;
	}
	
	/**
	 * 获得map型的session
	 * @author 胡礼波
	 * 10:28:03 AM Oct 17, 2012 
	 * @return
	 */
	public Map<String, Object> getSession() {
		session=session==null?(Map<String,Object>)ActionContext.getContext().getSession():session;
		return session;
	}
	

	/**
	 * 获得登录的用户对象
	 * @author 胡礼波
	 * 10:28:16 AM Oct 17, 2012 
	 * @return
	 */
	public SysUser getLoginedUser()
	{
		return (SysUser) getSession().get("adminuser");
	}
	
	public void setRequest(Map<String, Object> request) {
		this.request=request;
	}
	public void setSession(Map<String, Object> session) {
		this.session=session;
	}
}
