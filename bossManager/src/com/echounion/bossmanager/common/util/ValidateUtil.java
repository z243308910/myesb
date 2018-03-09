package com.echounion.bossmanager.common.util;

import java.util.regex.Pattern;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import org.apache.commons.lang.StringUtils;

/**
 * 数据验证工具类
 * @author 胡礼波
 * Dec 6, 2012 2:48:01 PM
 */
public class ValidateUtil {

	/**
	 * 验证是否是邮件格式
	 * @author 胡礼波
	 * Dec 6, 2012 2:51:06 PM
	 * @param email
	 * @return true是邮件格式 false反之
	 */
	public static boolean isEmail(String email)
	{
		boolean flag=false;
		if(StringUtils.isEmpty(email))
		{
			return false;
		}
		try
		{
			InternetAddress address=new InternetAddress(email);
	        address.validate();
	        flag=true;
		}catch (AddressException e){
			flag=false;
		}
        return flag;
	}
	
	/**
	 * 验证是否是整形
	 * @author 胡礼波
	 * Dec 6, 2012 3:18:23 PM
	 * @param number
	 * @return true返回是整形 false反之
	 */
	public static boolean isInteger(String number)
	{
		if(StringUtils.isEmpty(number))
		{
			return false;
		}
		return Pattern.matches("\\d*",number);
	}
	
	/**
	 * 验证是否是合法的url
	 * @author 胡礼波
	 * 2013-3-5 上午10:03:43
	 * @param url
	 * @return
	 */
	public static boolean isUrl(String url)
	{
		String exp="^(http|https|ftp)\\://([a-zA-Z0-9\\.\\-]+(\\:[a-zA-"   
	           + "Z0-9\\.&%\\$\\-]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{"   
	           + "2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}"   
	           + "[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|"   
	           + "[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-"   
	           + "4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0"   
	           + "-9\\-]+\\.)*[a-zA-Z0-9\\-]+\\.[a-zA-Z]{2,4})(\\:[0-9]+)?(/"   
	           + "[^/][a-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&%\\$\\=~_\\-@]*)*$";
	  	Pattern p = Pattern.compile(exp);
		return url==null?false:p.matcher(url).matches();
	}
}
