package com.example.wildlifeapplication.Feed;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PostDA0 {

    @Query("SELECT * FROM Post")
    List<Post> getAllPosts();

    @Query("SELECT * FROM Post WHERE id = :id")
    Post getPostid(int id);

    @Insert
    void insertPosts(Post... Post);

    @Query("DELETE FROM Post")
    void clearPosts();


}
