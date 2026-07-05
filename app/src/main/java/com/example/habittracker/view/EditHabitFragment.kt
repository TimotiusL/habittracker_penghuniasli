package com.example.habittracker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentEditHabitBinding
import com.example.habittracker.viewmodel.EditHabitFormViewModel
import com.example.habittracker.viewmodel.HabitViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class EditHabitFragment : Fragment() {
    private lateinit var binding: FragmentEditHabitBinding

    private val formViewModel: EditHabitFormViewModel by viewModels()
    private val viewModel: HabitViewModel by activityViewModels()

    private val args: EditHabitFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_edit_habit,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.form = formViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val habitId = args.habitId
        val actvIcon = binding.actvSelectIcon
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

                formViewModel.habitName.value = habit.name
                formViewModel.description.value = habit.description
                formViewModel.goal.value = habit.goal.toString()
                formViewModel.unit.value = habit.unit

                actvIcon.setText(
                    getIconName(habit.icon),
                    false
                )

                btnUpdate.setOnClickListener {
                    val updatedHabit = habit.copy(
                        name = formViewModel.habitName.value ?: "",
                        description = formViewModel.description.value ?: "",
                        goal = formViewModel.goal.value?.toIntOrNull() ?: 0,
                        unit = formViewModel.unit.value ?: "",
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