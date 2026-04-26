package com.example.habittracker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.R
import com.example.habittracker.adapter.HabitAdapter
import com.example.habittracker.model.Habit
import com.example.habittracker.viewmodel.HabitViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DashboardFragment : Fragment() {

    private val habitViewModel: HabitViewModel by activityViewModels()
    private lateinit var adapter: HabitAdapter
    private lateinit var rvHabits: RecyclerView
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var tvEmptyMessage: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvHabits = view.findViewById(R.id.rvHabits)
        fabAdd = view.findViewById(R.id.fabAdd)
        tvEmptyMessage = view.findViewById(R.id.tvEmptyMessage)

        setupRecyclerView()
        observeData()

        fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_addHabitFragment)
        }
    }

    private fun setupRecyclerView() {
        val emptyList = ArrayList<Habit>()
        adapter = HabitAdapter(
            habitList = emptyList,
            onIncrementClick = { habit, position ->
                val newProgress = habit.progress + 1
                if (newProgress <= habit.goal) {
                    habitViewModel.updateProgress(habit, newProgress)
                }
            },
            onDecrementClick = { habit, position ->
                val newProgress = habit.progress - 1
                if (newProgress >= 0) {
                    habitViewModel.updateProgress(habit, newProgress)
                }
            }
        )
        rvHabits.layoutManager = LinearLayoutManager(requireContext())
        rvHabits.adapter = adapter
    }

    private fun observeData() {
        habitViewModel.habitList.observe(viewLifecycleOwner) { habits ->
            if (habits.isNotEmpty()) {
                adapter.updateList(habits)
                rvHabits.visibility = View.VISIBLE
                tvEmptyMessage.visibility = View.GONE
            } else {
                rvHabits.visibility = View.GONE
                tvEmptyMessage.visibility = View.VISIBLE
            }
        }
    }
}