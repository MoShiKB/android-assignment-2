package com.example.studentsapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.studentsapp.repository.StudentRepository

class StudentDetailsActivity : AppCompatActivity() {

    private lateinit var studentId: String

    private lateinit var avatar: ImageView
    private lateinit var nameTv: TextView
    private lateinit var idTv: TextView
    private lateinit var phoneTv: TextView
    private lateinit var addressTv: TextView
    private lateinit var checkedCb: CheckBox
    private lateinit var editBtn: Button

    private val editLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)

        val toolbar: Toolbar = findViewById(R.id.details_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Student Details"

        studentId = intent.getStringExtra(EXTRA_STUDENT_ID) ?: run {
            Toast.makeText(this, "Missing student id", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        avatar = findViewById(R.id.details_avatar)
        nameTv = findViewById(R.id.details_name)
        idTv = findViewById(R.id.details_id)
        phoneTv = findViewById(R.id.details_phone)
        addressTv = findViewById(R.id.details_address)
        checkedCb = findViewById(R.id.details_checked)
        editBtn = findViewById(R.id.details_btn_edit)

        avatar.setImageResource(R.drawable.student_avatar)

        checkedCb.setOnClickListener {
            val newStatus = checkedCb.isChecked
            StudentRepository.updateCheckStatus(studentId, newStatus)
        }

        editBtn.setOnClickListener {
            val intent = Intent(this, EditStudentActivity::class.java)
            intent.putExtra(EditStudentActivity.EXTRA_ORIGINAL_ID, studentId)
            editLauncher.launch(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadStudent()
    }

    private fun loadStudent() {
        if (isFinishing) return
        val student = StudentRepository.findStudentById(studentId)
        if (student == null) {
            Toast.makeText(this, "Student not found (maybe deleted)", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        nameTv.text = student.fullName
        idTv.text = "ID: ${student.studentId}"
        phoneTv.text = "Phone: ${student.phoneNumber}"
        addressTv.text = "Address: ${student.homeAddress}"
        checkedCb.isChecked = student.checked
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> { finish(); true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val EXTRA_STUDENT_ID = "extra_student_id"
    }
}
