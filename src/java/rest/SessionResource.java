/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Guest
 */
@Path("/session")
@Stateless
public class SessionResource {

    @Context
    private UriInfo context;
    @Context HttpServletRequest req;
    
   HashMap <String ,Integer>loggedIn = new HashMap<String ,Integer>();
    
    
    public SessionResource() {
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getMango(@Context HttpServletRequest req,@PathParam("id") int id){
      int a =  id *5;
   
    String jesid = req.getSession().getId();
    loggedIn.put(jesid, id);

        return String.valueOf(a);
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getSessionId(@Context HttpServletRequest request){
      return request.getSession(true).getId();
        
    }
    
     @GET
     @Path("/value")
    @Produces(MediaType.TEXT_PLAIN)
    public String getMapCount(){
         
      return String.valueOf(loggedIn.size());
        
    }
    
     
    
}
