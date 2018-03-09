package com.echounion.bossmanager.common.util;

import com.echounion.bossmanager.common.enums.ParamType;
import com.echounion.bossmanager.entity.dto.ParamsDTO;

/**
 * HTML元素操作类
 * @author 胡礼波
 * 2013-3-11 下午05:44:42
 */
public class HtmlElementUtil {

	/**
	 * 普通输入框
	 * @author 胡礼波
	 * 2013-3-11 下午05:47:02
	 * @param param
	 * @return
	 */
	public static String getHtmlElement(ParamsDTO params)
	{
		String html=null;
		int typeId=params.getTypeId();
		if(typeId==ParamType.DATETIME.getId())
		{
			html=getDateTimeInput(params);
		}else if(typeId==ParamType.BOOLEAN.getId())
		{
			html=getCheckBoxInput(params);
		}else
		{
			html=getNumberInput(params);	
		}
		return html;
	}
	
	/**
	 * 表单提交按钮
	 * @author 胡礼波
	 * 2013-3-11 下午06:14:37
	 * @return
	 */
	public static String getFormBtn(String formName)
	{
		StringBuffer sb=new StringBuffer();
		sb.append("<div class='toolbar' align='center'>");
		sb.append("<input type='submit' value='执 行'/>");
		sb.append("<input type='reset' value='重 置'/>");		
		sb.append("</div>");
		return sb.toString();
	}
	
	/**
	 * 获得复选框类型的元素---boolean类型的
	 * @author 胡礼波
	 * 2013-3-11 下午06:01:12
	 * @return
	 */
	public static String getCheckBoxInput(ParamsDTO params)
	{
		return params.getName()+":<input name='"+params.getCode()+"' type='checkbox' >";
	}
	
	/**
	 * 获得普通的文本框
	 * @author 胡礼波
	 * 2013-3-11 下午05:52:57
	 * @return
	 */
	public static String getInput(ParamsDTO params)
	{
		return params.getName()+":<input name='"+params.getCode()+"' class='easyui-validatebox' data-options='required:"+params.isRequire()+"'>";
	}
	
	/**
	 * 获得只能输入数字类型的文本框
	 * @author 胡礼波
	 * 2013-3-11 下午05:59:36
	 * @param params
	 * @return
	 */
	public static String getNumberInput(ParamsDTO params)
	{
		return params.getName()+":<input name='"+params.getCode()+"' class='easyui-numberbox' data-options='required:"+params.isRequire()+"'>";
	}
	
	/**
	 * 获得时间类型的文本框
	 * @author 胡礼波
	 * 2013-3-11 下午05:52:57
	 * @return
	 */
	public static String getDateTimeInput(ParamsDTO params)
	{
		return params.getName()+":<input name='"+params.getCode()+"' class='easyui-datebox' data-options='required:"+params.isRequire()+"'>";
	}
}
