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

class EditStudentActivity : AppCompatActivity() {

    private lateinit var originalId: String

    private lateinit var inputName: EditText
    private lateinit var inputId: EditText
    private lateinit var inputPhone: EditText
    private lateinit var inputAddress: EditText
    private lateinit var checkboxStatus: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        val toolbar: Toolbar = findViewById(R.id.edit_student_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Edit Student"

        originalId = intent.getStringExtra(EXTRA_ORIGINAL_ID) ?: run {
            Toast.makeText(this, "Missing student id", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        inputName = findViewById(R.id.edit_input_name)
        inputId = findViewById(R.id.edit_input_id)
        inputPhone = findViewById(R.id.edit_input_phone)
        inputAddress = findViewById(R.id.edit_input_address)
        checkboxStatus = findViewById(R.id.edit_checkbox_status)

        val btnCancel: Button = findViewById(R.id.edit_btn_cancel)
        val btnDelete: Button = findViewById(R.id.edit_btn_delete)
        val btnSave: Button = findViewById(R.id.edit_btn_save)

        loadStudentToForm()

        btnCancel.setOnClickListener { finish() }

        btnDelete.setOnClickListener {
            val removed = StudentRepository.removeStudent(originalId)
            if (removed) {
                Toast.makeText(this, "Student deleted", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show()
            }
        }

        btnSave.setOnClickListener {
            val name = inputName.text.toString().trim()
            val newId = inputId.text.toString().trim()
            val phone = inputPhone.text.toString().trim()
            val address = inputAddress.text.toString().trim()
            val isChecked = checkboxStatus.isChecked

            if (name.isEmpty() || newId.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updated = Student(
                studentId = newId,
                fullName = name,
                phoneNumber = phone,
                homeAddress = address,
                checked = isChecked
            )

            val success = StudentRepository.modifyStudent(originalId, updated)
            if (success) {
                Toast.makeText(this, "Student updated", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Update failed (maybe ID already exists?)", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadStudentToForm() {
        val student = StudentRepository.findStudentById(originalId)
        if (student == null) {
            Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        inputName.setText(student.fullName)
        inputId.setText(student.studentId)
        inputPhone.setText(student.phoneNumber)
        inputAddress.setText(student.homeAddress)
        checkboxStatus.isChecked = student.checked
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> { finish(); true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val EXTRA_ORIGINAL_ID = "extra_original_id"
    }
}
