package com.greylabs.todotoday.data.db.dao

import androidx.room.*
import com.greylabs.todotoday.data.db.entity.TaskEntity
import io.reactivex.Single

private const val TABLE_NAME = "TaskEntity"

@Dao
interface TaskDao {
    @Query("SELECT * FROM $TABLE_NAME WHERE id=:taskId")
    fun getById(taskId: String): TaskEntity

    @Query("SELECT * FROM $TABLE_NAME WHERE name LIKE :query")
    fun findByName(query: String): List<TaskEntity>

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAll(): List<TaskEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(task: TaskEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(task: TaskEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(task: List<TaskEntity>)

    @Delete
    fun delete(task: TaskEntity)

    @Query("DELETE FROM $TABLE_NAME WHERE name=:name")
    fun deleteByName(name: String)

    @Query("DELETE FROM $TABLE_NAME")
    fun deleteAll()
}