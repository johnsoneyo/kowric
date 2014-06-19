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
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PrePersist;
import javax.persistence.Query;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import pojo.Category;
import socialobj.Facebook;
import pojo.Files;
import socialobj.Gmail;
import socialobj.LinkedIn;
import pojo.Profile;
import pojo.Projects;
import pojo.Shareholder;
import pojo.Tags;
import pojo.Users;

/**
 * Kowric Web Service
 *
 * @author johnson eyo
 */
@Path("usermanager")
@Stateless
@Interceptors(LoggingInterceptor.class)
public class ScalingResource {

    static final private Logger logger = Logger.getLogger("kowric logs");
    @Context
    private UriInfo context;

    public ScalingResource() {
    }
    @PersistenceContext
    EntityManager em;

    @POST
    @Path("/registration1")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registration1(Users user) throws NoSuchAlgorithmException {

 

        Query q = em.createQuery("select u.email from Users u where u.email = :email");
        q.setParameter("email", user.getEmail());

        try {
            String value = (String) q.getSingleResult();

            logger.log(Level.INFO, "user conflict");
            return Response.status(Response.Status.CONFLICT).build();
        } catch (NoResultException no) {
            user.setStatus("UN-VERIFIED");
            String shatext = md5ToSha256(user.getHashpass());
           
            user.setPassword(shatext);
            em.persist(user);

            Profile profile = new Profile(user.getUserId());
            profile.setTimeCreated(new java.sql.Timestamp(new java.util.Date().getTime()));
            em.persist(profile);


            user.setProfile(profile);
            em.merge(user);


            logger.log(Level.INFO, "user created");

          sendEmail(user);
            return Response.ok(profile, MediaType.APPLICATION_JSON).build();

        }


 

    }

    @PrePersist
    public void checkEmail() {
        Query q = em.createQuery("");

    }

