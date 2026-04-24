package com.example.habittracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habittracker.model.Habit

class HabitViewModel : ViewModel() {

    val habitList = MutableLiveData<ArrayList<Habit>>()

    fun addHabit(habit: Habit) {
        val list = habitList.value ?: ArrayList()
        list.add(habit)
        habitList.value = list
    }
}