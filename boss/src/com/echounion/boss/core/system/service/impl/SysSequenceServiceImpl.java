package com.echounion.boss.core.system.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.echounion.boss.core.system.ISysSequenceService;
import com.echounion.boss.entity.SysSequence;
import com.echounion.boss.persistence.system.SysSequenceMapper;

@Service
@Transactional(propagation=Propagation.SUPPORTS)
public class SysSequenceServiceImpl implements ISysSequenceService {

	@Autowired
	private SysSequenceMapper sysSequenceMapper;
	
	public int getCUrrentValue(String tableName, String fieldName) {
		Assert.notNull(tableName);
		Assert.notNull(fieldName);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("tableName", tableName);
		map.put("fieldName",fieldName);
		SysSequence sequence=sysSequenceMapper.getSysSequence(map);
		return sequence==null?0:sequence.getNextValue()-1;
	}

	public int getCurrentValue(String code) {
		Assert.notNull(code);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("code", code);
		SysSequence sequence=null;
		try {
			sequence = sysSequenceMapper.getSysSequence(map);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return sequence==null?0:sequence.getNextValue()-1;
	}

}
