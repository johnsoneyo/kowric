/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

/**
 *
 * @author Guest
 */
public class UnVerifiedUserException extends Exception{

    private String usermessage;
    public UnVerifiedUserException(String message) {
       usermessage = message;
    }

    public String getUsermessage() {
        return usermessage;
    }

    public void setUsermessage(String usermessage) {
        this.usermessage = usermessage;
    }

    
    
    
    
}
