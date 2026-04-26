package com.example.habittracker.data

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.example.habittracker.model.Habit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HabitRepository private constructor(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("habit_data", Context.MODE_PRIVATE)
    private val gson = Gson()

    private val _habitList = MutableLiveData<ArrayList<Habit>>(ArrayList())
    val habitList: MutableLiveData<ArrayList<Habit>> = _habitList

    init {
        loadData()
    }

    private fun loadData() {
        val json = prefs.getString("habits", null)
        if (json != null) {
            val type = object : TypeToken<ArrayList<Habit>>() {}.type
            val habits: ArrayList<Habit> = gson.fromJson(json, type)
            _habitList.value = habits
        } else {
            _habitList.value = ArrayList()
        }
    }

    private fun saveData() {
        val json = gson.toJson(_habitList.value)
        prefs.edit().putString("habits", json).apply()
    }

    fun addHabit(habit: Habit) {
        val currentList = _habitList.value ?: ArrayList()
        val newList = ArrayList(currentList)
        newList.add(habit)
        _habitList.value = newList
        saveData()
    }

    fun updateProgress(habitToUpdate: Habit, newProgress: Int) {
        val currentList = _habitList.value ?: return
        val index = currentList.indexOfFirst {
            it.name == habitToUpdate.name &&
                    it.description == habitToUpdate.description
        }

        if (index != -1) {
            val updatedHabit = currentList[index].copy(progress = newProgress)
            val newList = ArrayList(currentList)
            newList[index] = updatedHabit
            _habitList.value = newList
            saveData()
        }
    }

    fun clearAll() {
        _habitList.value = ArrayList()
        saveData()
    }

    companion object {
        @Volatile
        private var instance: HabitRepository? = null

        fun getInstance(context: Context): HabitRepository {
            return instance ?: synchronized(this) {
                instance ?: HabitRepository(context.applicationContext).also { instance = it }
            }
        }
    }
}