package com.echounion.boss.test;

import org.springframework.web.util.HtmlUtils;

import com.echounion.boss.common.util.StringUtil;

public class HtmlFilter {

	public static void main(String[] args) {
		String data=HtmlUtils.htmlEscape("<script>alert(34234);</script>");
		System.out.println(data);
		
		data=StringUtil.trimHtmlTag("<script>alert(34234);</script>");
		System.out.println(data);
	}
}
