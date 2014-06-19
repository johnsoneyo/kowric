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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import pojo.Category;
import pojo.Comment;
import pojo.InvestmentList;
import pojo.Job;
import pojo.Notifications;
import pojo.Profile;
import pojo.ProjectJobList;
import pojo.Projects;
import pojo.Shareholder;
import pojo.Subscription;
import pojo.Tags;
import pojo.Users;

        

/**
 *
 * @author johnson eyo
 */
@Path("/userupdate")
@Stateless
//@RolesAllowed("users")
@Interceptors(LoggingInterceptor.class)
public class UpdateResource {

    static final private Logger logger = Logger.getLogger("kowric update logs ");
    @PersistenceContext
    EntityManager em;

   

    @DELETE
    @Path("/removeprofile")
    public Response removeProfile(@QueryParam("detach") int id) {
        Users u = em.find(Users.class, id);
        em.detach(u);
        logger.log(Level.INFO, "user detached from  ");
        return Response.ok(u, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getprofile/{user}")
    public Response getProfile(@PathParam("user") int id) {


        try {

            Profile p = em.find(Profile.class, id);
            return Response.ok(p, MediaType.APPLICATION_JSON).build();
        } catch (NullPointerException no) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

  

    @GET
    @Path("/getobject/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProfileObject(@PathParam("userid") int id) {
        Profile p = em.find(Profile.class, id);
        return Response.ok(p, MediaType.APPLICATION_JSON).build();

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

     
    //UPDATE PROFILE OF HIM
    @POST
    @Path("/updateprofile")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProfile(Profile prof) {

        Profile p = em.find(Profile.class, prof.getUserId());

        prof.setTimeCreated(prof.getTimeCreated());
        prof.setTimeModified(new java.sql.Timestamp(new java.util.Date().getTime()));
        prof.setPicture(p.getPicture());
        prof.setCv(p.getCv());
        Profile pu = em.merge(prof);
        System.out.printf("the phone is %s", prof.getPhone());

        logger.log(Level.INFO, " profile edited  ");
        return Response.ok(pu, MediaType.APPLICATION_JSON).build();
    }

    //VIEW PROJECTS 
    @GET
    @Path("/projectlist/{getuser}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ListProject(@PathParam("getuser") int id) {
        Query q = em.createQuery("select p from Projects p join p.usersList u where u.userId = :userId");

        q.setParameter("userId", id);

        List<Projects> userProj = q.getResultList();
        logger.log(Level.INFO, " projectlist called up  ");
        return Response.ok(userProj.toArray(new Projects[userProj.size()])).build();

    }
    //VIEW PROJECT  

    @GET
    @Path("/viewproject")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response viewProject(@MatrixParam("projectid") int projid) {
        try {
            Projects ManagedProj = em.find(Projects.class, projid);
            logger.log(Level.INFO, " project called up  ");
            return Response.ok(ManagedProj, MediaType.APPLICATION_JSON).build();
        } catch (NullPointerException no) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    //MODIFY PROJECT
    @POST
    @Path("/modifyproject")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modifyProject(Projects proj, @QueryParam("getuser") int id) {
        Projects p = em.find(Projects.class, proj.getProjectId());

        Users u = em.find(Users.class, id);

        proj.setProjectImage(p.getProjectImage());


        u.getProjectsList().add(proj);
        proj.getUsersList().add(u);

        proj.setTagsList(p.getTagsList());

        Projects ManagedProj = em.merge(proj);
        logger.log(Level.INFO, "project merged");
        return Response.ok(ManagedProj, MediaType.APPLICATION_JSON).build();

    }

    //DELETE PROJECT
    @DELETE
    @Path("/deleteproject/{id}")
    public Response deleteProject(@PathParam("id") int id) {
        Projects p = em.find(Projects.class, id);
        em.remove(p);
        logger.log(Level.INFO, "project deleted");
        return Response.ok().entity("project deleted").build();
    }
    //VIEW NOTIFICATIONS

    @GET
    @Path("/usernotifications/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response userNotification(@PathParam("userid") int id) {
        Users us = em.find(Users.class, id);
        List<Notifications> noteList = us.getNotificationsList();
        return Response.ok(noteList.toArray(new Notifications[noteList.size()])).build();
    }

    //VIEW NOTIFICATION
    @GET
    @Path("/viewnotification/{noteid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response notificationMessage(@PathParam("noteid") int id) {
        Notifications no = em.find(Notifications.class, id);
        return Response.ok(no, MediaType.APPLICATION_JSON).build();
    }
    //VIEW TAGLIST
    //COMMENT

    @POST
    @Path("/usercomment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response userComment(Comment comment, @QueryParam("getuser") int id) {
        Users u = em.find(Users.class, id);
        u.getCommentList().add(comment);
        em.merge(u);
        return Response.ok(comment, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/usertaglist/{getuser}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response entrepreneurTags(@PathParam("getuser") int id) {
        Users u = em.find(Users.class, id);
        List<Tags> usertags = u.getTagsList();
        return Response.ok(usertags.toArray(new Tags[usertags.size()])).build();
    }

    //XCREATE JOBX
    @POST
    @Path("/createjob")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createJob(Job job, @QueryParam("getproject") int id) {
        job.setTimeCreated(new java.sql.Date(new java.util.Date().getTime()));
        Projects project = em.find(Projects.class, id);
        project.getJobList().add(job);
        job.setProjectId(project);
        em.persist(job);
        em.merge(project);
        logger.log(Level.INFO, "job created");
        return Response.ok(job, MediaType.APPLICATION_JSON).build();

    }

    //XCREATE JOB TAG 
    @POST
    @Path("/cjt")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addJobTag(List<Tags> tagList, @QueryParam("getjob") int id) {
        Job jb = em.find(Job.class, id);
        for (Tags t : tagList) {
            Tags managedTag = em.find(Tags.class, t.getTagId());

            jb.getTagsList().add(t);

            Job job = em.merge(jb);

        }
        logger.log(Level.INFO, "job tags created");
        return Response.ok().build();
    }

    //PROJECT JOB LIST
    @GET
    @Path("/projectjoblist/{projectid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProjectJobs(@PathParam("projectid") int id) {
        Projects p = em.find(Projects.class, id);
        Query q = em.createQuery("select j from Job j where j.projectId = :projectId ");
        q.setParameter("projectId", p);
        List<Job> jobList = q.getResultList();
        logger.log(Level.INFO, "job projectlist called");
        return Response.ok(jobList.toArray(new Job[jobList.size()])).build();
    }

    //DELETE JOB
    @DELETE
    @Path("/deletejob/{jobid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteJob(@PathParam("jobid") int id) {
        Job jb = em.find(Job.class, id);
        em.remove(jb);
        logger.log(Level.INFO, "job deleted");
        return Response.ok().entity("job deleted").build();
    }

    //ADD PROJECT TO PROJECTLIST
    @POST
    @Path("/addproject")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProjectToList(Projects project, @QueryParam("getuser") int id) {
        try {
            Users u = em.find(Users.class, id);


            System.out.printf("project name s %s", project.getProjectName());
            project.setTimeCreated(new java.sql.Timestamp(new java.util.Date().getTime()));
            project.setProjectStatus("UN-VERIFIED");

            u.getProjectsList().add(project);
            project.getUsersList().add(u);


            em.persist(project);



            logger.log(Level.INFO, "project created add");
            return Response.ok(project, MediaType.APPLICATION_JSON).build();
        } catch (NullPointerException no) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("user not logged").build();
        }
    }

    @POST
    @Path("/addtag")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTagsSubscription(List<Tags> tagList, @QueryParam("getuser") int id) {



        Users u = em.find(Users.class, id);
        u.getTagsList().clear();
        Users ManagedU = em.merge(u);
        for (Tags t : tagList) {
            Tags to = em.find(Tags.class, t.getTagId());
            ManagedU.getTagsList().add(t);
            em.merge(ManagedU);
        }

        logger.log(Level.INFO, "tags added user add");
        return Response.ok(u, MediaType.APPLICATION_XML).build();

    }

    @GET
    @Path("/getusertags")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserTags(@QueryParam("getuser") int id) {
        Query q = em.createQuery("select t from Tags t join t.usersList u where u.userId = :userId");
        q.setParameter("userId", id);
        List<Tags> tList = q.getResultList();
        logger.log(Level.INFO, "get user tags");
        return Response.ok(tList.toArray(new Tags[tList.size()])).build();
    }

    @POST
    @Path("addshareholder")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addShareHolder(List<Shareholder> shareList, @QueryParam("getproject") int id) {

        Projects p = em.find(Projects.class, id);
        for (Shareholder s : shareList) {
            p.getShareholderList().add(s);
            s.setProjectId(p);
            em.merge(s);
            em.merge(p);
        }



        System.out.print("reg2 share method called");

        return Response.ok(p, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/projectpath")
    @Produces(MediaType.APPLICATION_JSON)
    public Response projectPath(@QueryParam("getuser") int u, @QueryParam("getproject") int proj) {

        Users us = em.find(Users.class, u);
        Projects p = em.find(Projects.class, proj);
        boolean value = us.getProjectsList().contains(p);
        if (value == true) {
            return Response.ok(p, MediaType.APPLICATION_JSON).build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }

    }

    /* @GET
     @Produces(MediaType.TEXT_HTML)
     public void newTodo(@Context HttpServletResponse servletResponse) throws IOException {
   
     servletResponse.sendRedirect("../content.html");
     }
   
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/employeejobs/{userid}")
    public Response getJobs(@PathParam("userid") int id) {



        Set<Job> jobSubList = new HashSet();
        Query q;
        Users us = em.find(Users.class, id);
        List<Tags> tagList = us.getTagsList();
        for (Tags t : tagList) {
            q = em.createQuery("select j from Job j join j.tagsList  t where  t.tagId = :tagId");
            q.setParameter("tagId", t.getTagId());
            List<Job> jl = q.getResultList();
            jobSubList.addAll(jl);
            q = em.createQuery("select p.jobId from ProjectJobList p where p.userId = :userId");
            q.setParameter("userId", us);
            List<Job> appliedList = q.getResultList();
            for (Job j : appliedList) {
                jobSubList.remove(j);
            }
        }






        return Response.ok(jobSubList.toArray(new Job[jobSubList.size()])).build();
    }

    @GET
    @Path("/jobapply")
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewToApply(@QueryParam("getjob") int jobid, @QueryParam("getuser") int userid) {


        Users us = em.find(Users.class, userid);
        Job jb = em.find(Job.class, jobid);
        ProjectJobList pjl = new ProjectJobList();
        pjl.setJobId(jb);
        pjl.setUserId(us);
        pjl.setStatus("PENDING");


        Notifications not = new Notifications();
        not.setNotificationMessage(String.format("One %s applied for a job titled %s ", us.getUserType(), jb.getJobTitle()));
        not.setTimeCreated(new java.sql.Timestamp(new java.util.Date().getTime()));

        Users u = jb.getProjectId().getUsersList().get(0);
        not.getUsersList().add(u);

        em.persist(not);

        em.persist(pjl);
        return Response.ok(pjl, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/appliedjobs/{getuser}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeJobCount(@PathParam("getuser") int id) {
        Users us = em.find(Users.class, id);
        /*   
         Query q =   em.createQuery("select p.jobId from ProjectJobList p where p.userId = :userId");
         q.setParameter("userId",us );
         List<Job>jobList = q.getResultList();
         */

        Query q = em.createQuery("select p from ProjectJobList p where p.userId = :userId");
        q.setParameter("userId", us);
        List<ProjectJobList> pjl = q.getResultList();
        logger.log(Level.INFO, "applied jobs list");
        return Response.ok(pjl.toArray(new ProjectJobList[pjl.size()])).build();

    }

    @GET
    @Path("/viewapplicants/{jobid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getView(@PathParam("jobid") int id) {

        Query q = em.createQuery("select p from ProjectJobList p where p.jobId = :jobId");
        Job j = em.find(Job.class, id);
        q.setParameter("jobId", j);
        List<ProjectJobList> pjl = q.getResultList();

        List<ProjectListCustom> plj = new ArrayList();


        for (ProjectJobList p : pjl) {
            String fn = String.format("%s %s", p.getUserId().getFirstName(), p.getUserId().getLastName());
            ProjectListCustom po = new ProjectListCustom(p.getUserId().getUserId(), fn, p.getProjectJobId(), p.getJobId().getJobId(), p.getJobId().getJobTitle(), p.getStatus());
            plj.add(po);

        }
        logger.log(Level.INFO, "applied jobs list");
        return Response.ok(plj.toArray(new ProjectListCustom[plj.size()])).build();
    }

    @GET
    @Path("/getcv")
    @Produces({"application/msword"})
    public Response getCV(@QueryParam("profile") int id) throws IOException {

        Profile p = em.find(Profile.class, id);
        try {
            logger.log(Level.INFO, "iyoye");
            return Response.ok(new ByteArrayInputStream(p.getCv())).build();
        } catch (NullPointerException no) {
            logger.log(Level.INFO, "iyoye no cv");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/getapplicantlist")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getApplicantList(@QueryParam("getproject") int id) {

        Query q = em.createQuery("select p from ProjectJobList p join p.jobId j where j.projectId  = :projectId");
        Projects pr = em.find(Projects.class, id);
        q.setParameter("projectId", pr);
        List<ProjectJobList> pjl = q.getResultList();

        List<ProjectListCustom> plj = new ArrayList();
        for (ProjectJobList p : pjl) {
            String fn = String.format("%s %s", p.getUserId().getFirstName(), p.getUserId().getLastName());
            ProjectListCustom po = new ProjectListCustom(p.getUserId().getUserId(), fn, p.getProjectJobId(), p.getJobId().getJobId(), p.getJobId().getJobTitle(), p.getStatus());
            plj.add(po);

        }
        logger.log(Level.INFO, "applied jobs total list");
        return Response.ok(plj.toArray(new ProjectListCustom[plj.size()])).build();
    }

    @GET
    @Path("/hero")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStuf() {
        List< SubSetList> sb = new ArrayList();
        SubSetList a = new SubSetList("ope");
        SubSetList b = new SubSetList("ope");
        SubSetList c = new SubSetList("ope");
        sb.add(a);
        sb.add(b);
        sb.add(c);


        return Response.ok(sb.toArray(new SubSetList[sb.size()])).build();
    }

    @GET
    @Path("/approvejob/{pjid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response approveJob(@PathParam("pjid") int id) {

        ProjectJobList pj = em.find(ProjectJobList.class, id);
        pj.setStatus("APPROVED");

        Notifications no = new Notifications();
        no.setNotificationMessage(String.format("you have been approved for the job with the title %s", pj.getJobId().getJobTitle()));
        no.setRed(false);
        no.setTimeCreated(new java.sql.Timestamp(new java.util.Date().getTime()));
        no.getUsersList().add(pj.getUserId());

        em.persist(no);
        em.merge(pj);
        return Response.ok(pj, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/pendjob/{pjid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response pendJob(@PathParam("pjid") int id) {

        ProjectJobList pj = em.find(ProjectJobList.class, id);
        pj.setStatus("PENDING");

        em.merge(pj);

        return Response.ok(pj, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/disapprovejob/{pjid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response disApproveJob(@PathParam("pjid") int id) {

        ProjectJobList pj = em.find(ProjectJobList.class, id);
        pj.setStatus("DISAPPROVED");
        em.merge(pj);

        Notifications no = new Notifications();
        no.setNotificationMessage(String.format("you have been dis-approved for the job with the title %s", pj.getJobId().getJobTitle()));
        no.setRed(false);
        no.setTimeCreated(new java.sql.Timestamp(new java.util.Date().getTime()));
        no.getUsersList().add(pj.getUserId());

        em.persist(no);
        return Response.ok(pj, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/getuser/{getuser}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("getuser") int id) {
        Users pj = em.find(Users.class, id);
        try {
            logger.log(Level.INFO, "user retrieved");
            return Response.ok(pj, MediaType.APPLICATION_JSON).build();
        } catch (NullPointerException b) {
            logger.log(Level.INFO, "null user retrieved");
            return Response.status(Response.Status.BAD_REQUEST).build();

        }

    }

    @POST
    @Path("/changepicture")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response changePishur(@FormDataParam("file") InputStream file, @QueryParam("getuser") int id) {
        Profile p = em.find(Profile.class, id);

        System.out.printf("the id is %d", p.getUserId());
        try {
            byte[] upload = IOUtils.toByteArray(file);
            p.setPicture(upload);
            em.merge(p);

            logger.log(Level.INFO, "photo changed");
            return Response.ok(MediaType.APPLICATION_JSON).build();
        } catch (IOException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }


    }

    //COMMENT
    @POST
    @Path("/comment")
    @Produces(MediaType.APPLICATION_JSON)
    public Response commentOnProject(@QueryParam("getuser") int usid, @QueryParam("getproject") int prid, Comment comment) {

        Projects pr = em.find(Projects.class, prid);
        Users us = em.find(Users.class, usid);

        comment.setCommentUser(us);
        comment.setTimeSaid(new java.sql.Timestamp(new java.util.Date().getTime()));
        comment.setProjectId(pr);
        em.persist(comment);
        logger.log(Level.INFO, "comment made");


        Notifications not = new Notifications();
        not.setRed(false);
        not.setNotificationMessage(String.format("One %s, named %s commented on one of your projects titled %s ", us.getUserType(), us.getFirstName(), pr.getProjectName()));
        not.setTimeCreated(new java.sql.Timestamp(new java.util.Date().getTime()));

      Query q =  em.createNamedQuery("select s.userId from Subscription s where s.projectId = :projectId  ");
        q.setParameter("projectId", pr);
       List<Users>userList = q.getResultList();
      
       
       for(Users uo : userList){
           uo.getNotificationsList().add(not);
           em.merge(uo);
           em.merge(not);
       }
           
        
        Users u = pr.getUsersList().get(0);
        not.getUsersList().add(u);

        em.persist(not);

        return Response.ok(comment, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/getcomments/{projectid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCommentList(@PathParam("projectid") int id) {

        Projects p = em.find(Projects.class, id);
        Query q = em.createQuery("select c from Comment c where c.projectId = :projectId order by c.timeSaid desc");
        q.setParameter("projectId", p);
        List<Comment> comment = q.getResultList();
        logger.log(Level.INFO, "comments called");
        return Response.ok(comment.toArray(new Comment[comment.size()])).build();

    }

    @GET
    @Path("/getinvestproject/{getuser}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInvestProject(@PathParam("getuser") int id) {
        Query q = em.createQuery("select t from Tags t join t.usersList u where u.userId = :userId ");
        q.setParameter("userId", id);
        List<Tags> tagList = q.getResultList();

        Set<Projects> projectList = new HashSet();

        for (Tags t : tagList) {
            q = em.createQuery("select p from Projects p join p.tagsList t where t.tagId = :tagId and p.projectStatus ='VERIFIED'");
            q.setParameter("tagId", t.getTagId());
            List<Projects> pl = q.getResultList();
            projectList.addAll(pl);
        }
        logger.log(Level.INFO, "invest project are called");
        return Response.ok(projectList.toArray(new Projects[projectList.size()])).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/notificationthread")
    public Response notificationThread(@QueryParam("getuser") int id) {

        Query q = em.createQuery("select n from Notifications n join n.usersList u where u.userId = :userId order by n.timeCreated desc");
        q.setParameter("userId", id);
        List<Notifications> nl = q.getResultList();
        logger.log(Level.INFO, "notification thread called");
        return Response.ok(nl.toArray(new Notifications[nl.size()])).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/readnote")
    public Response readMessage(@QueryParam("getnote") int id) {

        Notifications n = em.find(Notifications.class, id);
        n.setRed(true);
        em.merge(n);
        return Response.ok(n, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getunread")
    public Response getUnread(@QueryParam("getuser") int id) {
        Users us = em.find(Users.class, id);
        Query q = em.createQuery("select n from Notifications n join n.usersList u where u.userId = :userId and n.red = false ");
        q.setParameter("userId", id);
        List<Notifications> nl = q.getResultList();
        int count = nl.size();
        String viso = String.valueOf(count);
        return Response.ok(viso, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/joinproject")
    public Response joinProject(@QueryParam("getuser") int usid, @QueryParam("getproject") int prid, InvestmentList inl) {

        Users us = em.find(Users.class, usid);
        Projects pr = em.find(Projects.class, prid);
        inl.setProjectId(pr);
        inl.setUserId(us);
        em.persist(inl);

        Notifications not = new Notifications();
        not.setRed(false);
        not.setNotificationMessage(String.format("One %s, named %s joined one of your projects titled %s ", us.getUserType(), us.getFirstName(), pr.getProjectName()));
        not.setTimeCreated(new java.sql.Timestamp(new java.util.Date().getTime()));

        Users u = pr.getUsersList().get(0);
        not.getUsersList().add(u);

        em.persist(not);
        logger.log(Level.INFO, "project joined");
        return Response.ok(inl, MediaType.APPLICATION_JSON).build();

    }

    @GET
    @Path("/checkinvestuser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkInverstUser(@QueryParam("getuser") int usid, @QueryParam("getproject") int prid) {
        Users us = em.find(Users.class, usid);
        Projects pr = em.find(Projects.class, prid);

        Query q = em.createQuery("select i from InvestmentList i where i.userId = :userId and i.projectId = :projectId");
        q.setParameter("userId", us);
        q.setParameter("projectId", pr);
        try {
            InvestmentList i = (InvestmentList) q.getSingleResult();
            logger.log(Level.INFO, "found");
            return Response.ok().entity("found").build();
        } catch (NoResultException no) {
            logger.log(Level.INFO, "not found");
            return Response.ok().entity("not found").build();
        }

    }

    @GET
    @Path("/projectimage")
    @Produces({"image/jpeg"})
    public Response getProjectImage(@QueryParam("getproject") int id) throws IOException {


        Projects p = em.find(Projects.class, id);
        try {
            return Response.ok(new ByteArrayInputStream(p.getProjectImage())).build();
        } catch (NullPointerException no) {

            File file = new File("no-image-found.png");

            byte[] imo = FileUtils.readFileToByteArray(file);

            return Response.ok(new ByteArrayInputStream(imo)).build();
        }
    }

    @GET
    @Path("/getprojectmembers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProjectMembers(@QueryParam("getproject") int id) {
        Projects p = em.find(Projects.class, id);
        Query q = em.createQuery("select i.userId from InvestmentList i where i.projectId = :projectId ");
        q.setParameter("projectId", p);
        List<Users> uList = q.getResultList();
        Set<Users> usList = new HashSet();
        usList.addAll(uList);
        logger.log(Level.INFO, "project members gotten");
        return Response.ok(usList.toArray(new Users[usList.size()])).build();
    }

    @GET
    @Path("/projectowner")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProjectOwner(@QueryParam("getproject") int id) {

        Query q = em.createQuery("select u from Users u join u.projectsList p where p.projectId = :projectId");
        q.setParameter("projectId", id);


        List<Users> usList = q.getResultList();
        logger.log(Level.INFO, "project owner list retrieved");
        return Response.ok(usList.toArray(new Users[usList.size()])).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/editshareholder")
    public Response editShair(List<Shareholder> shareList, @QueryParam("getproject") int p) {

        Projects po = em.find(Projects.class, p);
        for (Shareholder s : shareList) {
            po.getShareholderList().add(s);
            s.setProjectId(po);
            em.merge(po);

        }
        logger.log(Level.INFO, "edit share called");
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getshareholder")
    public Response getShareHair(@QueryParam("getproject") int id) {

        Projects p = em.find(Projects.class, id);
        Query q = em.createQuery("select s from Shareholder s where s.projectId = :projectId");
        q.setParameter("projectId", p);
        List<Shareholder> shareList = q.getResultList();
        return Response.ok(shareList.toArray(new Shareholder[shareList.size()])).build();

    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/deletenote")
    public Response deleteNote(@QueryParam("getnote") int id) {

        Notifications n = em.find(Notifications.class, id);
        em.remove(n);
        return Response.ok().build();
    }

    @POST
    @Path("/edittags")
    @Produces(MediaType.APPLICATION_JSON)
    public Response editTags(List<Tags> tagList, @QueryParam("getproject") int id) {
        Projects p = em.find(Projects.class, id);

        p.getTagsList().clear();
        Projects po = em.merge(p);
        for (Tags t : tagList) {
            Tags to = em.find(Tags.class, t.getTagId());
            po.getTagsList().add(t);
            em.merge(po);

        }

        logger.log(Level.INFO, "tags modified");
        return Response.ok().build();
    }

    @GET
    @Path("/getprojecttags")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProjectTags(@QueryParam("getproject") int id) {

        Query q = em.createQuery("select t from Tags t join t.projectsList p where p.projectId = :projectId");
        q.setParameter("projectId", id);
        List<Tags> tgList = q.getResultList();
        logger.log(Level.INFO, " p tags called");
        return Response.ok(tgList.toArray(new Tags[tgList.size()])).build();
    }

    @GET
    @Path("/members")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMembers(@QueryParam("getproject") int prid) {

        Projects p = em.find(Projects.class, prid);
        Query q = em.createQuery("select i.userId from InvestmentList i where i.projectId = :projectId ");
        q.setParameter("projectId", p);
        List<Users> inList = q.getResultList();

        q = em.createQuery("select p.userId from ProjectJobList p join p.jobId j where j.projectId  = :projectId and p.status= 'APPROVED' ");
        q.setParameter("projectId", p);
        List<Users> emList = q.getResultList();

        q = em.createQuery("select u from Users u join u.projectsList p where p.projectId = :projectId ");
        q.setParameter("projectId", prid);
        List<Users> owList = q.getResultList();

        Set<Users> buildList = new HashSet();
        buildList.addAll(inList);
        buildList.addAll(emList);
        buildList.addAll(owList);
        logger.log(Level.INFO, "project members gotten");
        return Response.ok(buildList.toArray(new Users[buildList.size()])).build();

    }

    @POST
    @Path("/inviteusertoproject")
    @Produces(MediaType.APPLICATION_JSON)
    public void inviteUser(@QueryParam("email") String email) {

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
                    InternetAddress.parse(email));
            message.setSubject("Kowric Invite To Project ");
            message.setText("");

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }




    }

    @GET
    @Path("/checkuserrelation")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkUserRelations(@QueryParam("getuser") int usid, @QueryParam("getproject") int prid) {
        Users uo = em.find(Users.class, usid);
        Projects po = em.find(Projects.class, prid);

        Query q = em.createQuery("select p.userId from ProjectJobList p join p.jobId j where j.projectId  = :projectId and p.status= 'APPROVED' and p.userId = :userId ");
        q.setParameter("projectId", po);
        q.setParameter("userId", uo);
        try {
            Users u = (Users) q.getSingleResult();
            logger.log(Level.INFO, "found");
            return Response.ok().entity("found").build();
        } catch (NoResultException no) {
            logger.log(Level.INFO, "not found");
            return Response.ok().entity("not found").build();
        }

    }

    @POST
    @Path("/createtag")
    @Consumes
    @Produces
    public Response createTag(Tags t) {

        em.persist(t);
        return Response.ok().build();
    }

    @POST
    @Path("/createCategory")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCategory(Category cat) {

        em.persist(cat);
        return Response.ok().build();
    }

    @GET
    @Path("/deletecategory/{catid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCategory(@PathParam("catid") int catid) {
        Category cat = em.find(Category.class, catid);

        for (Projects p : cat.getProjectsList()) {
            p.setCategoryId(null);
            em.merge(p);
        }

        em.remove(cat);
        return Response.ok().entity("category delete Success").build();
    }

    @GET
    @Path("/deletetag/{tagid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response tagDelete(@PathParam("tagid") int tagid) {
        Tags t = em.find(Tags.class, tagid);
        em.remove(t);
        return Response.ok().entity("tag delete Success").build();
    }
    
    @POST
    @Path("/createsubscription")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSubscription(@QueryParam("userid")int userid,@QueryParam("projid")int projid){
   Users uo =  em.find(Users.class,userid);  
   Projects po = em.find(Projects.class, projid);
   
   Subscription sub  = new Subscription();
   sub.setUserId(uo);
   sub.setProjectId(po);
   em.persist(sub);
        
     return Response.ok().build();   
    }
    
    @GET
    @Path("/getsubcriptions")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSubscriptions(@QueryParam("userid")int userid){
       Users uo =  em.find(Users.class,userid);  
     List<Subscription>subList =  uo.getSubscriptionList();
        
     return Response.ok(subList.toArray(new Subscription[subList.size()])).build();   
    }
    
    
    
}
