package com.example.retrofittest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.retrofittest.databasing.AuthDB
import com.example.retrofittest.databasing.DoctorDB

class MainActivity : AppCompatActivity() /* DoctorDB interfaces DoctorDB.GetDoctorByIdSuccessListener, DoctorDB.GetDoctorByIdFailureListener */ /* AuthDB interfaces AuthDB.RegisterDoctorBasicSuccessListener, AuthDB.RegisterDoctorBasicFailureListener, AuthDB.RegisterPatientBasicSuccessListener, AuthDB.RegisterPatientBasicFailureListener, AuthDB.LoginDoctorSuccessListener, AuthDB.LoginDoctorFailureListener, AuthDB.LoginPatientSuccessListener, AuthDB.LoginPatientFailureListener */{

    lateinit var ddb: DoctorDB
    lateinit var adb: AuthDB
    lateinit var tv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv = findViewById(R.id.text)

        /*AuthDB usage
        adb = AuthDB(this)
        adb.setRegisterPatientBasicSuccessListener(this)
        adb.setRegisterPatientBasicFailureListener(this)
        adb.setRegisterDoctorBasicSuccessListener(this)
        adb.setRegisterDoctorBasicFailureListener(this)
        adb.setLoginPatientSuccessListener(this)
        adb.setLoginPatientFailureListener(this)
        adb.setLoginDoctorSuccessListener(this)
        adb.setLoginDoctorFailureListener(this)

        adb.registerPatientBasic("Dr. Zayad Chowdhury", "zayad.chowdhury@gmail.com", "zipto123")
        adb.loginPatient("zayad.chowdhury@gmail.com", "zipto123")*/

        /*DoctorDB usage
        ddb = DoctorDB()
        ddb.setGetDoctorByIDSuccessListener(this)
        ddb.setGetDoctorByIDFailureListener(this)


        ddb.getDoctorByID("04999760-63aa-41d5-8927-ec8b2ab86a4c")*/
    }

    /*DoctorDB usage
    override fun getDoctorByIDSuccess(doctor: Doctor) {
        Log.d("Retro Within Main", "Doctor name: ${doctor.name}")
        tv.text = doctor.name + " " + doctor.email
    }

    override fun getDoctorByIDFailure() {
        Log.d("oopsie", "Failure")
    }*/

    /*AuthDB usage
    override fun registerDoctorBasicSuccess(doctor: Doctor) {
        Log.d("Retro Within Main", "Doctor name: ${doctor.name}")
        tv.text = doctor.name + " " + doctor.email
    }

    override fun registerDoctorBasicFailure() {
        Log.d("oopsie", "Failure")
    }

    override fun registerPatientBasicSuccess(patient: Patient) {
        Log.d("Retro Within Main", "Patient name: ${patient.name}")
        tv.text = patient.name + " " + patient.email
    }

    override fun registerPatientBasicFailure() {
        Log.d("oopsie", "Failure")
    }

    override fun loginDoctorSuccess(doctor: Doctor) {
        Log.d("Retro Within Main", "Doctor name: ${doctor.name}")
        tv.text = doctor.name + " " + doctor.email
    }

    override fun loginDoctorFailure() {
        Log.d("oopsie", "Failure")
    }

    override fun loginPatientSuccess(patient: Patient) {
        Log.d("Retro Within Main", "Patient name: ${patient.name}")
        tv.text = patient.name + " " + patient.email
    }

    override fun loginPatientFailure() {
        Log.d("oopsie", "Failure")
    }*/

}