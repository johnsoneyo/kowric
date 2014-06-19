/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;


import com.sun.jersey.multipart.FormDataParam;
import java.io.ByteArrayInputStream;
import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import javax.ejb.Stateless;
import javax.interceptor.ExcludeClassInterceptors;
import javax.interceptor.Interceptors;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.FileUtils;
import pojo.Category;
import pojo.Profile;
import pojo.Projects;
import pojo.Users;
import pojo.Files;
import pojo.Shareholder;
import pojo.Tags;


/** 
 * Kowric Web Service
 *
 * @author johnson eyo
 */
@Path("/userreg")
@Stateless
@Interceptors(LoggingInterceptor.class)

public class UserResource {

  static final  private Logger logger = Logger.getLogger("kowric logs");
          HashMap <String ,Projects>projectMap = new HashMap<String ,Projects>();
              HashMap <String ,Profile>userMap = new HashMap<String ,Profile>();
    
    @Context
    private UriInfo context;

 
    private Users user;
   private Profile profile;
   private Projects project;
    private Files file;
    private Tags tag;
    
    @PersistenceContext 
    EntityManager em;
     @PersistenceContext 
    EntityManager em2;

   
   
    
 
    @POST
    @Path("/registration1")
    @Consumes(MediaType.APPLICATION_JSON)
     @Produces(MediaType.APPLICATION_JSON)
    public Response registration1(Users user,@Context HttpServletRequest req) throws IOException{
      
     user.setStatus("UN-VERIFIED");
      em.persist(user);
      
       Profile profile = new Profile(user.getUserId());
       profile.setTimeCreated( new java.sql.Timestamp(new java.util.Date().getTime()));
       
      
       em.persist(profile);
      
      user.setProfile(profile);
      em.merge(user);
      
    
      System.out.printf("the name is %s ",user.getFirstName());
      logger.log(Level.INFO, "new user profile created ");
      
            userMap.put(req.getSession().getId(), profile);
   sendEmail(user);
      return Response.ok(user,MediaType.APPLICATION_JSON).build();
        
    }
    
    
    @GET
    @Path("/testmap")
    @Produces(MediaType.TEXT_PLAIN)
   public Response getObject(@Context HttpServletRequest req){
     String id = req.getSession().getId();
        return Response.ok(id, MediaType.TEXT_PLAIN).build();
    }    
    
    
 
    
     
    
    @POST 
    @Path("/registration2")
    @Consumes(MediaType.APPLICATION_JSON)
      @Produces(MediaType.APPLICATION_JSON)
    public Response registration2(Profile prof, @Context  HttpServletRequest req){
      
        
         prof.setTimeCreated( new java.sql.Timestamp(new java.util.Date().getTime()));
      
     Profile Managedpro =  em.merge(prof);
       
 
      logger.log(Level.INFO, "profile modified ");
       
         System.out.print("reg2 method called");
                     
         userMap.put(req.getSession().getId(), Managedpro);
       return Response.ok(Managedpro,MediaType.APPLICATION_JSON).build(); 
    }
    
    
     
    
    
    
    @POST
    @Path("/registration2/tag") 
     @Consumes(MediaType.APPLICATION_JSON)
     @Produces(MediaType.APPLICATION_JSON)
    public Response registration2_3(List<Tags>tagList,@Context HttpServletRequest req){
     
     Profile p = (Profile)userMap.get(req.getSession().getId());
     Users u = em2.find(Users.class,p.getUserId());
       
    for(Tags t : tagList){
      Tags managedTag =  em.find(Tags.class, t.getTagId());
        managedTag.getUsersList().add(u);
        u.getTagsList().add(t);         
       Users managedUser =  em.merge(u); 
   
    }     
   logger.log(Level.INFO, "the taglist created for user");
  
       return Response.ok(u,MediaType.APPLICATION_JSON).build(); 
    } 
    
  
    @POST
    @Path("/registration2/tag2") 
     @Consumes(MediaType.APPLICATION_JSON)
     @Produces(MediaType.APPLICATION_JSON)
    public Response registration2_4( final List<Tags>tagList,@Context final HttpServletRequest req){
   
    
           
              Profile p = userMap.get(req.getSession().getId());
      Projects pr = projectMap.get(req.getSession().getId());
      
     Users u =  em2.find(Users.class,p.getUserId());
       
      
    for(Tags t : tagList){
    Tags managedTag =   em.find(Tags.class, t.getTagId());
      managedTag.getUsersList().add(u);
        u.getTagsList().add(t);         
      
       
     //  managedTag.getProjectsList().add(pr);
        pr.getTagsList().add(t);
        em.merge(pr);
       
        u.getProjectsList().add(pr);
        pr.getUsersList().add(u);
        
         Users managedUser =  em.merge(u); 
       
          
        
    } 
      
     
   logger.log(Level.INFO, "the taglist created for user");    
     
  
       return Response.ok(u,MediaType.APPLICATION_JSON).build(); 
    }
    
     
     
