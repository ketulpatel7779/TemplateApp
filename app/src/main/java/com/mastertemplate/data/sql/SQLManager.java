package com.mastertemplate.data.sql;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.mastertemplate.data.sql.table.user.User;
import com.mastertemplate.data.sql.table.user.UserDao;

import static com.mastertemplate.AppClass.getAppContext;

/**
 * Class contains all the necessary methods to interact with @{@link android.database.sqlite.SQLiteDatabase}
 */
@Database(entities = {User.class}, version = 1)
public abstract class SQLManager extends RoomDatabase {

    private static SQLManager INSTANCE;

    public abstract UserDao userDao();
    /**
     * Get instance of @{@link SQLManager}
     */
    private static SQLManager getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), SQLManager.class, "user-database")
                            .build();
        }
        return INSTANCE;
    }

    /**
     * Get instance of @{@link User} table to perform any operation
     * @return UserDao
     */
    public static UserDao getUserTable()
    {
        return getAppDatabase(getAppContext()).userDao();
    }

}