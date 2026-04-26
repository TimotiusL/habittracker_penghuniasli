package com.example.habittracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.habittracker.data.HabitRepository
import com.example.habittracker.model.Habit

class HabitViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = HabitRepository.getInstance(application)
    val habitList: LiveData<ArrayList<Habit>> = repository.habitList

    fun addHabit(habit: Habit) {
        repository.addHabit(habit)
    }

    fun updateProgress(habitToUpdate: Habit, newProgress: Int) {
        repository.updateProgress(habitToUpdate, newProgress)
    }
}