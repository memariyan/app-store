package ir.ac.iust.appstore.model;

public class Comment
{
    private String date;
    private double rate;
    private String author;
    private String text;

    public Comment( String author,String date,double rate, String text)
    {
        this.date = date;
        this.rate = rate;
        this.author = author;
        this.text = text;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public double getRate()
    {
        return rate;
    }

    public void setRate(double rate)
    {
        this.rate = rate;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }
}
