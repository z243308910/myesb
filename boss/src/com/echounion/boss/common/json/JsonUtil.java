package com.echounion.boss.common.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;

/**
 * Json工具类
 * @author 胡礼波
 * 10:08:35 AM Oct 17, 2012
 */
public class JsonUtil {


	/**
	 * 把对象转为字符串
	 * @author 胡礼波
	 * 2012-11-20 上午11:56:41
	 * @param jsonObj
	 * @return
	 */
	public static String toJsonString(Object object)
	{
		return toJsonStringFilterPropter(object).toJSONString();
	}
	
	/**
	 * 传入一个实体对象过滤Hiibernate属性返回Json对象
	 * @author 胡礼波
	 * 2012-10-31 下午01:39:48
	 * @param object
	 * @return
	 */
	public static JSONObject toJsonStringFilterPropter(Object object)
	{
		String jsonStr = filter(object);
		JSONObject jb=JSON.parseObject(jsonStr);
		return jb;
	}	
	
	/**
	 * @author 胡礼波
	 * 10:09:44 AM Oct 17, 2012 
	 * @param object
	 * @return
	 */
	public static JSONArray toJsonStringFilterPropterForArray(Object object)
	{
		String jsonStr = filter(object);
		JSONArray jb=JSON.parseArray(jsonStr);
		return jb;
	}


	/**
	 * @author 胡礼波
	 * 10:09:50 AM Oct 17, 2012 
	 * @param object
	 * @return
	 */
	private static String filter(Object object){
		String jsonStr=null;
		try {
			PropertyFilter filter=new PropertyFilter()
			{
				public boolean apply(Object source, String name, Object value) {
						return true;
				}
			};
			SerializeWriter writer=new SerializeWriter();
			JSONSerializer serializer=new JSONSerializer(writer);
			serializer.getPropertyFilters().add(filter);
			serializer.write(object);
			jsonStr = writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("json过滤属性出错！"); 
		}
		return jsonStr;
	}
}
