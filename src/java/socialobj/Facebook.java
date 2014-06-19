/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package socialobj;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Guest
 */
@XmlRootElement
public class Facebook {

    private Picture picture;
    private String email;
    private String id;
    private String first_name;
    private String last_name;

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    
    
    
   
    
}
