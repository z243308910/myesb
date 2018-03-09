package com.echounion.boss.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.echounion.boss.core.history.service.IDataHistoryService;
import com.echounion.boss.entity.dto.DataHistoryDTO;

public class BeanFactoryTest {

	public static void main(String[] args) {
		ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
		IDataHistoryService historyService=ctx.getBean(IDataHistoryService.class);
		List<DataHistoryDTO> data=historyService.getInfoHistory("",null);
		for (DataHistoryDTO dataHistoryDTO : data) {
			System.out.println(dataHistoryDTO.getType()+ " "+dataHistoryDTO.getReceiver());
		}
	}
}
