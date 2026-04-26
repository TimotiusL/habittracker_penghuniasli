package com.example.habittracker.view

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.habittracker.R
import com.example.habittracker.model.Habit
import com.example.habittracker.viewmodel.HabitViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class AddHabitFragment : Fragment(R.layout.fragment_add_habit) {

    private val habitViewModel: HabitViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = view.findViewById<TextInputEditText>(R.id.etHabitName)
        val desc = view.findViewById<TextInputEditText>(R.id.etDescription)
        val goal = view.findViewById<TextInputEditText>(R.id.etGoal)
        val unit = view.findViewById<TextInputEditText>(R.id.etUnit)
        val actvSelectIcon = view.findViewById<AutoCompleteTextView>(R.id.actvSelectIcon)
        val btnCreate = view.findViewById<MaterialButton>(R.id.btnCreateHabit)

        setupIconDropdown(actvSelectIcon)

        btnCreate.setOnClickListener {

            val habitName = name.text.toString().trim()
            val habitDesc = desc.text.toString().trim()
            val habitGoal = goal.text.toString().trim()
            val habitUnit = unit.text.toString().trim()
            val selectedIcon = actvSelectIcon.text.toString()

            if (habitName.isEmpty() ||
                habitDesc.isEmpty() ||
                habitGoal.isEmpty() ||
                habitUnit.isEmpty() ||
                selectedIcon.isEmpty()
            ) {
                Toast.makeText(requireContext(),
                    "All fields must be filled",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val iconRes = getIconResource(selectedIcon)

            val habit = Habit(
                name = habitName,
                description = habitDesc,
                goal = habitGoal.toInt(),
                progress = 0,
                unit = habitUnit,
                icon = iconRes
            )

            habitViewModel.addHabit(habit)

            Toast.makeText(requireContext(),
                "Habit Created!",
                Toast.LENGTH_SHORT).show()

            Navigation.findNavController(view)
                .navigate(R.id.action_addHabitFragment_to_dashboardFragment)
        }
    }

    private fun setupIconDropdown(actvSelectIcon: AutoCompleteTextView) {
        val iconOptions = listOf("Water", "Walking", "Praying", "Sleeping", "Eating", "Exercise", "Reading")

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            iconOptions
        )

        actvSelectIcon.setAdapter(adapter)
        actvSelectIcon.setText(iconOptions[0], false)
    }

    private fun getIconResource(iconName: String): Int {
        return when (iconName.lowercase()) {
            "water" -> R.drawable.water_drop
            "walking" -> R.drawable.walking
            "praying" -> R.drawable.pray
            "sleeping" -> R.drawable.sleep
            "eating" -> R.drawable.food
            "exercise" -> R.drawable.exercise
            "reading" -> R.drawable.book
            else -> R.drawable.water_drop
        }
    }
}