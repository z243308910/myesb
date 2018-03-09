
package com.echounion.boss.soushipping.sehedule.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "args"
})
@XmlRootElement(name = "main")
public class Main {

    @XmlElement(nillable = true)
    protected List<String> args;

    public List<String> getArgs() {
        if (args == null) {
            args = new ArrayList<String>();
        }
        return this.args;
    }

}
