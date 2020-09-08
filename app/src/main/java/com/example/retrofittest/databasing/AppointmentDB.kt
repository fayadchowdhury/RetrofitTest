package com.example.retrofittest.databasing

import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager
import com.example.retrofittest.MainActivity
import com.example.retrofittest.models.Appointment
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppointmentDB(val context: Context) {

    //View appointment interfaces
    lateinit var mViewAppointmentByIdSuccessListener: ViewAppointmentByIdSuccessListener
    lateinit var mViewAppointmentByIdFailureListener: ViewAppointmentByIdFailureListener

    //Create appointment interfaces
    lateinit var mCreateAppointmentSuccessListener: CreateAppointmentSuccessListener
    lateinit var mCreateAppointmentFailureListener: CreateAppointmentFailureListener

    //Upcoming appointment for patients interfaces
    lateinit var mViewUpcomingAppointmentsPatientSuccessListener: ViewUpcomingAppointmentsPatientSuccessListener
    lateinit var mViewUpcomingAppointmentsPatientFailureListener: ViewUpcomingAppointmentsPatientFailureListener

    //Upcoming Appointment for doctors interfaces
    lateinit var mViewUpcomingAppointmentsDoctorSuccessListener: ViewUpcomingAppointmentsDoctorSuccessListener
    lateinit var mViewUpcomingAppointmentsDoctorFailureListener: ViewUpcomingAppointmentsDoctorFailureListener

    //Past Appointment for patients interfaces
    lateinit var mViewPastAppointmentsPatientSuccessListener: ViewPastAppointmentsPatientSuccessListener
    lateinit var mViewPastAppointmentsPatientFailureListener: ViewPastAppointmentsPatientFailureListener

    //Past Appointment for doctors interfaces
    lateinit var mViewPastAppointmentsDoctorSuccessListener: ViewPastAppointmentsDoctorSuccessListener
    lateinit var mViewPastAppointmentsDoctorFailureListener: ViewPastAppointmentsDoctorFailureListener

    //Complete Appointment Interfaces
    lateinit var mCompleteAppointmentSuccessListener: CompleteAppointmentSuccessListener
    lateinit var mCompleteAppointmentFailureListener: CompleteAppointmentFailureListener

    //Delete Appointment By Id interfaces
    lateinit var mDeleteAppointmentByIdSuccessListener: DeleteAppointmentByIdSuccessListener
    lateinit var mDeleteAppointmentByIdFailureListener: DeleteAppointmentByIdFailureListener

    //Update Prescription for doctors interfaces
    lateinit var mUpdatePrescriptionSuccessListener: UpdatePrescriptionSuccessListener
    lateinit var mUpdatePrescriptionFailureListener: UpdatePrescriptionFailureListener


    fun createAppointment(slotId: String)
    {
        val sh = PreferenceManager.getDefaultSharedPreferences(context)
        val jwt = sh.getString("jwt", "NONE FOUND").toString()
        val uid = sh.getString("uid", "NONE FOUND").toString()
        if ( jwt == "NONE FOUND" || uid == "NONE FOUND" )
        {
            //don't go any further
            Log.d("CREATESAPP", "$jwt $uid")
            mCreateAppointmentFailureListener.createAppointmentFailure()
        }
        else
        {
            val paramsJSON = JSONObject()
            paramsJSON.put("patientId", uid)
            paramsJSON.put("slotId", slotId)

            val params = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), paramsJSON.toString())

            val headerJwt = "Bearer $jwt"

            val call = APIObject.api.createAppointment(headerJwt, params)

            call.enqueue(object: Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    mCreateAppointmentFailureListener.createAppointmentFailure()
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if ( response.isSuccessful )
                    {
                        val jsonRes = JSONObject(response.body()!!.string())
                        val appRet = Appointment().fromJSON(jsonRes.getJSONObject("appRet"))
                        mCreateAppointmentSuccessListener.createAppointmentSuccess(appRet)
                    }
                    else
                        mCreateAppointmentFailureListener.createAppointmentFailure()
                }
            })
        }
    }

    fun viewAppointmentById(appId: String)
    {
        val paramsJSON = JSONObject()
        paramsJSON.put("appId", appId)
        val params = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), paramsJSON.toString())

        val call = APIObject.api.viewAppointmentById(params)

        call.enqueue(object: Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                mViewAppointmentByIdFailureListener.viewAppointmentByIdFailure()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if ( response.isSuccessful )
                {
                    val jsonRes = JSONObject(response.body()!!.string())
                    val appRet = Appointment().fromJSON(jsonRes.getJSONObject("appRet"))
                    mViewAppointmentByIdSuccessListener.viewAppointmentByIdSuccess(appRet)
                }
                else
                    mViewAppointmentByIdFailureListener.viewAppointmentByIdFailure()
            }
        })
    }

    fun viewUpcomingAppointmentsPatient(patientId : String){
        val paramsJSON = JSONObject()
        paramsJSON.put("patientId", patientId)
        val params = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), paramsJSON.toString())

        val call = APIObject.api.viewUpcomingAppointmentsPatient(params)

        call.enqueue(object: Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                mViewUpcomingAppointmentsPatientFailureListener.viewUpcomingAppointmentsPatientFailure()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if ( response.isSuccessful )
                {
                    val jsonRes = JSONObject(response.body()!!.string())
                    val appointments = makeAppointmentArrayListFromJsonArray(jsonRes.getJSONArray("appRet"))
                    mViewUpcomingAppointmentsPatientSuccessListener.viewUpcomingAppointmentsPatientSuccess(appointments)
                }
                else
                    mViewUpcomingAppointmentsPatientFailureListener.viewUpcomingAppointmentsPatientFailure()
            }
        })
    }

    fun viewUpcomingAppointmentsDoctor(doctorId : String){
        val paramsJSON = JSONObject()
        paramsJSON.put("doctorId", doctorId)
        val params = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), paramsJSON.toString())

        val call = APIObject.api.viewUpcomingAppointmentsDoctor(params)

        call.enqueue(object: Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                mViewUpcomingAppointmentsDoctorFailureListener.viewUpcomingAppointmentsDoctorFailure()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if ( response.isSuccessful )
                {
                    val jsonRes = JSONObject(response.body()!!.string())
                    val appointments = makeAppointmentArrayListFromJsonArray(jsonRes.getJSONArray("appRet"))
                    mViewUpcomingAppointmentsDoctorSuccessListener.viewUpcomingAppointmentsDoctorSuccess(appointments)
                }
                else
                    mViewUpcomingAppointmentsDoctorFailureListener.viewUpcomingAppointmentsDoctorFailure()
            }
        })
    }


    fun viewPastAppointmentsPatient(updOpts: Map<String, String>){
        val sh = PreferenceManager.getDefaultSharedPreferences(context)
        val jwt = sh.getString("jwt", "NONE FOUND").toString()
        val uid = sh.getString("uid", "NONE FOUND").toString()

        if ( jwt == "NONE FOUND" || uid == "NONE FOUND" )
        {
            //don't go any further
            mViewPastAppointmentsPatientFailureListener.viewPastAppointmentsPatientFailure()
        }
        else
        {
            val paramsJSON = JSONObject()
            paramsJSON.put("patientId", updOpts["patientId"].toString())

            val params = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), paramsJSON.toString())

            val headerJwt = "Bearer $jwt"
            val call = APIObject.api.viewPastAppointmentsPatient(headerJwt, params)

            call.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                    mViewPastAppointmentsPatientFailureListener.viewPastAppointmentsPatientFailure()
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        val jsonRes = JSONObject(response.body()!!.string())
                        val appointments = makeAppointmentArrayListFromJsonArray(jsonRes.getJSONArray("appsRet"))
                        mViewPastAppointmentsPatientSuccessListener.viewPastAppointmentsPatientSuccess(appointments)
                    } else {
                        mViewPastAppointmentsPatientFailureListener.viewPastAppointmentsPatientFailure()
                    }
                }
            })
        }
    }

    fun viewPastAppointmentsDoctor(updOpts: Map<String, String>){
        val sh = PreferenceManager.getDefaultSharedPreferences(context)
        val jwt = sh.getString("jwt", "NONE FOUND").toString()
        val uid = sh.getString("uid", "NONE FOUND").toString()

        if ( jwt == "NONE FOUND" || uid == "NONE FOUND" )
        {
            //don't go any further
            mViewPastAppointmentsDoctorFailureListener.viewPastAppointmentsDoctorFailure()
        }
        else
        {
            val paramsJSON = JSONObject()
            paramsJSON.put("doctorId", updOpts["doctorId"].toString())

            val params = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), paramsJSON.toString())

            val headerJwt = "Bearer $jwt"
            val call = APIObject.api.viewPastAppointmentsDoctor(headerJwt, params)

            call.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                    mViewPastAppointmentsDoctorFailureListener.viewPastAppointmentsDoctorFailure()
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        val jsonRes = JSONObject(response.body()!!.string())
                        val appointments = makeAppointmentArrayListFromJsonArray(jsonRes.getJSONArray("appsRet"))
                        mViewPastAppointmentsDoctorSuccessListener.viewPastAppointmentsDoctorSuccess(appointments)
                    } else {
                        mViewPastAppointmentsDoctorFailureListener.viewPastAppointmentsDoctorFailure()
                    }
                }
            })
        }
    }

    //Complete Appointment
    fun completeAppointment(appointmentId: String){
        val sh = PreferenceManager.getDefaultSharedPreferences(context)
        val jwt = sh.getString("jwt", "NONE FOUND").toString()
        val uid = sh.getString("uid", "NONE FOUND").toString()

        if ( jwt == "NONE FOUND" || uid == "NONE FOUND" )
        {
            //don't go any further
            mCompleteAppointmentFailureListener.completeAppointmentFailure()
        }
        else
        {
            val paramsJSON = JSONObject()
            paramsJSON.put("appId", appointmentId)

            val params = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), paramsJSON.toString())

            val headerJwt = "Bearer $jwt"
            val call = APIObject.api.completeAppointment(headerJwt, params)

            call.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    mCompleteAppointmentFailureListener.completeAppointmentFailure()
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        mCompleteAppointmentSuccessListener.completeAppointmentSuccess()
                    } else {
                        mCompleteAppointmentFailureListener.completeAppointmentFailure()
                    }
                }
            })
        }
    }

    //Delete Appointment By Id
    fun deleteAppointmentById(appointmentId: String){
        val sh = PreferenceManager.getDefaultSharedPreferences(context)
        val jwt = sh.getString("jwt", "NONE FOUND").toString()
        val uid = sh.getString("uid", "NONE FOUND").toString()

        if ( jwt == "NONE FOUND" || uid == "NONE FOUND" )
        {
            //don't go any further
            mDeleteAppointmentByIdFailureListener.deleteAppointmentByIdFailure()
        }
        else
        {
            val paramsJSON = JSONObject()
            paramsJSON.put("appId", appointmentId)

            val params = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), paramsJSON.toString())

            val headerJwt = "Bearer $jwt"
            val call = APIObject.api.deleteAppointmentById(headerJwt, params)

            call.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    mDeleteAppointmentByIdFailureListener.deleteAppointmentByIdFailure()
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        mDeleteAppointmentByIdSuccessListener.deleteAppointmentByIdSuccess()
                    } else {
                        mDeleteAppointmentByIdFailureListener.deleteAppointmentByIdFailure()
                    }
                }
            })
        }
    }


    //An utility function to return a list of appointments on the basis of jsonArray
    fun makeAppointmentArrayListFromJsonArray(jsonArray: JSONArray): ArrayList<Appointment> {
        val appointments = ArrayList<Appointment>()
        val countOfAppointment = jsonArray.length()
        for(i in 0 until countOfAppointment){
            appointments.add(Appointment().fromJSON(jsonArray.getJSONObject(i)))
        }
        return appointments
    }

    //Update Prescription

    fun updatePrescription(appId: String, prescription: String)
    {
        val sh = PreferenceManager.getDefaultSharedPreferences(context)
        val jwt = sh.getString("jwt", "NONE FOUND").toString()
        val uid = sh.getString("uid", "NONE FOUND").toString()
        if ( jwt == "NONE FOUND" || uid == "NONE FOUND" )
        {
            //don't go any further
            mUpdatePrescriptionFailureListener.updatePrescriptionFailure()
        }
        else
        {
            val paramsJSON = JSONObject()
            paramsJSON.put("id", uid)
            paramsJSON.put("appId", appId)
            paramsJSON.put("prescription", prescription)

            val params = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), paramsJSON.toString())

            val headerJwt = "Bearer $jwt"
            val call = APIObject.api.updatePrescription(headerJwt, params)

            call.enqueue(object: Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    mUpdatePrescriptionFailureListener.updatePrescriptionFailure()
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if ( response.isSuccessful )
                    {
                        mUpdatePrescriptionSuccessListener.updatePrescriptionSuccess()
                    }
                    else
                    {
                        mUpdatePrescriptionFailureListener.updatePrescriptionFailure()
                    }
                }
            })
        }
    }





    /***Interfaces***/
    //view appointment interfaces
    interface ViewAppointmentByIdSuccessListener
    {
        fun viewAppointmentByIdSuccess(app: Appointment)
    }

    interface ViewAppointmentByIdFailureListener
    {
        fun viewAppointmentByIdFailure()
    }

    //create appointment interfaces
    interface CreateAppointmentSuccessListener
    {
        fun createAppointmentSuccess(app: Appointment)
    }

    interface CreateAppointmentFailureListener
    {
        fun createAppointmentFailure()
    }

    //Upcoming Appointments for patient
    interface ViewUpcomingAppointmentsPatientSuccessListener{
        fun viewUpcomingAppointmentsPatientSuccess(appointments: ArrayList<Appointment>)
    }

    interface ViewUpcomingAppointmentsPatientFailureListener{
        fun viewUpcomingAppointmentsPatientFailure()
    }

    //Upcoming Appointments for doctor
    interface ViewUpcomingAppointmentsDoctorSuccessListener{
        fun viewUpcomingAppointmentsDoctorSuccess(appointments: ArrayList<Appointment>)
    }

    interface ViewUpcomingAppointmentsDoctorFailureListener{
        fun viewUpcomingAppointmentsDoctorFailure()
    }

    //Past Appointments for patient
    interface ViewPastAppointmentsPatientSuccessListener{
        fun viewPastAppointmentsPatientSuccess(appointments: ArrayList<Appointment>)
    }
    interface ViewPastAppointmentsPatientFailureListener{
        fun viewPastAppointmentsPatientFailure()
    }

    //Past Appointments for doctor
    interface ViewPastAppointmentsDoctorSuccessListener{
        fun viewPastAppointmentsDoctorSuccess(appointments: ArrayList<Appointment>)
    }

    //Complete Appointment
    interface CompleteAppointmentSuccessListener
    {
        fun completeAppointmentSuccess()
    }

    interface CompleteAppointmentFailureListener
    {
        fun completeAppointmentFailure()
    }

    //Delete Appointment By Id
    interface ViewPastAppointmentsDoctorFailureListener{
        fun viewPastAppointmentsDoctorFailure()
    }

    interface DeleteAppointmentByIdSuccessListener
    {
        fun deleteAppointmentByIdSuccess()
    }


    interface DeleteAppointmentByIdFailureListener
    {
        fun deleteAppointmentByIdFailure()
    }

    //Update Prescription
    interface UpdatePrescriptionSuccessListener
    {
        fun updatePrescriptionSuccess()
    }
    interface UpdatePrescriptionFailureListener
    {
        fun updatePrescriptionFailure()
    }



    /***interface setters***/
    //set Upcoming Appointments for patient
    fun setViewUpcomingAppointmentsPatientSuccessListener(int: ViewUpcomingAppointmentsPatientSuccessListener){
        this.mViewUpcomingAppointmentsPatientSuccessListener = int
    }

    fun setViewUpcomingAppointmentsPatientFailureListener(int: ViewUpcomingAppointmentsPatientFailureListener){
        this.mViewUpcomingAppointmentsPatientFailureListener = int
    }
    //set Upcoming Appointments for doctor
    fun setViewUpcomingAppointmentsDoctorSuccessListener(int: ViewUpcomingAppointmentsDoctorSuccessListener){
        this.mViewUpcomingAppointmentsDoctorSuccessListener = int
    }

    fun setViewUpcomingAppointmentsDoctorFailureListener(int: ViewUpcomingAppointmentsDoctorFailureListener){
        this.mViewUpcomingAppointmentsDoctorFailureListener = int
    }

    //set Past Appointments for patient
    fun setViewPastAppointmentsPatientSuccessListener(int: ViewPastAppointmentsPatientSuccessListener){
        this.mViewPastAppointmentsPatientSuccessListener = int
    }
    fun setViewPastAppointmentsPatientFailureListener(int: ViewPastAppointmentsPatientFailureListener){
        this.mViewPastAppointmentsPatientFailureListener = int
    }

    //set Past Appointments for doctor
    fun setViewPastAppointmentsDoctorSuccessListener(int: ViewPastAppointmentsDoctorSuccessListener){
        this.mViewPastAppointmentsDoctorSuccessListener = int
    }
    fun setViewPastAppointmentsDoctorFailureListener(int: ViewPastAppointmentsDoctorFailureListener){
        this.mViewPastAppointmentsDoctorFailureListener = int
    }

    //Set Complete Appointment Interface
    fun setCompleteAppointmentSuccessListener(int: CompleteAppointmentSuccessListener)
    {
        this.mCompleteAppointmentSuccessListener = int
    }

    fun setCompleteAppointmentFailureListener(int: CompleteAppointmentFailureListener)
    {
        this.mCompleteAppointmentFailureListener = int
    }

    //Set Delete Appointment By Id interface
    fun setDeleteAppointmentByIdSuccessListener(int: DeleteAppointmentByIdSuccessListener)
    {
        this.mDeleteAppointmentByIdSuccessListener = int
    }

    fun setDeleteAppointmentByIdFailureListener(int: DeleteAppointmentByIdFailureListener)
    {
        this.mDeleteAppointmentByIdFailureListener = int
    }

    //Update Prescription

    fun setUpdatePrescriptionSuccessListener(int: MainActivity)
    {
        this.mUpdatePrescriptionSuccessListener = int
    }
    fun setUpdatePrescriptionFailureListener(int: MainActivity)
    {
        this.mUpdatePrescriptionFailureListener = int
    }

    fun setCreateAppointmentSuccessListener(int: CreateAppointmentSuccessListener)
    {
        this.mCreateAppointmentSuccessListener = int
    }
    fun setCreateAppointmentFailureListener(int: CreateAppointmentFailureListener)
    {
        this.mCreateAppointmentFailureListener = int
    }

    fun setViewAppointmentByIdSuccessListener(int: ViewAppointmentByIdSuccessListener)
    {
        this.mViewAppointmentByIdSuccessListener = int
    }
    fun setViewAppointmentByIdFailureListener(int: ViewAppointmentByIdFailureListener)
    {
        this.mViewAppointmentByIdFailureListener = int
    }
}