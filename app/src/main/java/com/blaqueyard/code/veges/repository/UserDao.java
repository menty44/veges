package com.blaqueyard.code.veges.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.blaqueyard.code.veges.model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