    @POST
    @Path("/registration3/uploadphoto")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
   
    public Response uploadPhoto(@FormDataParam("file") InputStream file,@Context HttpServletRequest req){
       Profile p = (Profile)userMap.get(req.getSession().getId());
    
         try {
            byte[]upload =    IOUtils.toByteArray(file);      
            p.setPicture(upload);
            em.merge(p);
            
          logger.log(Level.INFO, "photo uploaded"); 
           userMap.put(req.getSession().getId(), p);
             return Response.ok(p,MediaType.APPLICATION_JSON).build(); 
        } catch (IOException ex) {
         return Response.status(Response.Status.BAD_REQUEST).build();
        }
       
    }
     
    
    @POST
    @Path("/registration3/uploadcv")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadCV(@FormDataParam("file") InputStream file, @Context HttpServletRequest req){
         Profile p = (Profile)userMap.get(req.getSession().getId());
        try {
            byte[]upload =    IOUtils.toByteArray(file);
            p.setCv(upload);
            em.merge(p);
           userMap.remove(req.getSession().getId());
           
            logger.log(Level.INFO, "cv uploaded");
             return Response.ok(p,MediaType.APPLICATION_JSON).build(); 
        } catch (IOException ex) {
         return Response.status(Response.Status.BAD_REQUEST).build();
        }
        
         
    }
    //
    //
    //
    //method call for onlt entrepreneurs
    @POST
    @Path("/registration/project")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registration2_1(Projects project,@Context HttpServletRequest req){
    
      Profile p = userMap.get(req.getSession().getId());
      
    System.out.printf("the p name %s", project.getProjectName());
      
    em2.flush();
    
    Users u =  em2.find(Users.class,p.getUserId());
    System.out.printf("the pr name %s", u.getFirstName());
     
        project.setTimeCreated(new java.sql.Timestamp(new java.util.Date().getTime()));
        project.setProjectStatus("UN-VERIFIED");
        
       u.getProjectsList().add(project);
       project.getUsersList().add(u);
       
       em.persist(project);
       em.merge(u);
      logger.log(Level.INFO, "project created"); 
    
    projectMap.put(req.getSession().getId(), project);
    
       return Response.ok(project,MediaType.APPLICATION_JSON).build(); 
    }
    
    
   
    
   @POST
    @Path("registration/shareholder")
    @Consumes(MediaType.APPLICATION_JSON)
     @Produces(MediaType.APPLICATION_JSON)
    public Response registration2_2(List<Shareholder> shareList,@Context HttpServletRequest req){
       
        Projects p = projectMap.get(req.getSession().getId());
       for(Shareholder s : shareList){
           p.getShareholderList().add(s);
           s.setProjectId(p);   
            em.merge(s);
              em.merge(p);
       }
       
    
     
       System.out.print("reg2 share method called");
     
       return Response.ok(p,MediaType.APPLICATION_JSON).build(); 
    }
    
    
  
    
    
