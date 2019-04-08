package com.example.wildlifeapplication.Feed;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;


@Entity
public class Post {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "post_username")
    private String username;

    @ColumnInfo(name = "post_caption")
    private String caption;

    @ColumnInfo(name = "image_path")
    private String imagePath;

    @Ignore
    public Post(String aUsername, String aCaption, String anImagePath) {
        this.username = aUsername;
        this.caption = aCaption;
        this.imagePath = anImagePath;
    }

    public Post() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
