package com.example.retrofittest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.retrofittest.databasing.DoctorDB
import com.example.retrofittest.models.Doctor

class MainActivity : AppCompatActivity(), DoctorDB.GetDoctorByIdSuccessListener, DoctorDB.GetDoctorByIdFailureListener {

    lateinit var ddb: DoctorDB
    lateinit var tv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv = findViewById(R.id.text)

        ddb = DoctorDB()
        ddb.setGetDoctorByIDSuccessListener(this)
        ddb.setGetDoctorByIDFailureListener(this)


        ddb.getDoctorByID("04999760-63aa-41d5-8927-ec8b2ab86a4c")
    }

    override fun getDoctorByIDSuccess(doctor: Doctor) {
        Log.d("Retro Within Main", "Doctor name: ${doctor.name}")
        tv.text = doctor.name + " " + doctor.email
    }

    override fun getDoctorByIDFailure() {
        Log.d("oopsie", "Failure")
    }
}