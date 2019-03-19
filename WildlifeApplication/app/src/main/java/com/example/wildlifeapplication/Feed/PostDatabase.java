package com.example.wildlifeapplication.Feed;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Post.class}, version = 1, exportSchema = false)
public abstract class PostDatabase extends RoomDatabase {

    public abstract PostDA0 postDA0();
}
