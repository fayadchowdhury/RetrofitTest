package com.example.retrofittest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.retrofittest.databasing.*
import com.example.retrofittest.models.*


class MainActivity : AppCompatActivity() , DoctorDB.GetDoctorByIdSuccessListener, DoctorDB.GetDoctorByIdFailureListener,
    DoctorDB.GetDoctorsSuccessListener,
    DoctorDB.GetDoctorsFailureListener,
    DoctorDB.GetTopDoctorsSuccessListener,
    DoctorDB.GetTopDoctorsFailureListener,
    AuthDB.RegisterDoctorBasicSuccessListener, AuthDB.RegisterDoctorBasicFailureListener, AuthDB.RegisterPatientBasicSuccessListener, AuthDB.RegisterPatientBasicFailureListener, AuthDB.LoginDoctorSuccessListener, AuthDB.LoginDoctorFailureListener, AuthDB.LoginPatientSuccessListener, AuthDB.LoginPatientFailureListener,
    DoctorDB.UpdateDoctorProfileSuccessListener,
    DoctorDB.UpdateDoctorProfileFailureListener,
    DoctorDB.DeleteDoctorProfileSuccessListener,
    DoctorDB.DeleteDoctorProfileFailureListener,
    SlotDB.createSlotSuccessListener,
    SlotDB.createSlotFailureListener,
    SlotDB.deleteDoctorSlotsSuccessListener,
    SlotDB.deleteDoctorSlotsFailureListener,
    /* PatientDB interfaces */
    PatientDB.GetPatientByIdSuccessListener,
    PatientDB.GetPatientByIdFailureListener,
    PatientDB.UpdatePatientProfileSuccessListener,
    PatientDB.UpdatePatientProfileFailureListener,
    PatientDB.DeletePatientByIdSuccessListener,
    PatientDB.DeletePatientByIdFailureListener,
    /* RatingDB interfaces */
    RatingDB.GetRatingsByIdSuccessListener,
    RatingDB.GetRatingsByIdFailureListener
{

    lateinit var ddb: DoctorDB
    lateinit var adb: AuthDB
    lateinit var sdb: SlotDB
    lateinit var rdb: RatingDB
    lateinit var pdb: PatientDB
    lateinit var tv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv = findViewById(R.id.text)

        adb = AuthDB(this)
        adb.setRegisterPatientBasicSuccessListener(this)
        adb.setRegisterPatientBasicFailureListener(this)
        adb.setRegisterDoctorBasicSuccessListener(this)
        adb.setRegisterDoctorBasicFailureListener(this)
        adb.setLoginPatientSuccessListener(this)
        adb.setLoginPatientFailureListener(this)
        adb.setLoginDoctorSuccessListener(this)
        adb.setLoginDoctorFailureListener(this)

      //adb.registerDoctorBasic("Dr. Zayada Chowdhury", "zayada.chowdhury@gmail.com", "zipto123", "768324")
       //adb.loginDoctor("zayada.chowdhury@gmail.com", "zipto123")


        ddb = DoctorDB(this)
        ddb.setUpdateDoctorProfileSuccessListener(this)
        ddb.setUpdateDoctorProfileFailureListener(this)


        ddb.setDeleteDoctorProfileSuccessListener(this)
        ddb.setDeleteDoctorProfileFailureListener(this)
//        /*****Find Doctor By Id******/
        ddb.setGetDoctorByIDSuccessListener(this)
        ddb.setGetDoctorByIDFailureListener(this)
        //ddb.getDoctorByID("04999760-63aa-41d5-8927-ec8b2ab86a4c")
//
//        /********Find Doctors*******/
        ddb.setGetDoctorsSuccessListener(this)
        ddb.setGetDoctorsFailureListener(this)
//        //With email and limit
//        ddb.getDoctors(1, "hakimrahman@gmail.com")
//        //Only with limit
//        ddb.getDoctors(1)
//        //Only With email
//        ddb.getDoctors( "rh@gmail.com")
//        //In general
//        ddb.getDoctors()
//        //Wrong email
//        ddb.getDoctors("naafiz@gmail.com")
//
//        /****Find Top Doctors in a particular speciality*****/
        ddb.setGetTopDoctorsSuccessListener(this)
        ddb.setGetTopDoctorsFailureListener(this)
//        ddb.getTopDoctors("ENT", 2)
//
//        /****Find Top Doctors in general****/
//        ddb.setGetTopDoctorsInAllCategoriesSuccessListener(this)
//        ddb.setGetTopDoctorsInAllCategoriesFailureListener(this)
//        //Without limit
//        ddb.getTopDoctorsInAllCategories()
//        //With Limit
//        ddb.getTopDoctorsInAllCategories(1)

//        val updMap = mutableMapOf<String, String>()
//        updMap.put("name", "CHOMKYBASTARDO")
//        updMap.put("bmdc", "6996420")
//        ddb.updateDoctorProfile(updMap)


//        val queryMap = mutableMapOf<String, String>()
//        queryMap.put("name", "CHOMKYBASTARDO")
//        queryMap.put("specialty", "Cardiology")
//        queryMap.put("limit", "2")
//        ddb.getDoctors(queryMap)
//        ddb.getTopDoctors(queryMap)

//        ddb.deleteDoctorById()

        sdb = SlotDB(this)
        sdb.setCreateSlotFailureListener(this)
        sdb.setCreateSlotSuccessListener(this)
        sdb.setDeleteDoctorSlotsSuccessListener(this)
        sdb.setDeleteDoctorSlotsFailureListener(this)

//        sdb.createSlot("2020-09-24", "10:00", "15:00", 10, 0)
        sdb.deleteSlotsByDoctorId()


        // ***     PatientDB usage      ***//

        // Get Patient by ID
        pdb = PatientDB(this)
        pdb.setGetPatientByIdSuccessListener(this)
        pdb.setGetPatientByIdFailureListener(this)
        //Test
        pdb.getPatientById("2af1a743-4411-4311-80fc-64bef5373bea")

        // Update Patient Profile
        pdb.setUpdatePatientProfileSuccessListener(this)
        pdb.setUpdatePatientProfileFailureListener(this)
        //Test
        val updMap = mutableMapOf<String, String>()
        updMap.put("name", "Kokila ben")
        updMap.put("email", "kokilaben@rocketmail.com")
        updMap.put("phone", "+8801769696969")
        //updMap.put("dob", "")
        updMap.put("gender", "female")
        updMap.put("blood", "O-")
        updMap.put("address", "cooker")
        pdb.updatePatientProfile(updMap)

        //Delete Patient by Id
        pdb.setDeletePatientByIdSuccessListener(this)
        pdb.setDeletePatientByIdFailureListener(this)
        //Test
        pdb.deletePatientById()

        //RATING DB
        rdb = RatingDB(this)
        //rdb.setGetRatingsByIDSuccessListener(this)
        //rdb.setGetRatingsByIDFailureListener(this)
        //rdb.getRatingsById("c27e6999-cdd6-4569-9b89-6118b78d2db4")
    }

    //Find Doctor By Id
    override fun getDoctorByIDSuccess(doctor: Doctor) {
        Log.d("Doc by id", "Doctor name: ${doctor.name}")
        tv.text = doctor.name + " " + doctor.email
    }

    override fun getDoctorByIDFailure() {
        Log.d("oopsie by id", "Failure")
    }

    //Find Doctors
    override fun getDoctorsSuccess(doctorArray: ArrayList<Doctor>) {
        for ( doc in doctorArray )
        {
            Log.d("getDoctorsSuccess", "Name ${doc.name}")
        }
    }

    override fun getDoctorsFailure(message: String) {
        Log.d("oopsie docs general", message)
    }

    //Find top doctors
    override fun getTopDoctorsSuccess(ratedDoctorArray: ArrayList<RatedDoctor>) {
        for ( doc in ratedDoctorArray )
        {
            Log.d("getDoctorsSuccess", "Name ${doc.doctor.name}")
        }
    }

    override fun getTopDoctorsFailure(message: String) {
        Log.d("oopsie top docs", message)
    }

    override fun updateDoctorProfileSuccess() {
        Log.d("UpdateProfile", "BHAI JITSEN")
    }

    override fun updateDoctorProfileFailure() {
        Log.d("UpdateProfile", "BHAI HOLO NA TO :(")
    }

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
    }

    override fun deleteDoctorProfileSuccess() {
        Log.d("DELETEMAIN", "Success")
    }

    override fun deleteDoctorProfileFailure() {
        Log.d("DELETEMAIN", "Failure")
    }


    override fun createSlotSuccess(slotArray: ArrayList<Slot>) {
        for ( slot in slotArray )
        {
            Log.d("SLOTSUCCESS", "slot ID = ${slot.id} starting at ${slot.startTime} and ending at ${slot.endTime} on ${slot.dateOfSlot}")
        }
    }

    override fun createSlotFailure() {
        Log.d("SLOTFAILURE", "Failure to create slots")
    }

    override fun deleteDoctorSlotsSuccess() {
        Log.d("SLOTDEL", "Slots deleted successfully")
    }

    override fun deleteDoctorSlotsFailure() {
        Log.d("SLOTDEL", "Failed to delete slots")
    }



    //Find Patient By Id

    override fun getPatientByIdSuccess(patient: Patient) {
        Log.d("Patient by id", "Patient name: ${patient.name}")
        tv.text = patient.name + " " + patient.email
    }

    override fun getPatientByIdFailure() {
        Log.d("oopsie by id", "Failure")
    }
    //Update Patient Profile

    override fun updatePatientProfileSuccess() {
        Log.d("UpdateProfile", "BHAI JITSEN")
    }

    override fun updatePatientProfileFailure() {
        Log.d("UpdateProfile", "BHAI HOLO NA TO :(")
    }

    // Delete Patient by Id

    override fun deletePatientByIdSuccess() {
        Log.d("DELETEMAIN", "Success")
    }

    override fun deletePatientByIdFailure() {
        Log.d("DELETEMAIN", "Failure")
    }

    //Rating Routes

    //Rating by Id
    override fun getRatingsByIDSuccess(rating: Rating) {
        Log.d("Rating by ID Success", rating.average)
    }

    override fun getRatingsByIDFailure() {
        Log.d("Rating by ID Failure","Failure")
    }

}