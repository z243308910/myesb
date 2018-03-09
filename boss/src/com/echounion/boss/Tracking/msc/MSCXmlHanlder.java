package com.echounion.boss.Tracking.msc;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MSCXmlHanlder extends DefaultHandler{

	private String preTag=null;
	private StringBuilder content=null;
	
	public StringBuilder getContent() {
		return content;
	}

	public void setContent(StringBuilder content) {
		this.content = content;
	}

	@Override
	public void characters(char[] ch, int start, int length)throws SAXException {
		String content=new String(ch,start,length);
		if("description".equals(preTag))
		{
			this.content.append(content);
		}
	}

	@Override
	public void endElement(String uri, String localName, String name)throws SAXException {
		preTag=null;
	}

	@Override
	public void startDocument() throws SAXException {
		content=new StringBuilder();
		content.append("<html>");
		content.append("<head></head>");
		content.append("<body>");
	}

	@Override
	public void startElement(String uri, String localName, String name,Attributes attributes) throws SAXException {
		preTag=name;
	}

	@Override
	public void endDocument() throws SAXException {
		content.append("</body>");
		content.append("</html>");
	}

}
