/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package socialobj;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Guest
 */
public class LinkedIn {
    
    private String emailAddress;
    private String id;
    private String firstName;
    private String lastName;
    private String pictureUrl;

    @XmlElement(name="emailAddress")
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @XmlElement(name="id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement(name="firstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @XmlElement(name="lastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

      @XmlElement(name="pictureUrl")
    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
    
    
    
    
    
}
