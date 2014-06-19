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
@XmlRootElement(name="ProjectList")
public class ProjectListCustom {

    private int userid;
    private String fullname;
    private int jobid;
    private String status;
    private String jobTitle;
    private int projectListId;
    
    public ProjectListCustom() {
    }
    
    @XmlElement(name="userId")
    public int getUserid() {
        return userid;
    }

    public ProjectListCustom(int userid,String fullname,int projectListId, int jobid,String jobTitle, String status) {
        this.userid = userid;
        this.jobid = jobid;
        this.status = status;
        this.fullname = fullname;
        this.jobTitle = jobTitle;
        this.projectListId = projectListId;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
   @XmlElement(name="jobId")
    public int getJobid() {
        return jobid; 
    }

    public void setJobid(int jobid) {
        this.jobid = jobid;
    }
     @XmlElement(name="status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
   @XmlElement(name="fullname") 
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    @XmlElement(name="jobTitle") 
    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
    @XmlElement(name="projectListId")
    public int getProjectListId() {
        return projectListId;
    }
    
    public void setProjectListId(int projectListId) {
        this.projectListId = projectListId;
    }

   
    
    
    
}
