package com.example.habittracker.view

import android.os.Bundle
import android.view.View
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
        val btnCreate = view.findViewById<MaterialButton>(R.id.btnCreateHabit)

        btnCreate.setOnClickListener {

            val habitName = name.text.toString()
            val habitDesc = desc.text.toString()
            val habitGoal = goal.text.toString()
            val habitUnit = unit.text.toString()

            if (habitName.isEmpty() ||
                habitDesc.isEmpty() ||
                habitGoal.isEmpty() ||
                habitUnit.isEmpty()
            ) {
                Toast.makeText(requireContext(),
                    "All fields must be filled",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val habit = Habit(
                habitName,
                habitDesc,
                habitGoal.toInt(),
                0,
                habitUnit,
                R.drawable.ic_launcher_foreground
            )

            habitViewModel.addHabit(habit)

            Toast.makeText(requireContext(),
                "Habit Created!",
                Toast.LENGTH_SHORT).show()

            Navigation.findNavController(view)
               .navigate(R.id.action_addHabitFragment_to_dashboardFragment)
        }
    }
}