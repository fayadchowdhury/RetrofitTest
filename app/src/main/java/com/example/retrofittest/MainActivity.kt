package com.example.retrofittest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.retrofittest.databasing.AuthDB
import com.example.retrofittest.databasing.DoctorDB
import com.example.retrofittest.models.Doctor
import com.example.retrofittest.models.Rating

class MainActivity : AppCompatActivity() , DoctorDB.GetDoctorByIdSuccessListener, DoctorDB.GetDoctorByIdFailureListener,
    DoctorDB.GetDoctorsSuccessListener,
    DoctorDB.GetDoctorsFailureListener,
    DoctorDB.GetTopDoctorsSuccessListener,
    DoctorDB.GetTopDoctorsFailureListener,
    DoctorDB.GetTopDoctorsInAllCategoriesSuccessListener,
    DoctorDB.GetTopDoctorsInAllCategoriesFailureListener  /* AuthDB interfaces AuthDB.RegisterDoctorBasicSuccessListener, AuthDB.RegisterDoctorBasicFailureListener, AuthDB.RegisterPatientBasicSuccessListener, AuthDB.RegisterPatientBasicFailureListener, AuthDB.LoginDoctorSuccessListener, AuthDB.LoginDoctorFailureListener, AuthDB.LoginPatientSuccessListener, AuthDB.LoginPatientFailureListener */{

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


        ddb = DoctorDB()

        //Find Doctor By Id
        /*
        ddb.setGetDoctorByIDSuccessListener(this)
        ddb.setGetDoctorByIDFailureListener(this)
        ddb.getDoctorByID("04999760-63aa-41d5-8927-ec8b2ab86a4c")
         */

        //Find Doctors
        ddb.setGetDoctorsSuccessListener(this)
        ddb.setGetDoctorsFailureListener(this)
        //With email and limit
        ddb.getDoctors(1, "hakimrahman@gmail.com")
        //Only with limit
        ddb.getDoctors(1)
        //Only With email
        ddb.getDoctors( "rh@gmail.com")
        //In general
        ddb.getDoctors()
        //Wrong email
        ddb.getDoctors("naafiz@gmail.com")

        //Find Top Doctors in a particular speciality
        ddb.setGetTopDoctorsSuccessListener(this)
        ddb.setGetTopDoctorsFailureListener(this)
        ddb.getTopDoctors("ENT", 2)

        //Find Top Doctors in general
        ddb.setGetTopDoctorsInAllCategoriesSuccessListener(this)
        ddb.setGetTopDoctorsInAllCategoriesFailureListener(this)
        ddb.getTopDoctorsInAllCategories()



    }

    //Find Doctor By Id
    override fun getDoctorByIDSuccess(doctor: Doctor) {
        Log.d("Retro Within Main", "Doctor name: ${doctor.name}")
        tv.text = doctor.name + " " + doctor.email
    }

    override fun getDoctorByIDFailure() {
        Log.d("oopsie", "Failure")
    }

    //Find Doctors
    override fun getDoctorsSuccess(doctor: Doctor) {
        Log.d("Doctors Within Mainnn", "Doctor name: ${doctor.name}")
        tv.text = doctor.name + " " + doctor.email
    }

    override fun getDoctorsFailure(message: String) {
        Log.d("oopsie", message)
    }

    //Find top doctors
    override fun getTopDoctorsSuccess(doctor: Doctor, rating: Rating) {
        Log.d("TopDocs Within Mainnn", "Doctor name: ${doctor.name} Rating: ${rating.average}")
        tv.text = doctor.name + " " + rating.average
    }

    override fun getTopDoctorsFailure(message: String) {
        Log.d("oopsie", message)
    }

    //Find Top Doctors in all Categories
    override fun getTopDoctorsInAllCategoriesSuccess(doctor: Doctor, rating: Rating) {
        Log.d("TopDocs Within Mainnn", "Doctor name: ${doctor.name} Rating: ${rating.average}")
        tv.text = doctor.name + " " + rating.average
    }

    override fun getTopDoctorsInAllCategoriesFailure(message: String) {
        Log.d("oopsie", message)
    }

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