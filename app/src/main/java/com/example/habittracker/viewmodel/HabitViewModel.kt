package com.example.habittracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HabitViewModel : ViewModel() {

    val habitList = MutableLiveData<ArrayList<Habit>>()

    fun addHabit(habit: Habit) {
        val list = habitList.value ?: ArrayList()
        list.add(habit)
        habitList.value = list
    }
}