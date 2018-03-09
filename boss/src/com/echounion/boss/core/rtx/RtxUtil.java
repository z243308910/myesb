package com.echounion.boss.core.rtx;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.echounion.boss.common.enums.Status;
import com.echounion.boss.common.util.StringUtil;
import com.echounion.boss.core.esb.convertor.ConvertorFactory;
import com.echounion.boss.core.esb.convertor.impl.HttpConvertor;
import com.echounion.boss.core.rtx.service.IRtxService;
import com.echounion.boss.entity.RtxHistory;
import com.echounion.boss.entity.SysLog;
import com.echounion.boss.entity.dto.ResponseMessage;
import com.echounion.boss.logs.service.ILogService;

/**
 * RTX工具类
 * @author 胡礼波
 * 2013-7-22 下午4:37:48
 */
@Component
public class RtxUtil {

	private static Logger logger = Logger.getLogger(RtxUtil.class);
	private static TaskExecutor gatewayTaskExecutor;
	private static IRtxService rtxService;
	private static ILogService<SysLog> logService;
	
	@Autowired
	public void setRtxService(IRtxService rtxService) {
		RtxUtil.rtxService = rtxService;
	}
	
	@Autowired
	@Qualifier("gatewayTaskExecutor")
	public void setGatewayTaskExecutor(TaskExecutor gatewayTaskExecutor) {
		RtxUtil.gatewayTaskExecutor = gatewayTaskExecutor;
	}
	

	/**
	 * 发送通知
	 * @author 胡礼波
	 * 2013-3-17 下午2:29:42
	 * @param rtx
	 * @return
	 */
	public static void send(Rtx... rtxs)throws Exception
	{
		StringBuffer sb=new StringBuffer();
			sb.append("sendnotify.cgi");
			
//			String url=RtxConfig.getRtxServerUlr()+ sb.toString();
//			map.put("receiver",StringUtil.chartEncodeToGBK(rtx.getReceiver()));
//			map.put("title",StringUtil.chartEncodeToGBK(rtx.getTitle()));
//			map.put("msg",StringUtil.chartEncodeToGBK(rtx.getContent()));
			
			final String url=RtxConfig.getRtxServerUlr();//"http://www.cntrans.cn:9000/mc/send.do";
			for (final Rtx rtx : rtxs) {
				gatewayTaskExecutor.execute(new Runnable() {
					@Override
					public void run() {
						Map<String, Object> map=new HashMap<String, Object>();
						map.put("app_code","BOSS");
						map.put("channel_code","rtx");
						map.put("receiver",StringUtil.chartEncodeToUTF8(rtx.getReceiver()));
						map.put("sender","BOSS");
						map.put("subject",StringUtil.chartEncodeToUTF8(rtx.getTitle()));
						map.put("content",StringUtil.chartEncodeToUTF8(rtx.getContent()));
						try
						{
							int index = 1;
							ConvertorFactory factory=new HttpConvertor();
							do {
								try {
									ResponseMessage resMsg=factory.invoke(url,map);
									if(resMsg.getStatus()==Status.FAIL)			//失败
									{
										throw new Exception("RTX发送失败！"+resMsg.getCode());
									}
									rtxService.editRtxHistoryStatus(rtx.getHistoryId(),RtxHistory.SEND_SUCCESS);//修改状态为成功		
									logger.info("RTX<"+rtx.getReceiver()+">发送成功!");
									logService.addLog(new SysLog("RTX服务","发送RTX","RTX("+rtx.getReceiver()+")第"+index+"次发送成功!"));
									break;
								} catch (Exception e) {
									rtxService.editRtxHistoryStatus(rtx.getHistoryId(),RtxHistory.SEND_FAIL);//修改状态为失败
									if(index<3)
									{
										logger.error("RTX<"+rtx.getReceiver()+">发送第"+index+"次失败，准备第"+(index+1)+"次尝试发送!" + e);
									}
									else
									{
										logger.error("RTX<"+rtx.getReceiver()+">发送第"+index+"次失败，发送停止!" + e);
									}
									logService.addLog(new SysLog("RTX服务","发送RTX","RTX("+rtx.getReceiver()+")发送第"+index+"次失败!"));
									index++;
									try{Thread.sleep(5000*2);}catch (Exception ex) {logger.error(ex);}
								}
							} while (index <=3);
						}catch (Exception e) {
							logger.error("RTX消息发送异常",e);
						}
					}
				});
			}
	}
	
	@Autowired
	@Qualifier("SysLogServiceImpl")
	public void setLogService(ILogService<SysLog> logService) {
		RtxUtil.logService = logService;
	}
	
}
