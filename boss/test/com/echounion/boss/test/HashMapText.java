package com.echounion.boss.test;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class HashMapText {

	public static void main(String[] args) {
		String str="{'region':'aaa','company':'echo','email':'649468273qq.com','nvocc':'aaa','contact:'echo','tel':'111111'}";
		
		Map<String,Object> map=JSON.parseObject(str,HashMap.class);
		
		System.out.println(map);
	}
}
