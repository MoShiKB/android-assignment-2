package com.example.studentsapp.repository

import com.example.studentsapp.model.Student

object StudentRepository {

    private val studentsList: MutableList<Student> = mutableListOf()

    init {
        addSampleStudents()
    }

    private fun addSampleStudents() {
        studentsList.addAll(listOf(
            Student("101", "Israel Israeli", "0521111111", "Holon", false),
            Student("102", "John Doe", "0501231231", "Rishon Lezion", true),
            Student("103", "Jane Smith", "0525555555", "Tel Aviv", false),
        ))
    }

    fun fetchAllStudents(): List<Student> {
        return studentsList.toList()
    }

    fun findStudentById(id: String): Student? {
        return studentsList.find { it.studentId == id }
    }

    fun insertStudent(student: Student): Boolean {
        val exists = studentsList.any { it.studentId == student.studentId }
        if (exists) {
            return false
        }
        studentsList.add(student)
        return true
    }

    fun modifyStudent(originalId: String, updatedStudent: Student): Boolean {
        val index = studentsList.indexOfFirst { it.studentId == originalId }
        if (index == -1) {
            return false
        }
        studentsList.removeAt(index)
        studentsList.add(index, updatedStudent)
        return true
    }

    fun removeStudent(id: String): Boolean {
        return studentsList.removeIf { it.studentId == id }
    }

    fun updateCheckStatus(id: String, isChecked: Boolean): Boolean {
        val student = studentsList.find { it.studentId == id }
        student?.let {
            it.checked = isChecked
            return true
        }
        return false
    }

    fun clearAllStudents() {
        studentsList.clear()
    }
}

