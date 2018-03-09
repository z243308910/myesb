
package com.echounion.boss.cargosmart.schedule.ssm.types.common;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.cargosmart.ssm.types.common package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.cargosmart.ssm.types.common
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CarrierType }
     * 
     */
    public CarrierType createCarrierType() {
        return new CarrierType();
    }

    /**
     * Create an instance of {@link CityListType }
     * 
     */
    public CityListType createCityListType() {
        return new CityListType();
    }

    /**
     * Create an instance of {@link ServiceLoopInfoType }
     * 
     */
    public ServiceLoopInfoType createServiceLoopInfoType() {
        return new ServiceLoopInfoType();
    }

    /**
     * Create an instance of {@link FacilityInfoType }
     * 
     */
    public FacilityInfoType createFacilityInfoType() {
        return new FacilityInfoType();
    }

    /**
     * Create an instance of {@link ServiceType }
     * 
     */
    public ServiceType createServiceType() {
        return new ServiceType();
    }

    /**
     * Create an instance of {@link VesselInfoType }
     * 
     */
    public VesselInfoType createVesselInfoType() {
        return new VesselInfoType();
    }

}
