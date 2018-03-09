package com.echounion.boss.core.history.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echounion.boss.common.util.StringUtil;
import com.echounion.boss.core.history.service.IDataHistoryService;
import com.echounion.boss.core.security.annotation.ActionModel;
import com.echounion.boss.entity.dto.DataHistoryDTO;
import com.echounion.boss.persistence.email.EmailMapper;
import com.echounion.boss.persistence.rtx.RtxMapper;
import com.echounion.boss.persistence.shortmsg.MobileMapper;

@Service
@ActionModel(description="业务数据记录")
@Transactional(propagation=Propagation.REQUIRED)
public class DataHistoryServiceImpl implements IDataHistoryService {

	@Autowired
	private EmailMapper emailMapper;
	@Autowired
	private MobileMapper mobileMapper;
	@Autowired
	private RtxMapper rtxMapper;
	
	@Override
	public List<DataHistoryDTO> getInfoHistory(String btype, String bcode) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("btype", btype);
		map.put("bcode",bcode);
		
		List<DataHistoryDTO> datas=new ArrayList<>();
		DataHistoryDTO tempData=null;
		List<Map<String,Object>> mobileList=mobileMapper.getMobileHistoryByParam(map);
		for (Map<String, Object> mapData : mobileList) {
			tempData=new DataHistoryDTO();
			tempData.setContent(StringUtil.trimHtmlTag(String.valueOf(mapData.get("content"))));
			tempData.setReceiver(String.valueOf(mapData.get("phone_no")));
			tempData.setSendTime(String.valueOf(mapData.get("active_time")));
			tempData.setStatus(String.valueOf(mapData.get("status")));
			tempData.setType(1);
			datas.add(tempData);
		}
		
		List<Map<String,Object>> emailList=emailMapper.getMailHistoryByParam(map);
		for (Map<String, Object> mapData : emailList) {
			tempData=new DataHistoryDTO();
			tempData.setContent(StringUtil.trimHtmlTag(String.valueOf(mapData.get("content"))));
			tempData.setReceiver(String.valueOf(mapData.get("email_address")));
			tempData.setSendTime(String.valueOf(mapData.get("send_time")));
			tempData.setStatus(String.valueOf(mapData.get("status")));
			tempData.setType(2);
			datas.add(tempData);
		}
		
		List<Map<String,Object>> rtxList=rtxMapper.getRtxHistoryByParam(map);
		for (Map<String, Object> mapData : rtxList) {
			tempData=new DataHistoryDTO();
			tempData.setContent(StringUtil.trimHtmlTag(String.valueOf(mapData.get("content"))));
			tempData.setReceiver(String.valueOf(mapData.get("receiver")));
			tempData.setSendTime(String.valueOf(mapData.get("send_time")));
			tempData.setStatus(String.valueOf(mapData.get("status")));
			tempData.setType(3);
			datas.add(tempData);
		}
		return datas;
	}

}
