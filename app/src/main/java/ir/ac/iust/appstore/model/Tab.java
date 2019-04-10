package ir.ac.iust.appstore.model;

import android.view.View;
import android.widget.ImageView;

import ir.ac.iust.appstore.view.widget.CustomTextView;

public class Tab
{
    public enum Type
    {
        MY_APPS, HOME, CATEGORIES;
    }

    private Type type;
    private ImageView icon;
    private CustomTextView title;
    private ImageView line;
    private View button;
    private OnTabClickListener clickListener;

    public Tab(Type type, View button, ImageView icon, CustomTextView title, ImageView line, OnTabClickListener listener)
    {
        this.button=button;
        this.type = type;
        this.icon = icon;
        this.title = title;
        this.line = line;
        this.clickListener=listener;
        this.button.setOnClickListener(view -> clickListener.onClick(Tab.this));
    }

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    public ImageView getIcon()
    {
        return icon;
    }

    public void setIcon(ImageView icon)
    {
        this.icon = icon;
    }

    public CustomTextView getTitle()
    {
        return title;
    }

    public void setTitle(CustomTextView title)
    {
        this.title = title;
    }

    public ImageView getLine()
    {
        return line;
    }

    public void setLine(ImageView line)
    {
        this.line = line;
    }

    public View getButton()
    {
        return button;
    }

    public void setButton(View button)
    {
        this.button = button;
    }
}
