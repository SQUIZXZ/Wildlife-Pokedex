package com.example.wildlifeapplication.Feed;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Insert;

import java.util.List;

@Dao
public interface PostDA0 {

    @Query("SELECT * FROM Post")
    List<Post> getAllBooks();

    @Insert
    void insertBooks(Post... Post);

    @Query("DELETE FROM Post")
    void clearBooks();


}
