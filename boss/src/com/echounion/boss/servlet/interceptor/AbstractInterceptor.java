package com.echounion.boss.servlet.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.echounion.boss.common.constant.Constant;
import com.echounion.boss.common.json.JsonUtil;
import com.echounion.boss.entity.dto.Record;

/**
 * 抽象类拦截器
 * @author 胡礼波
 * 2012-11-2 下午01:45:23
 */
public abstract class AbstractInterceptor implements Interceptor{

	/**
	 * 返回数据给调用者
	 * @author 胡礼波
	 * 2012-11-2 上午09:55:18
	 * @param obj
	 * @param response
	 */
	public void writeJsonData(Record record,ServletResponse response)
	{
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding(Constant.ENCODEING_UTF8);
		PrintWriter out=null;
		try
		{
		out=response.getWriter();
		String dataStr=JsonUtil.toJsonStringFilterPropter(record).toJSONString();
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

	public boolean intercept(HttpServletRequest request, HttpServletResponse response, Interceptor chain) throws IOException, ServletException {
		return false;
	}
}
