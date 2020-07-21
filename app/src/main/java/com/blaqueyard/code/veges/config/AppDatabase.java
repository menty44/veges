package com.blaqueyard.code.veges.config;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.blaqueyard.code.veges.model.User;
import com.blaqueyard.code.veges.repository.UserDao;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}