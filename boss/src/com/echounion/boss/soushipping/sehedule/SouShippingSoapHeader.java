package com.echounion.boss.soushipping.sehedule;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.commons.codec.binary.Base64;
import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SouShippingSoapHeader extends AbstractSoapInterceptor {

	private static Logger logger=Logger.getLogger(SouShippingSoapHeader.class);
	
	private String userName;
	private String password;
	
	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		
		QName qname=new QName("RequestSOAPHeader");
		Document doc=DOMUtils.createDocument();
		
		Element root=doc.createElementNS("http://handler.com","Authentication");

		Element userNameElement=doc.createElement("Username");
		Element passwordElement=doc.createElement("Password");
		Element uuIdElement=doc.createElement("Uuid");
		
		userNameElement.setTextContent(userName);
		passwordElement.setTextContent(password);
		uuIdElement.setTextContent(getUuid(userName,password));
		
		root.appendChild(userNameElement);
		root.appendChild(passwordElement);
		root.appendChild(uuIdElement);

		SoapHeader head=new SoapHeader(qname,root);
		List<Header> header=message.getHeaders();

		header.add(head);
	}

	 public static String getUuid(String username, String password)
	  {
	    String source = null;
	    String dest = null;
	    source = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
	    source = source + "-" + username + "-" + password + "-" + getMac();
	    dest = new String(Base64.encodeBase64(source.getBytes()));

	    return dest;
	  }

	 public static String getMac()
	  {
	    String macAddress = "7845C4290954";
	    InetAddress ip = null;
	    try {
	      ip = InetAddress.getLocalHost();
	      if(ip==null)
	      {
	    	  return macAddress;
	      }
	      NetworkInterface network = NetworkInterface.getByInetAddress(ip);
	      if(network==null)
	      {
	    	  return macAddress;
	      }
	      byte[] mac = network.getHardwareAddress();
	      if(mac==null)
	      {
	    	  return macAddress;
	      }
	      StringBuilder sb = new StringBuilder();
	      for (int i = 0; i < mac.length; i++)
	      {
	        sb.append(String.format("%02X%s", new Object[] { Byte.valueOf(mac[i]), i < mac.length - 1 ? "" : "" }));
	      }
	      macAddress = sb.toString();
	    } catch (UnknownHostException e) {
	    	logger.warn(e.getMessage());
	    } catch (SocketException e) {
	    	logger.warn(e.getMessage());
	    }

	    return macAddress;
	  }
	 
	public SouShippingSoapHeader(){   
        super(Phase.WRITE);   
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
