package de.kejanu.drinkinggame.Adapters;

import android.graphics.drawable.Drawable;

public class Category {
    private int imageID;
    private String name;
    private String summary;

    public Category(String name, String summary, int imageID) {
        this.imageID = imageID;
        this.name = name;
        this.summary = summary;
    }

    public Category(String name, String summary) {
        this.name = name;
        this.summary = summary;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
