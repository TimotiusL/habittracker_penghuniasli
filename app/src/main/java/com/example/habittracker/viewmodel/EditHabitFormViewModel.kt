package com.example.habittracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditHabitFormViewModel : ViewModel() {

    val habitName = MutableLiveData("")
    val description = MutableLiveData("")
    val goal = MutableLiveData("")
    val unit = MutableLiveData("")
}