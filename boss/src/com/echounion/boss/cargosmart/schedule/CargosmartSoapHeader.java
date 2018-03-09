package com.echounion.boss.cargosmart.schedule;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CargosmartSoapHeader extends AbstractSoapInterceptor {

	public String nameURI;
	public String uuid;

	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		
		QName qname=new QName("RequestSOAPHeader");
		Document doc=DOMUtils.createDocument();
		
		
		Element root=doc.createElementNS(nameURI,"UUID");
		root.setTextContent(uuid);
		
		SoapHeader head=new SoapHeader(qname,root);
		List<Header> header=message.getHeaders();
		header.add(head);
	}

	public String getNameURI() {
		return nameURI;
	}

	public void setNameURI(String nameURI) {
		this.nameURI = nameURI;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public CargosmartSoapHeader(){   
        super(Phase.WRITE);   
    }   
}
