package com.example.habittracker.view

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.habittracker.R
import com.example.habittracker.viewmodel.HabitViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class EditHabitFragment : Fragment(R.layout.fragment_edit_habit) {

    private val viewModel: HabitViewModel by activityViewModels()

    private val args: EditHabitFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val habitId = args.habitId
        val etName = view.findViewById<TextInputEditText>(R.id.etHabitName)
        val etDescription = view.findViewById<TextInputEditText>(R.id.etDescription)
        val etGoal = view.findViewById<TextInputEditText>(R.id.etGoal)
        val etUnit = view.findViewById<TextInputEditText>(R.id.etUnit)
        val actvIcon = view.findViewById<AutoCompleteTextView>(R.id.actvSelectIcon)
        val iconList = arrayOf(
            "Water",
            "Walking",
            "Praying",
            "Sleeping",
            "Eating",
            "Exercise",
            "Reading"
        )

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            iconList
        )

        actvIcon.setAdapter(adapter)
        val btnUpdate = view.findViewById<MaterialButton>(R.id.btnCreateHabit)

        viewModel.getHabitById(habitId)
            .observe(viewLifecycleOwner) { habit ->

                etName.setText(habit.name)
                etDescription.setText(habit.description)
                etGoal.setText(habit.goal.toString())
                etUnit.setText(habit.unit)

                actvIcon.setText(
                    getIconName(habit.icon),
                    false
                )

                btnUpdate.setOnClickListener {
                    val updatedHabit = habit.copy(
                        name = etName.text.toString(),
                        description = etDescription.text.toString(),
                        goal = etGoal.text.toString().toIntOrNull() ?: 0,
                        unit = etUnit.text.toString(),
                        icon = getIconResource(actvIcon.text.toString())
                    )
                    viewModel.updateHabit(updatedHabit)
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
            }
    }
}
private fun getIconName(icon: Int): String {
    return when (icon) {
        R.drawable.water_drop -> "Water"
        R.drawable.walking -> "Walking"
        R.drawable.pray -> "Praying"
        R.drawable.sleep -> "Sleeping"
        R.drawable.food -> "Eating"
        R.drawable.exercise -> "Exercise"
        R.drawable.book -> "Reading"
        else -> "Water"
    }
}
private fun getIconResource(name: String): Int {
    return when (name) {
        "Water" -> R.drawable.water_drop
        "Walking" -> R.drawable.walking
        "Praying" -> R.drawable.pray
        "Sleeping" -> R.drawable.sleep
        "Eating" -> R.drawable.food
        "Exercise" -> R.drawable.exercise
        "Reading" -> R.drawable.book
        else -> R.drawable.water_drop
    }
}