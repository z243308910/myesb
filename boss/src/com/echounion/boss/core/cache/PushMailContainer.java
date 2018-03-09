package com.echounion.boss.core.cache;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.echounion.boss.core.email.EmailConfig;
import com.echounion.boss.core.system.ISysConfigService;

/**
 * 邮件推广使用 保存每个通道邮件配置信息
 * @author 胡礼波
 * 2013-8-8 下午5:18:39
 */
public class PushMailContainer {

	private static Logger logger=Logger.getLogger(PushMailContainer.class);
	
	private static LinkedList<EmailConfig> list=new LinkedList<>();
	
	
	public static LinkedList<EmailConfig> getMailContainer() {
		return list;
	}

	/**
	 * 判断是否存在指定通道的邮件
	 * @author 胡礼波
	 * 2013-8-9 上午10:54:52
	 * @param channel
	 * @return
	 */
	public static boolean isExist(String channel)
	{
		boolean flag=false;
		for (EmailConfig config:list) {
			if(config.getChannel().equals(channel))
			{
				flag=true;
				break;
			}
		}
		if(!flag)
		{
			logger.warn("指定的邮件通道<"+channel+">不存在!");
		}
		return flag;
	}
	
	/**
	 * 初始化队列
	 * @author 胡礼波
	 * 2013-8-8 下午5:20:35
	 * @param servletCtx
	 */
	public static void initPushMailContainer(ServletContext servletCtx)
	{
		WebApplicationContext ctx= WebApplicationContextUtils.getRequiredWebApplicationContext(servletCtx);
		ISysConfigService configService=ctx.getBean(ISysConfigService.class);
		List<EmailConfig> arrayList=configService.getEmailConfig();
		list.clear();
		list.addAll(arrayList);
		logger.info("....................邮件推广配置更新成功....................");
	}
	
	/**
	 * 定时器调用更新接口路由器
	 * @author 胡礼波
	 * 2013-7-23 下午2:28:41
	 */
	public void listener()
	{
		ServletContext servletCtx=ContextLoaderListener.getCurrentWebApplicationContext().getServletContext();
		initPushMailContainer(servletCtx);
	}
}