    @POST
    @Path("/registration2")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registration2(Profile prof) {


        prof.setTimeCreated(new java.sql.Timestamp(new java.util.Date().getTime()));

        Profile Managedpro = em.merge(prof);
        logger.log(Level.INFO, "profile created");
        return Response.ok(Managedpro, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getprojectlist")
    public List<Projects> getList() {

        return em.createQuery("select p from Projects p").getResultList();
    }

    @POST
    @Path("/registration2/tag")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registration2_3(List<Tags> tagList, @QueryParam("getuser") int id) {


        Users u = em.find(Users.class, id);
        u.getTagsList().clear();
        Users ManagedU = em.merge(u);
        for (Tags t : tagList) {
            Tags to = em.find(Tags.class, t.getTagId());
            ManagedU.getTagsList().add(t);
            em.merge(ManagedU);
        }

        logger.log(Level.INFO, "tags added user add");
        return Response.ok(MediaType.APPLICATION_XML).build();

        /*
         Users u = em.find(Users.class,id);
         for(Tags t : tagList){
         Tags managedTag =  em.find(Tags.class, t.getTagId());
         managedTag.getUsersList().add(u);
          
         Users managedUser =  em.merge(u); 
   
         }  
         logger.log(Level.INFO, "new tags"); 
         return Response.ok(u,MediaType.APPLICATION_JSON).build();
         * 
         */
    }

    @POST
    @Path("/registration3/uploadphoto")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadPhoto(@FormDataParam("file") InputStream file, @QueryParam("getuser") int id) {

        Profile p = em.find(Profile.class, id);
        try {
            byte[] upload = IOUtils.toByteArray(file);
            p.setPicture(upload);
            em.merge(p);

            logger.log(Level.INFO, "photo uploaded");
            return Response.ok(p, MediaType.APPLICATION_JSON).build();
        } catch (IOException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    @POST
    @Path("/registration3/uploadcv")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadCV(@FormDataParam("file") InputStream file, @QueryParam("getuser") int id) {
        Profile p = em.find(Profile.class, id);
        try {
            byte[] upload = IOUtils.toByteArray(file);
            p.setCv(upload);
            em.merge(p);
            logger.log(Level.INFO, "cv uploaded");
            return Response.ok(p, MediaType.APPLICATION_JSON).build();
        } catch (IOException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }


    } 

    public void sendEmail(Users user) {

        Users u = user;

        String email = u.getEmail();
        String hashDate = hashDate();
        String verifylink = "http://78.47.136.55:8080/KowricService/resources/usermanager/email?email=" + email + "&value=" + hashDate;
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
                    + verifylink + "");

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


    }

    public String hashDate() {
        java.util.Date date = new java.util.Date();
        String plaintext = date.toString();
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(plaintext.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String hashtext = bigInt.toString(16);

            return hashtext;
        } catch (Exception no) {
            return "";
        }
    }

    @GET
    @PermitAll
    @Path("/email")
    @Produces(MediaType.APPLICATION_JSON)
    public Response verifyAccount(@QueryParam("email") String email, @QueryParam("value") String value) throws URISyntaxException {


        Query q = em.createQuery("select u from Users u where u.email = :email");
        q.setParameter("email", email);
        Users u = (Users) q.getSingleResult();

        u.setStatus("VERIFIED");
        URI uri = new URI("http://app.kowric.com/#!/verify/success");
        em.merge(u);
        return Response.temporaryRedirect(uri).build();
    }

    @GET
    @Path("/photo")
    @Produces({"image/jpeg", "image/png"})
    public Response getPhoto(@QueryParam("profile") int id) throws IOException {

        Profile p = em.find(Profile.class, id);
        try {
            return Response.ok(new ByteArrayInputStream(p.getPicture())).build();
        } catch (NullPointerException no) {

            File file = new File("kowric_head.png");

            byte[] imo = FileUtils.readFileToByteArray(file);

            return Response.ok(new ByteArrayInputStream(imo)).build();
        }
    }

    @GET
    @PermitAll
    @Path("/pishur")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPishur() {


        return "OMG!!!";


    }

    @POST
    @Path("/registration/project")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registration2_1(Projects project, @QueryParam("getuser") int id) {

        Users u = em.find(Users.class, id);



        project.setTimeCreated(new java.sql.Timestamp(new java.util.Date().getTime()));
        project.setProjectStatus("UN-VERIFIED");

        u.getProjectsList().add(project);
        project.getUsersList().add(u);

        em.persist(project);
        em.merge(u);

        logger.log(Level.INFO, "proejcts created");


        return Response.ok(project, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("registration/shareholder")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registration2_2(List<Shareholder> shareList, @QueryParam("getproject") int id) {

        Projects p = em.find(Projects.class, id);

        System.out.printf("id is %s", p.getProjectId());
        for (Shareholder s : shareList) {
            p.getShareholderList().add(s);
            s.setProjectId(p);
            em.persist(s);

        }


        System.out.print("reg2 share method called");

        return Response.ok(p, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/registration3/uploadfile")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@FormDataParam("file") InputStream file, @QueryParam("getproject") int id) {

        Projects p = em.find(Projects.class, id);
        try {
            byte[] upload = IOUtils.toByteArray(file);
            Files f = new Files();
            f.setPhile(upload);
            p.getFilesList().add(f);
            em.merge(p);
            em.persist(f);
            logger.log(Level.INFO, "file uploaded for proj");
            return Response.ok(p, MediaType.APPLICATION_JSON).build();
        } catch (IOException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    @POST
    @Path("/registration3/uploadprojectimage")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadProjectImage(@FormDataParam("file") InputStream file, @QueryParam("getproject") int id) {

        Projects p = em.find(Projects.class, id);
        try {
            byte[] upload = IOUtils.toByteArray(file);
             p.setProjectImage(upload);
            logger.log(Level.INFO, "project image added");
            return Response.ok(p, MediaType.APPLICATION_JSON).build();
        } catch (IOException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    @POST
    @Path("/registration2/tag2")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registration2_4(List<Tags> tagList, @QueryParam("getuser") int id, @QueryParam("getproject") int p) {



        Users u = em.find(Users.class, id);
        Projects pr = em.find(Projects.class, p);

        for (Tags t : tagList) {
            Tags managedTag = em.find(Tags.class, t.getTagId());


            //  managedTag.getProjectsList().add(pr);
            pr.getTagsList().add(t);


            u.getProjectsList().add(pr);


            Users managedUser = em.merge(u);

        }
        em.merge(pr);


        logger.log(Level.INFO, "the taglist created for user");


        return Response.ok(u, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/forgotpassword")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response forgotPassword(Users user) {
        Query q = em.createQuery("select u from Users u where u.email = :email");
        q.setParameter("email", user.getEmail());
        try {
            Users us = (Users) q.getSingleResult();
            confirmEmail(us);
            logger.log(Level.INFO, "forgot password option is found");
            return Response.status(Response.Status.OK).build();
        } catch (NoResultException noe) {
            logger.log(Level.INFO, "forgot password option not found");
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    private void confirmEmail(Users us) {

        Random rn = new Random(90000);
        rn.nextInt();
        String newPass = "old" + rn.nextInt();




        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(newPass.getBytes());

            byte byteData[] = md.digest();

            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
                us.setPassword(sb.toString());
                em.merge(us);
            }


        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }




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


        String html =
                "<html>"
                + "  <head>"
                + "    <style type='text/css'>"
                + "      p {color: #ddd;}"
                + "    </style>"
                + "  </head>"
                + "  <body>"
                + "   <p><img src='cid:icon'></p>"
                + " <div style='background-color : #d6f2ff; line-height:2em; font-family: arial,helvetica,verdana,tahoma; '>"
                + "    <p>Dear " + us.getFirstName() + " </p>\n"
                + "    <p>This is your new generated password,it has been changed for confidential reasons,</p>"
                + "    <p>please login on the kowric platform to change it</p>"
                + "    <p>password:" + newPass + "</p>"
                + "  <p></p>"
                + "    <p>Cheers..</p>"
                + "    <p>The Kowric Team.</p>"
                + "</div>"
                + "  </body>"
                + "</html>";



        try {


            Multipart multipart = new MimeMultipart("related");
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(html, "text/html");
            multipart.addBodyPart(messageBodyPart);


            MimeBodyPart iconBodyPart = new MimeBodyPart();
            DataSource iconDataSource = new FileDataSource(new File("01.jpg"));
            iconBodyPart.setDataHandler(new DataHandler(iconDataSource));
            iconBodyPart.setDisposition(Part.INLINE);
            iconBodyPart.setContentID("<icon>");
            iconBodyPart.addHeader("Content-Type", "image/jpeg");
            multipart.addBodyPart(iconBodyPart);

            MimeMessage message = new MimeMessage(session);
            message.setSentDate(new java.util.Date());
            message.setFrom(new InternetAddress("johnsoneyo@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(us.getEmail()));
            message.setSubject("Password Information ");
            message.setContent(multipart);


            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


    }

    @GET
    @Path("/emailverify")
    @Produces(MediaType.APPLICATION_JSON)
    public Response emailVerification(@QueryParam("email") String email) {

        Query q = em.createQuery("select u from Users u where u.email = :email");
        q.setParameter("email", email);
        try {
            Users user = (Users) q.getSingleResult();

            logger.log(Level.INFO, "user found ");
            return Response.ok(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException no) {
            logger.log(Level.INFO, "user not found ");
            return Response.status(Response.Status.NOT_FOUND).entity("user not found").build();
        }

    }

    @POST
    @Path("/userlogin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response userlogin(Users user) throws NoSuchAlgorithmException {
        System.out.printf("the %s", user.getEmail());

        
        String shatext = md5ToSha256(user.getPassword());

        Query q = em.createQuery("select u from Users u where u.email = :email and u.password = :password");
        q.setParameter("email", user.getEmail());
        q.setParameter("password", shatext);

        try {
            Users u = (Users) q.getSingleResult();
            if (u.getStatus().equals("UN-VERIFIED")) {
                try {
                    throw new UnVerifiedUserException("hi " + u.getFirstName() + ", you haven't verified account");
                } catch (UnVerifiedUserException ex) {
                    return Response.status(Response.Status.FORBIDDEN).entity(ex.getUsermessage()).build();
                }

            } else {

                return Response.ok(u, MediaType.APPLICATION_JSON).build();
            }
        } catch (NoResultException no) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @POST
    @Path("/facebooklogin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response facebookLogin(Facebook fb, @QueryParam("usertype") String value) throws MalformedURLException, IOException, NoSuchAlgorithmException {


        Query q = em.createQuery("select u from Users u where u.email = :email");
        q.setParameter("email", fb.getEmail());
        try {
            Users uo = (Users) q.getSingleResult();

            em.merge(uo);
            return Response.ok(uo, MediaType.APPLICATION_JSON).build();
        } catch (NoResultException no) {
            Users u = new Users();
            u.setFacebookId(fb.getId());
            u.setFirstName(fb.getFirst_name());
            u.setLastName(fb.getLast_name());
            u.setEmail(fb.getEmail());
            u.setStatus("VERIFIED");
            u.setUserType(value);

             java.util.Random rn = new java.util.Random();
            String passtext = "pass"+rn.nextInt(4563809);
            
            String hashtext = passwordToMD5(passtext);
            String shatext = md5ToSha256(hashtext);
            u.setHashpass(hashtext);
            u.setPassword(shatext);
            em.persist(u);


            String fbImage = fb.getPicture().getData().getUrl();

            InputStream input = new URL(fbImage).openStream();

            byte[] fbi = IOUtils.toByteArray(input);

            Profile p = new Profile(u.getUserId());
            p.setTimeCreated(new java.sql.Timestamp(new java.util.Date().getTime()));
            p.setPicture(fbi);
            em.persist(p);

            logger.log(Level.INFO, "method called");
            u.setProfile(p);
            Users uo = em.merge(u);



            return Response.ok(uo, MediaType.APPLICATION_JSON).build();
        }


    }

    @POST
    @Path("/gmaillogin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response gmailLogin(Gmail gm, @QueryParam("usertype") String value) throws IOException, NoSuchAlgorithmException {
        Query q = em.createQuery("select u from Users u where u.email = :email");
        q.setParameter("email", gm.getEmail());
        try {
            Users uo = (Users) q.getSingleResult();

            em.merge(uo);
            return Response.ok(uo, MediaType.APPLICATION_JSON).build();
        } catch (NoResultException no) {
            Users u = new Users();

            u.setFirstName(gm.getGiven_name());
            u.setLastName(gm.getFamily_name());
            u.setEmail(gm.getEmail());
            u.setGoogleId(gm.getId());
            u.setUserType(value);

             java.util.Random rn = new java.util.Random();
            String passtext = "pass"+rn.nextInt(4563809);
            
            String hashtext = passwordToMD5(passtext);
            String shatext = md5ToSha256(hashtext);
            u.setHashpass(hashtext);
            u.setPassword(shatext);
            em.persist(u);
            String gmImage = gm.getPicture();

            InputStream input = new URL(gmImage).openStream();

            byte[] gmi = IOUtils.toByteArray(input);

            Profile p = new Profile(u.getUserId());
            p.setTimeCreated(new java.sql.Timestamp(new java.util.Date().getTime()));
            p.setPicture(gmi);
            p.setSex(gm.getGender());
            em.persist(p);

            logger.log(Level.INFO, "method called");
            u.setProfile(p);
            Users uo = em.merge(u);





            return Response.ok(uo, MediaType.APPLICATION_JSON).build();
        }


    }

    @POST
    @Path("/linkedinlogin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response linkedInLogin(LinkedIn lnkd, @QueryParam("usertype") String value) throws IOException, NoSuchAlgorithmException {

        Query q = em.createQuery("select u from Users u where u.email = :email");
        q.setParameter("email", lnkd.getEmailAddress());
        try {
            Users uo = (Users) q.getSingleResult();

            em.merge(uo);
            return Response.ok(uo, MediaType.APPLICATION_JSON).build();
        } catch (NoResultException no) {
            Users u = new Users();
            u.setLinkedinId(lnkd.getId());
            u.setFirstName(lnkd.getFirstName());
            u.setLastName(lnkd.getLastName());
            u.setEmail(lnkd.getEmailAddress());
            u.setUserType(value);

            java.util.Random rn = new java.util.Random();
            String passtext = "pass"+rn.nextInt(4563809);
            
            String hashtext = passwordToMD5(passtext);
            String shatext = md5ToSha256(hashtext);
            u.setHashpass(hashtext);
            u.setPassword(shatext);
            em.persist(u);
            String gmImage = lnkd.getPictureUrl();

            InputStream input = new URL(gmImage).openStream();

            byte[] lnki = IOUtils.toByteArray(input);

            Profile p = new Profile(u.getUserId());
            p.setTimeCreated(new java.sql.Timestamp(new java.util.Date().getTime()));
            p.setPicture(lnki);

            em.persist(p);

            logger.log(Level.INFO, "method called");
            u.setProfile(p);
            Users uo = em.merge(u);


            return Response.ok(uo, MediaType.APPLICATION_JSON).build();
        }

    }

    public String passwordToMD5(String pass) throws NoSuchAlgorithmException {


        MessageDigest m = MessageDigest.getInstance("MD5");
        m.reset();
        m.update(pass.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        String hashtext = bigInt.toString(16);

        return hashtext;

    }

    public String md5ToSha256(String md5String) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(md5String.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));


        }
        return sb.toString();
    }
    
    
    
    
    
    //PULLS UP A LIST OF CATEGORIES
    @GET
    @Path("/categorylist")
    @Produces(MediaType.APPLICATION_JSON)
    public Response categoryList() {
        Query q = em.createQuery("select c from Category c ");
        List<Category> cateList = q.getResultList();

        System.out.print("tagList called");
        return Response.ok(cateList.toArray(new Category[cateList.size()])).build();


    } 
    
    
    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_XML)
    public Response userlo(){
        
          Query q = em.createQuery("select u from Users u ");
        List<Users> cateList = q.getResultList();

         System.out.print("tagList called");
        return Response.ok(cateList.toArray(new Users[cateList.size()])).build();

    }

}