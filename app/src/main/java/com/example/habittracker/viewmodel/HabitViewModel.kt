package com.example.habittracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.habittracker.data.HabitRepository
import com.example.habittracker.model.local.AppDatabase
import com.example.habittracker.model.Habit
import kotlinx.coroutines.launch

class HabitViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: HabitRepository
    val habitList: LiveData<List<Habit>>

    init {
        val habitDao = AppDatabase.getInstance(application).habitDao()
        repository = HabitRepository(habitDao)
        habitList = repository.allHabits
    }

    fun addHabit(habit: Habit) {
        viewModelScope.launch {
            repository.insertHabit(habit)
        }
    }

    fun updateHabit(habit: Habit) {
        viewModelScope.launch {
            repository.updateHabit(habit)
        }
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch {
            repository.deleteHabit(habit)
        }
    }

    fun getHabitById(id: Int): LiveData<Habit> {
        return repository.getHabitById(id)
    }
}