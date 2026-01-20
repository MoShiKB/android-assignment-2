package com.example.studentsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentsapp.adapter.StudentListAdapter
import com.example.studentsapp.model.Student
import com.example.studentsapp.repository.StudentRepository

class StudentListActivity : AppCompatActivity() {

    private lateinit var listAdapter: StudentListAdapter
    private var allStudents: List<Student> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_list)

        initToolbar()
        setupWindowInsets()
        initRecyclerView()
        loadStudentData()
    }

    private fun initToolbar() {
        val toolbar: Toolbar = findViewById(R.id.student_list_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Students"
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.student_list_root)) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }
    }

    private fun initRecyclerView() {
        val recycler: RecyclerView = findViewById(R.id.student_list_recycler)
        recycler.layoutManager = LinearLayoutManager(this)

        listAdapter = StudentListAdapter(allStudents) { }
        recycler.adapter = listAdapter
    }

    private fun loadStudentData() {
        allStudents = StudentRepository.fetchAllStudents()
        listAdapter.refreshData(allStudents)
    }

    override fun onResume() {
        super.onResume()
        loadStudentData()
    }
}
