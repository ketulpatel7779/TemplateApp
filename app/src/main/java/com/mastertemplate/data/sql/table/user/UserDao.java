package com.mastertemplate.data.sql.table.user;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;
/**
 * Class contains method signature and respective query to perform all the supported operations on User table
 */
@Dao
public interface UserDao {

    /**
     * Get list of all the @{@link User}  from database
     */
    @Query("SELECT * FROM user")
    List<User> getAll();

    /**
     * Get @{@link User} with matching provided firstname and lastname
     * @param firstName first name of user
     * @param lastName last name of user
     */
    @Query("SELECT * FROM user where first_name LIKE  :firstName AND last_name LIKE :lastName")
    User findByName(String firstName, String lastName);

    /**
     * Get total user count
     */
    @Query("SELECT COUNT(*) from user")
    int countUsers();

    /**
     * Insert all users
     * @param users multiple @{@link User} objects to save
     */
    @Insert
    void insertAll(User... users);

    /**
     * Delete user
     * @param user @{@link User} objects to delete
     */
    @Delete
    void delete(User user);
}