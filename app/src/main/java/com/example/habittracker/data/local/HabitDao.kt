package com.example.habittracker.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.habittracker.model.Habit

@Dao
interface HabitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: Habit)
    @Update
    suspend fun updateHabit(habit: Habit)
    @Delete
    suspend fun deleteHabit(habit: Habit)
    @Query("SELECT * FROM habit ORDER BY id ASC")
    fun getAllHabits(): LiveData<List<Habit>>
    @Query("SELECT * FROM habit WHERE id = :id LIMIT 1")
    fun getHabitById(id: Int): LiveData<Habit>
}