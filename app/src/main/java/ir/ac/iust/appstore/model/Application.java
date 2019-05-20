package ir.ac.iust.appstore.model;

import android.graphics.drawable.Drawable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import ir.ac.iust.appstore.AppStoreApplication;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Application implements SearchableModel
{
    private static final String BASE_URL_ADDRESS= (AppStoreApplication.APP_HOST_ADDRESS+"/Content/Downloads/");

    @JsonProperty("Id")
    private long id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Price")
    private int price;

    /*@JsonProperty("DateTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[X]")
    private Date registerTime;*/

    @JsonProperty("FileUpload")
    private String fileName;

    @JsonProperty("Rate")
    private float rate;

    @JsonProperty("Version")
    private int version;

    @JsonProperty("Developer")
    private String developer;

    @JsonProperty("Logo")
    private Image logo;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("FileSize")
    private String sizeText;

    @JsonProperty("ProductImages")
    private List<Image> images;

    @JsonProperty("Group")
    private Category category;

    @JsonProperty("Comments")
    private List<Comment> comments;

    private String packageName;

    private Drawable icon;

    public Application()
    {

    }

    public Application(String name, Drawable icon,String packageName)
    {
        this.name = name;
        this.icon = icon;
        this.packageName=packageName;
    }

    public String getName()
    {
        if(name!=null&&name.contains("-"))
        {
            return name.split("[-]")[0];
        }
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Drawable getIcon()
    {
        return icon;
    }

    public void setIcon(Drawable icon)
    {
        this.icon = icon;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

/*    public Date getRegisterTime()
    {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime)
    {
        this.registerTime = registerTime;
    }*/

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public float getRate()
    {
        return rate;
    }

    public void setRate(float rate)
    {
        this.rate = rate;
    }

    public int getVersion()
    {
        return version;
    }

    public void setVersion(int version)
    {
        this.version = version;
    }

    public String getDeveloper()
    {
        return developer;
    }

    public void setDeveloper(String developer)
    {
        this.developer = developer;
    }

    public Image getLogo()
    {
        return logo;
    }

    public void setLogo(Image logo)
    {
        this.logo = logo;
    }

    public List<Image> getImages()
    {
        return images;
    }

    public void setImages(List<Image> images)
    {
        this.images = images;
    }

    public Category getCategory()
    {
        return category;
    }

    public void setCategory(Category category)
    {
        this.category = category;
    }

    public List<Comment> getComments()
    {
        return comments;
    }

    public void setComments(List<Comment> comments)
    {
        this.comments = comments;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Float getSize()
    {
        if(sizeText!=null)
            return Float.valueOf(sizeText.replace("MB","").replace("/","."));

        return null;
    }

    public String getSizeText()
    {
        return sizeText;
    }

    public void setSizeText(String sizeText)
    {
        this.sizeText = sizeText;
    }

    public String getUrl()
    {
        return BASE_URL_ADDRESS+fileName;
    }

    public String getPackageName()
    {
        return packageName;
    }

    public void setPackageName(String packageName)
    {
        this.packageName = packageName;
    }

    @NonNull
    @Override
    public String toString()
    {
        return name;
    }
}
