package com.example.habittracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.R
import com.example.habittracker.model.Habit

class HabitAdapter(
    private val habitList: ArrayList<Habit>,
    private val onIncrementClick: (Habit, Int) -> Unit,
    private val onDecrementClick: (Habit, Int) -> Unit
) : RecyclerView.Adapter<HabitAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivIcon: ImageView = view.findViewById(R.id.ivIcon)
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
        val tvProgress: TextView = view.findViewById(R.id.tvProgress)
        val tvStatus: TextView = view.findViewById(R.id.tvStatus)
        val btnMinus: Button = view.findViewById(R.id.btnMinus)
        val btnPlus: Button = view.findViewById(R.id.btnPlus)
        val tvUnit: TextView = view.findViewById(R.id.tvUnit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.habit_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = habitList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val habit = habitList[position]

        val vStripCompleted = holder.itemView.findViewById<View>(R.id.vStripCompleted)

        holder.apply {
            tvName.text = habit.name
            tvDescription.text = habit.description
            tvUnit.text = habit.unit
            ivIcon.setImageResource(habit.icon)

            progressBar.max = habit.goal
            progressBar.progress = habit.progress
            tvProgress.text = "${habit.progress} / ${habit.goal} ${habit.unit}"

            val isCompleted = habit.progress >= habit.goal
            if (isCompleted) {
                tvStatus.text = "Completed"
                tvStatus.setBackgroundColor(android.graphics.Color.parseColor("#4CAF50"))
                btnPlus.isEnabled = false
                btnMinus.isEnabled = false
                vStripCompleted.visibility = View.VISIBLE
            } else {
                tvStatus.text = "In Progress"
                tvStatus.setBackgroundColor(android.graphics.Color.parseColor("#FF9800"))
                btnPlus.isEnabled = true
                btnMinus.isEnabled = habit.progress > 0
                vStripCompleted.visibility = View.GONE
            }

            btnPlus.setOnClickListener {
                if (habit.progress < habit.goal) {
                    onIncrementClick(habit, position)
                }
            }

            btnMinus.setOnClickListener {
                if (habit.progress > 0) {
                    onDecrementClick(habit, position)
                }
            }
        }
    }

    fun updateList(newList: List<Habit>) {
        habitList.clear()
        habitList.addAll(newList)
        notifyDataSetChanged()
    }
}