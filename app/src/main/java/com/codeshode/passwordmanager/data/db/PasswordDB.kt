package com.codeshode.passwordmanager.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codeshode.passwordmanager.data.model.Password

@Database(
    entities = [Password::class],
    version = 1,
    exportSchema = false
)
abstract class PasswordDB: RoomDatabase() {
    abstract val passwordDao: PasswordDao

    companion object{
        @Volatile
        private var INSTANCE: PasswordDB? = null

        fun getInstance(context: Context): PasswordDB {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PasswordDB::class.java,
                        "Password Database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}