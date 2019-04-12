package ir.ac.iust.appstore.model;

import java.util.List;

public class ApplicationGroup
{
    private String title;
    private List<Application> applications;

    public ApplicationGroup(String title,List<Application> applications)
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
