package com.example.studentsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentsapp.R
import com.example.studentsapp.model.Student
import com.example.studentsapp.repository.StudentRepository

class StudentListAdapter(
    private var studentData: List<Student>,
    private val onRowClick: (Student) -> Unit
) : RecyclerView.Adapter<StudentListAdapter.StudentItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentItemHolder {
        val rowView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student_row, parent, false)
        return StudentItemHolder(rowView)
    }

    override fun onBindViewHolder(holder: StudentItemHolder, position: Int) {
        val currentStudent = studentData[position]
        holder.bindData(currentStudent)

        holder.itemView.setOnClickListener {
            onRowClick(currentStudent)
        }

        holder.checkBox.setOnClickListener {
            val newStatus = holder.checkBox.isChecked
            currentStudent.checked = newStatus
            StudentRepository.updateCheckStatus(currentStudent.studentId, newStatus)
        }
    }

    override fun getItemCount(): Int = studentData.size

    fun refreshData(newData: List<Student>) {
        studentData = newData
        notifyDataSetChanged()
    }

    class StudentItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val avatarImage: ImageView = itemView.findViewById(R.id.item_student_avatar)
        private val nameLabel: TextView = itemView.findViewById(R.id.item_student_name)
        private val idLabel: TextView = itemView.findViewById(R.id.item_student_id)
        val checkBox: CheckBox = itemView.findViewById(R.id.item_student_checkbox)

        fun bindData(student: Student) {
            nameLabel.text = student.fullName
            idLabel.text = student.studentId
            checkBox.isChecked = student.checked
            avatarImage.setImageResource(R.drawable.student_avatar)
        }
    }
}

