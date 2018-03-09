package com.echounion.boss.common.util;

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
	 * 判断是否是合法的IP地址
	 * @author 胡礼波
	 * 2013-7-25 下午4:20:08
	 * @param ip
	 * @return
	 */
	public static boolean isIp(String ip)
	{
		if(StringUtils.isBlank(ip))
		{
			return false;
		}
		boolean flag = false;
        ip=ip.trim();  
        if(ip.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")){  
            String s[] = ip.split("\\.");  
            if(Integer.parseInt(s[0])<=255)
            {
                if(Integer.parseInt(s[1])<=255)
                {
                    if(Integer.parseInt(s[2])<=255)
                    {
                        if(Integer.parseInt(s[3])<=255)
                        {
                            flag = true;
                        }
                    }
                }
            }
        }  
        return flag;  
	}
	
	public static void main(String[] args) {
		System.out.println(isIp("223.255.242.39"));
	}
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
}
