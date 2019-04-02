package com.example.wildlifeapplication.Feed;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity
public class Post {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "post_username")
    private String username;

    @ColumnInfo(name = "post_caption")
    private String caption;

//    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
//    private byte[] image;

    public Post(String username, String caption) {
        this.username = username;
        this.caption = caption;

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

//    public byte[] getImage() {
//        return image;
//    }
//
//    public void setImage(byte[] image) {
//        this.image = image;
//    }
}
