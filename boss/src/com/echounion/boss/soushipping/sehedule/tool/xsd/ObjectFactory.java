
package com.echounion.boss.soushipping.sehedule.tool.xsd;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;



@XmlRegistry
public class ObjectFactory {

    private final static QName _SailingScheduleRoutesVoyage_QNAME = new QName("http://tool/xsd", "voyage");
    private final static QName _SailingScheduleRoutesVessel_QNAME = new QName("http://tool/xsd", "vessel");
    private final static QName _SailingScheduleRoutesOrigdate_QNAME = new QName("http://tool/xsd", "origdate");
    private final static QName _SailingScheduleRoutesService_QNAME = new QName("http://tool/xsd", "service");
    private final static QName _SailingScheduleRoutesCarrier_QNAME = new QName("http://tool/xsd", "carrier");
    private final static QName _SailingScheduleRoutesDestdate_QNAME = new QName("http://tool/xsd", "destdate");
    private final static QName _HarborCityName_QNAME = new QName("http://tool/xsd", "cityName");
    private final static QName _HarborHarborName_QNAME = new QName("http://tool/xsd", "harborName");
    private final static QName _HarborCountryName_QNAME = new QName("http://tool/xsd", "countryName");


    public ObjectFactory() {
    }


    public SailingScheduleRoutes createSailingScheduleRoutes() {
        return new SailingScheduleRoutes();
    }

    public Harbor createHarbor() {
        return new Harbor();
    }

    @XmlElementDecl(namespace = "http://tool/xsd", name = "voyage", scope = SailingScheduleRoutes.class)
    public JAXBElement<String> createSailingScheduleRoutesVoyage(String value) {
        return new JAXBElement<String>(_SailingScheduleRoutesVoyage_QNAME, String.class, SailingScheduleRoutes.class, value);
    }

    @XmlElementDecl(namespace = "http://tool/xsd", name = "vessel", scope = SailingScheduleRoutes.class)
    public JAXBElement<String> createSailingScheduleRoutesVessel(String value) {
        return new JAXBElement<String>(_SailingScheduleRoutesVessel_QNAME, String.class, SailingScheduleRoutes.class, value);
    }

    @XmlElementDecl(namespace = "http://tool/xsd", name = "origdate", scope = SailingScheduleRoutes.class)
    public JAXBElement<String> createSailingScheduleRoutesOrigdate(String value) {
        return new JAXBElement<String>(_SailingScheduleRoutesOrigdate_QNAME, String.class, SailingScheduleRoutes.class, value);
    }

    @XmlElementDecl(namespace = "http://tool/xsd", name = "service", scope = SailingScheduleRoutes.class)
    public JAXBElement<String> createSailingScheduleRoutesService(String value) {
        return new JAXBElement<String>(_SailingScheduleRoutesService_QNAME, String.class, SailingScheduleRoutes.class, value);
    }

    @XmlElementDecl(namespace = "http://tool/xsd", name = "carrier", scope = SailingScheduleRoutes.class)
    public JAXBElement<String> createSailingScheduleRoutesCarrier(String value) {
        return new JAXBElement<String>(_SailingScheduleRoutesCarrier_QNAME, String.class, SailingScheduleRoutes.class, value);
    }

    @XmlElementDecl(namespace = "http://tool/xsd", name = "destdate", scope = SailingScheduleRoutes.class)
    public JAXBElement<String> createSailingScheduleRoutesDestdate(String value) {
        return new JAXBElement<String>(_SailingScheduleRoutesDestdate_QNAME, String.class, SailingScheduleRoutes.class, value);
    }

    @XmlElementDecl(namespace = "http://tool/xsd", name = "cityName", scope = Harbor.class)
    public JAXBElement<String> createHarborCityName(String value) {
        return new JAXBElement<String>(_HarborCityName_QNAME, String.class, Harbor.class, value);
    }

    @XmlElementDecl(namespace = "http://tool/xsd", name = "harborName", scope = Harbor.class)
    public JAXBElement<String> createHarborHarborName(String value) {
        return new JAXBElement<String>(_HarborHarborName_QNAME, String.class, Harbor.class, value);
    }

    @XmlElementDecl(namespace = "http://tool/xsd", name = "countryName", scope = Harbor.class)
    public JAXBElement<String> createHarborCountryName(String value) {
        return new JAXBElement<String>(_HarborCountryName_QNAME, String.class, Harbor.class, value);
    }

}
