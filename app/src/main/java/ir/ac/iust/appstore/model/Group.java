package ir.ac.iust.appstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class Group
{
    private String title;

    private List<Application> applications;

    public Group(String title, List<Application> applications)
    {
        this.title = title;
        this.applications=applications;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public List<Application> getApplications()
    {
        return applications;
    }

    public void setApplications(List<Application> applications)
    {
        this.applications = applications;
    }
}
