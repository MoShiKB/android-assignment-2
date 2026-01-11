package com.example.studentsapp

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.studentsapp.model.Student
import com.example.studentsapp.repository.StudentRepository

class NewStudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_student)

        val toolbar: Toolbar = findViewById(R.id.new_student_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val inputName: EditText = findViewById(R.id.input_name)
        val inputId: EditText = findViewById(R.id.input_id)
        val inputPhone: EditText = findViewById(R.id.input_phone)
        val inputAddress: EditText = findViewById(R.id.input_address)
        val checkboxStatus: CheckBox = findViewById(R.id.checkbox_status)
        val btnSave: Button = findViewById(R.id.btn_save)
        val btnCancel: Button = findViewById(R.id.btn_cancel)

        btnSave.setOnClickListener {
            val name = inputName.text.toString().trim()
            val id = inputId.text.toString().trim()
            val phone = inputPhone.text.toString().trim()
            val address = inputAddress.text.toString().trim()
            val isChecked = checkboxStatus.isChecked

            if (name.isEmpty() || id.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newStudent = Student(
                studentId = id,
                fullName = name,
                phoneNumber = phone,
                homeAddress = address,
                checked = isChecked
            )

            val success = StudentRepository.insertStudent(newStudent)

            if (success) {
                Toast.makeText(this, "Student added successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Student with this ID already exists", Toast.LENGTH_SHORT).show()
            }
        }

        btnCancel.setOnClickListener {
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
