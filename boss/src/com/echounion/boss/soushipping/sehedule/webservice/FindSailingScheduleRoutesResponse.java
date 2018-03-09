
package com.echounion.boss.soushipping.sehedule.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.echounion.boss.soushipping.sehedule.tool.xsd.SailingScheduleRoutes;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "_return"
})
@XmlRootElement(name = "findSailingScheduleRoutesResponse")
public class FindSailingScheduleRoutesResponse {

    @XmlElement(name = "return", nillable = true)
    protected List<SailingScheduleRoutes> _return;

   
    public List<SailingScheduleRoutes> getReturn() {
        if (_return == null) {
            _return = new ArrayList<SailingScheduleRoutes>();
        }
        return this._return;
    }

}
