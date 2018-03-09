
package com.echounion.boss.soushipping.sehedule.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.echounion.boss.soushipping.sehedule.tool.xsd.Harbor;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "_return"
})
@XmlRootElement(name = "findHarborListResponse")
public class FindHarborListResponse {

    @XmlElement(name = "return", nillable = true)
    protected List<Harbor> _return;

    public List<Harbor> getReturn() {
        if (_return == null) {
            _return = new ArrayList<Harbor>();
        }
        return this._return;
    }

}
