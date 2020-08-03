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


        ddb.getDoctorByID("ec735618-0935-4bc7-b83d-edaf2972bc64")
    }

    override fun getDoctorByIDSuccess(doctor: Doctor) {
        tv.text = doctor.name + " " + doctor.email
    }

    override fun getDoctorByIDFailure() {
        Log.d("oopsie", "Failure")
    }
}