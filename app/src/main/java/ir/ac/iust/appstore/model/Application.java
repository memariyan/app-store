package ir.ac.iust.appstore.model;

public class Application
{
    private String name;

    private int iconRes;

    public Application(String name, int iconRes)
    {
        this.name = name;
        this.iconRes = iconRes;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getIconRes()
    {
        return iconRes;
    }

    public void setIconRes(int iconRes)
    {
        this.iconRes = iconRes;
    }
}
