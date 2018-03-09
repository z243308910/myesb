package com.echounion.boss.soushipping.sehedule.webservice;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

@WebServiceClient(name = "data", 
                  wsdlLocation = "http://soushipping.com:8080/public/services/data?wsdl",
                  targetNamespace = "http://ws.apache.org/axis2") 
public class Data extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://ws.apache.org/axis2", "data");
    public final static QName DataHttpSoap12Endpoint = new QName("http://ws.apache.org/axis2", "dataHttpSoap12Endpoint");
    public final static QName DataHttpEndpoint = new QName("http://ws.apache.org/axis2", "dataHttpEndpoint");
    public final static QName DataHttpSoap11Endpoint = new QName("http://ws.apache.org/axis2", "dataHttpSoap11Endpoint");
    static {
        URL url = null;
        try {
            url = new URL("http://soushipping.com:8080/public/services/data?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(Data.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://soushipping.com:8080/public/services/data?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public Data(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public Data(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Data() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    public Data(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public Data(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public Data(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     *
     * @return
     *     returns DataPortType
     */
    @WebEndpoint(name = "dataHttpSoap12Endpoint")
    public DataPortType getDataHttpSoap12Endpoint() {
        return super.getPort(DataHttpSoap12Endpoint, DataPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns DataPortType
     */
    @WebEndpoint(name = "dataHttpSoap12Endpoint")
    public DataPortType getDataHttpSoap12Endpoint(WebServiceFeature... features) {
        return super.getPort(DataHttpSoap12Endpoint, DataPortType.class, features);
    }
    /**
     *
     * @return
     *     returns DataPortType
     */
    @WebEndpoint(name = "dataHttpEndpoint")
    public DataPortType getDataHttpEndpoint() {
        return super.getPort(DataHttpEndpoint, DataPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns DataPortType
     */
    @WebEndpoint(name = "dataHttpEndpoint")
    public DataPortType getDataHttpEndpoint(WebServiceFeature... features) {
        return super.getPort(DataHttpEndpoint, DataPortType.class, features);
    }
    /**
     *
     * @return
     *     returns DataPortType
     */
    @WebEndpoint(name = "dataHttpSoap11Endpoint")
    public DataPortType getDataHttpSoap11Endpoint() {
        return super.getPort(DataHttpSoap11Endpoint, DataPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns DataPortType
     */
    @WebEndpoint(name = "dataHttpSoap11Endpoint")
    public DataPortType getDataHttpSoap11Endpoint(WebServiceFeature... features) {
        return super.getPort(DataHttpSoap11Endpoint, DataPortType.class, features);
    }

}