    @POST
    @Path("/registration3/uploadfile")
     @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@FormDataParam("file") InputStream file,@Context HttpServletRequest req){
       
        Projects p =  projectMap.get(req.getSession().getId());
         try {
            byte[]upload =    IOUtils.toByteArray(file);
            Files f = new Files();
            f.setPhile(upload);
         p.getFilesList().add(f);
            em.merge(p);
            em.persist(f);
            userMap.remove(req.getSession().getId());
            projectMap.remove(req.getSession().getId());
             return Response.ok(p,MediaType.APPLICATION_JSON).build(); 
        } catch (IOException ex) {
         return Response.status(Response.Status.BAD_REQUEST).build();
        }
        
    }
    
    @GET
    @Path("{id}")
    @Produces("image/jpeg")
    @PermitAll
    @ExcludeClassInterceptors
    public byte[] getPicture(@PathParam("id") int id){
        
     Profile user =   em2.find(Profile.class, id);
   
   return user.getPicture();
      
    }
    
  
    @GET
    @Path("/taglist")
   
    @Produces(MediaType.APPLICATION_JSON)
    public Response tagList(){
   Query q =     em.createQuery("select t from Tags t ");
   List<Tags>tagList  =q.getResultList();
  
   logger.log(Level.INFO, "the taglist is been pulled up");
  
        return Response.ok(tagList.toArray(new Tags[tagList.size()])).build();
        
     
    }
    
    
    
  
   
    
    @GET
    @Path("/categorylist")
   
    @Produces(MediaType.APPLICATION_JSON)
    public Response categoryList(){
   Query q =     em.createQuery("select c from Category c "); 
   List<Category>cateList  =q.getResultList();
   
  
   
    logger.log(Level.INFO, "the categorylist is been pulled up");
  
        return Response.ok(cateList.toArray(new Category[cateList.size()])).build();
        
     
    }
    
    
    
   
    @POST
    @Path("/tagcheck")
    @Consumes(MediaType.APPLICATION_JSON)
    public void tagCheck(List<Tags>tList){ 
        logger.log(Level.INFO, "the taglist is been pulled up");
  
        for(Tags t : tList)
            System.out.printf("%s",t.getTagName());
        
         
    }
    
  
    
    
    
    
    
 @GET
    @Path("/sharelist")
 @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response shareList(){
   Query q =     em.createQuery("select s from Shareholder s ");
   List<Shareholder>cateList  =q.getResultList();
  
   System.out.print("shareList called");
        return Response.ok(cateList.toArray(new Shareholder[cateList.size()])).build();
        
     
    }    
    
 
 
  @GET
  @Path("/check")
  @PermitAll
  @Produces(MediaType.APPLICATION_JSON)
  public Response getUser(@QueryParam("id") int id){
     Users u = em.find(Users.class,id);
       
     
      logger.log(Level.INFO, "user retrieved ");
     
      return Response.ok(u,MediaType.APPLICATION_JSON).build();
  }
   
    
  
  
  @GET
  @Path("/cat")
  @PermitAll
  @Produces(MediaType.APPLICATION_JSON)
  public Response getCat(@QueryParam("id") int id){
    Projects p =  em.find(Projects.class,id);
       return Response.ok(p,MediaType.APPLICATION_JSON).build();
      
  }
  
  
  
  public void sendEmail(Users user){
      
    Users u = user;  
    
    String email = u.getEmail();
    String hashDate =   hashDate();
   String verifylink = "http://54.200.6.152:8080/KowricService/resources/userreg/email?email="+email+"&value="+hashDate;
     final String username = "johnsoneyo@gmail.com";
		final String password = "freaks03";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "25");
 Session session = Session.getInstance(props,
		
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
      
                try {
 
			Message message = new MimeMessage(session);
                          message.setSentDate(new java.util.Date());
			message.setFrom(new InternetAddress("johnsoneyo@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(u.getEmail()));
			message.setSubject("Testing Subject");
			message.setText("clicking this link verifies you on our database  "
                                   +verifylink+"");
 
			Transport.send(message);
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
                
     
  }
  
  
  
  
  @GET
  @Path("/photo")
  @PermitAll
  @Produces({"image/jpeg","image/png"})
  public Response getPhoto(@QueryParam("profile") int id) throws IOException{
      
    Profile p =  em.find(Profile.class,id);
     try{
    return  Response.ok(new ByteArrayInputStream(p.getPicture())).build();
     }
     catch(NullPointerException no){
      
        File file = new File("kowric_head.png");
      
     byte[]imo =    FileUtils.readFileToByteArray(file);
      logger.log(Level.INFO,"view image called");
         return  Response.ok(new ByteArrayInputStream(imo)).build(); 
     }
  }

   
    public String hashDate() {
  java.util.Date date = new java.util.Date();
  String plaintext = date.toString();
  try{
MessageDigest m = MessageDigest.getInstance("MD5");
m.reset();
m.update(plaintext.getBytes());
byte[] digest = m.digest();
BigInteger bigInt = new BigInteger(1,digest);
String hashtext = bigInt.toString(16);

return hashtext;
  }
  catch(Exception no){
      return "";
  }
  }
  
  @GET
  @PermitAll
  @Path("/faketest")
  @Produces(MediaType.TEXT_PLAIN)
  public String fakever(){
      String jemail = "johnsoneyo@gmail.com";
   String hashDate =   hashDate();
   String fakeurl = "http://192.168.1.2:8080/KowricService/resources/userreg/email?email="+jemail+"&value="+hashDate;
   return fakeurl;
  }
  
  @GET
  @PermitAll
  @Path("/unverify/{userid}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response unverifyService(@PathParam("userid") int userid){
      
    Users u =  em.find(Users.class,userid);
    if(u==null){
     return   Response.status(Response.Status.NOT_FOUND).entity("user not found").build();
    }else{
        u.setStatus("UN-VERIFIED");
        em.merge(u);
        return Response.status(Response.Status.OK).entity("user unverified").build();
    }
        
      
  }
  
    
    @GET
    @Path("/email")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response verifyAccount(@QueryParam("email")String email,@QueryParam("value")String value) throws URISyntaxException{
       
        
     Query q =  em.createQuery("select u from Users u where u.email = :email");
      q.setParameter("email", email);
    Users u = (Users) q.getSingleResult();
    
        u.setStatus("VERIFIED");
       URI uri = new URI("http://app.kowric.com/#/verify/success");
        em.merge(u);
       return   Response.temporaryRedirect(uri).build();
    }
            
  @GET
  
  @Path("/delete/{id}")
  public String deleteUser(@PathParam("id") int id){
      
  Users us =    em.find(Users.class, id);
  if(us!=null){
  em.remove(us);
  return "successfully deleted";
  }else{
      return "no such user";
  }
  
  
  
  }
  
  
  @GET
  
  @Produces(MediaType.APPLICATION_XML)
  @Path("/userslist")
  public List<Users> userList(){
      
      return em.createQuery("select u from Users u ").getResultList();
  }
 
} 
  