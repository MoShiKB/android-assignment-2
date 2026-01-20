package com.example.studentsapp.model

data class Student(
    var studentId: String,
    var fullName: String,
    var phoneNumber: String,
    var homeAddress: String,
    var checked: Boolean = false,
    val profileImage: String = ""
)

