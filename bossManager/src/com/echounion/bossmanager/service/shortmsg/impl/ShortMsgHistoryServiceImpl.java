package com.echounion.bossmanager.service.shortmsg.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.echounion.bossmanager.common.enums.ShortMsgStatus;
import com.echounion.bossmanager.common.security.Boss;
import com.echounion.bossmanager.common.security.annotation.ActionModel;
import com.echounion.bossmanager.common.util.StringUtil;
import com.echounion.bossmanager.dao.shortmsg.IShortMsgHistoryDao;
import com.echounion.bossmanager.entity.MobileHistory;
import com.echounion.bossmanager.entity.MobileList;
import com.echounion.bossmanager.entity.dto.BossProperty;
import com.echounion.bossmanager.entity.dto.Record;
import com.echounion.bossmanager.service.shortmsg.IShortMsgHistoryService;

@Service
@Transactional(propagation=Propagation.SUPPORTS)
@ActionModel(description="短信日志")
public class ShortMsgHistoryServiceImpl implements IShortMsgHistoryService {

	@Autowired
	private IShortMsgHistoryDao shortMsgHistoryDao;
	private Logger logger=Logger.getLogger(ShortMsgHistoryServiceImpl.class);
	
	public int getCount(MobileHistory history) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("phoneNo",history.getPhoneNo());
		params.put("btype",history.getBtype());
		params.put("bcode",history.getBcode());
		return shortMsgHistoryDao.getCount(params);
	}

	public List<MobileHistory> getMobileHistory(MobileHistory history) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("phoneNo",history.getPhoneNo());
		params.put("btype",history.getBtype());
		params.put("bcode",history.getBcode());
		return shortMsgHistoryDao.getAll(params);
	}

	public int delMobileHistory(Integer[] ids) {
		return shortMsgHistoryDao.removeObject(ids,"id");
	}

	@ActionModel(description="重新发送短信")
	public String editReSendShortMsg(Integer[] shortMsgIds) {
		if(ArrayUtils.isEmpty(shortMsgIds))
		{
			logger.info("没有选择要重新发送的短信");
			return "没有选中要重新发送的短信";
		}
		String url=BossProperty.getInstance().getShortMsgResendUrl();
		String result=null;
		Map<String,Object> mappara=new HashMap<String, Object>();
		mappara.put("data",StringUtil.convertArray(shortMsgIds));		
		try {
			Record record=new Boss().requestBoss(url,mappara);
			if(record.getStatus()==Record.SUCCESS)		//成功
			{
				result="短信已经发出";
			}
			else										//失败
			{
				record.getErrorCode();
				result=ShortMsgStatus.getStatus(record.getErrorCode()).getName();
				result=result==null?"未知异常,发送失败!":result+",发送失败!";
			}
			
		} catch (IOException e) {
			logger.error("重新发送短信网络异常"+e);
			result="网络异常";
		}catch (Exception e) {
			logger.error("重新发送短信系统异常"+e);
			result="系统异常";
		}
		return result;
	}

	@ActionModel(description="删除注册手机")
	public int delRegMobile(Integer[] ids) {
		if(ArrayUtils.isEmpty(ids))
		{
			return 0;
		}
		return shortMsgHistoryDao.delRegMobile(ids);
	}

	public int getRegCount() {
		return shortMsgHistoryDao.getRegCount();
	}

	public List<MobileList> getRegMobile() {
		return shortMsgHistoryDao.getRegMobile();
	}

}
