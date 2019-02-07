package com.greylabs.todotoday.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.greylabs.todotoday.data.db.dao.TaskDao
import com.greylabs.todotoday.data.db.entity.TaskEntity

const val DB_NAME = "app_db"

@Database(entities = [TaskEntity::class], version = 1)

abstract class AppDatabase: RoomDatabase() {

    abstract fun getTaskDao(): TaskDao

}