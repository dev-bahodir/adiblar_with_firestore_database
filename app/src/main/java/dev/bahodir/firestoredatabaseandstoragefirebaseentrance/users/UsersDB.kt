package dev.bahodir.firestoredatabaseandstoragefirebaseentrance.users

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
/*
@Database(entities = [Users::class], version = 1)*/
abstract class UsersDB : RoomDatabase() {
/*    abstract fun usersDAO(): UsersDAO

    companion object {
        private var usersDB: UsersDB? = null

        @Synchronized
        fun getInstance(context: Context): UsersDB {
            if (usersDB == null) {
                usersDB = Room
                    .databaseBuilder(context, UsersDB::class.java, "helper")
                    .allowMainThreadQueries()
                    .build()
            }
            return usersDB!!
        }
    }*/
}