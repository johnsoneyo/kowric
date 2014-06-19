/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Guest
 */
@XmlRootElement(name="SubList")
public class SubSetList {
    
   private String data;

    public SubSetList() {
    }
    @XmlElement(name="rommie")
    public String getData() {
        return data;
    }

    public SubSetList(String data) {
        this.data = data;
    }

    
    
    public void setData(String data) {
        this.data = data;
    }
   
   
    
    
    
    
}
