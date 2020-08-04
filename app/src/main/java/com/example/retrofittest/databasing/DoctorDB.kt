package com.example.retrofittest.databasing

import com.example.retrofittest.MainActivity
import com.example.retrofittest.models.Doctor
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DoctorDB {

    //interface variables
    //Find Doctor By Id
    lateinit var mGetDoctorByIdSuccessListener: GetDoctorByIdSuccessListener
    lateinit var mGetDoctorByIdFailureListener: GetDoctorByIdFailureListener

    //Find Doctors
    lateinit var mGetDoctorsSuccessListener: GetDoctorsSuccessListener
    lateinit var mGetDoctorsFailureListener: GetDoctorsFailureListener


    //functions
    //Find Doctor By Id
    fun getDoctorByID(id: String)
    {
        val paramsJSON = JSONObject()
        paramsJSON.put("doctorId", id)
        val params = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), paramsJSON.toString())

        val call = APIObject.api.getDoctorById(params)

        call.enqueue(object: Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                mGetDoctorByIdFailureListener.getDoctorByIDFailure()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if ( response.isSuccessful )
                {
                    val jsonRes = JSONObject(response.body()!!.string())
                    val doctor = Doctor().fromJSON(jsonRes.getJSONObject("userObj"))
                    mGetDoctorByIdSuccessListener.getDoctorByIDSuccess(doctor)
                }
                else
                    mGetDoctorByIdFailureListener.getDoctorByIDFailure()
            }
        })
    }

    //Find the Doctors in general with email and limit
    fun getDoctors(limit: Int, email: String)
    {
        val paramsJSON = JSONObject()
        paramsJSON.put("email", email)
        paramsJSON.put("limit", limit)
        val params = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), paramsJSON.toString())

        val call = APIObject.api.getDoctors(params)

        call.enqueue(object: Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                val message = "Failed to retrieve from database"
                mGetDoctorsFailureListener.getDoctorsFailure(message)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if ( response.isSuccessful )
                {
                    val jsonRes = JSONObject(response.body()!!.string())
                    val doctors = jsonRes.getJSONArray("user")

                    if(doctors.length() != 0) {
                        for (i in 0 until doctors.length()) {
                            val doctorJsonObject = doctors.getJSONObject(i)
                            val doctor = Doctor().fromJSON(doctorJsonObject)
                            mGetDoctorsSuccessListener.getDoctorsSuccess(doctor)
                        }
                    }
                    else{
                        val message = "No doctor found"
                        mGetDoctorsFailureListener.getDoctorsFailure(message)
                    }
                }
                else{
                    val message = "Failed to retrieve response as success"
                    mGetDoctorsFailureListener.getDoctorsFailure(message)
                }

            }
        })
    }

    //Find the Doctors in general with limit
    fun getDoctors(limit: Int)
    {
        val paramsJSON = JSONObject()
        paramsJSON.put("limit", limit)
        val params = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), paramsJSON.toString())

        val call = APIObject.api.getDoctors(params)

        call.enqueue(object: Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                val message = "Failed to retrieve from database"
                mGetDoctorsFailureListener.getDoctorsFailure(message)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if ( response.isSuccessful )
                {
                    val jsonRes = JSONObject(response.body()!!.string())
                    val doctors = jsonRes.getJSONArray("user")

                    if(doctors.length() != 0) {
                        for (i in 0 until doctors.length()) {
                            val doctorJsonObject = doctors.getJSONObject(i)
                            val doctor = Doctor().fromJSON(doctorJsonObject)
                            mGetDoctorsSuccessListener.getDoctorsSuccess(doctor)
                        }
                    }
                    else{
                        val message = "No doctor found"
                        mGetDoctorsFailureListener.getDoctorsFailure(message)
                    }
                }
                else{
                    val message = "Failed to retrieve response as success"
                    mGetDoctorsFailureListener.getDoctorsFailure(message)
                }

            }
        })
    }

    //Find the Doctors in general with email
    fun getDoctors(email: String)
    {
        val paramsJSON = JSONObject()
        paramsJSON.put("email", email)
        val params = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), paramsJSON.toString())

        val call = APIObject.api.getDoctors(params)

        call.enqueue(object: Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                val message = "Failed to retrieve from database"
                mGetDoctorsFailureListener.getDoctorsFailure(message)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if ( response.isSuccessful )
                {
                    val jsonRes = JSONObject(response.body()!!.string())
                    val doctors = jsonRes.getJSONArray("user")

                    if(doctors.length() != 0) {
                        for (i in 0 until doctors.length()) {
                            val doctorJsonObject = doctors.getJSONObject(i)
                            val doctor = Doctor().fromJSON(doctorJsonObject)
                            mGetDoctorsSuccessListener.getDoctorsSuccess(doctor)
                        }
                    }
                    else{
                        val message = "No doctor found"
                        mGetDoctorsFailureListener.getDoctorsFailure(message)
                    }
                }
                else{
                    val message = "Failed to retrieve response as success"
                    mGetDoctorsFailureListener.getDoctorsFailure(message)
                }

            }
        })
    }

    //Find the Doctors in general
    fun getDoctors()
    {
        val paramsJSON = JSONObject()
        paramsJSON.put(null, null)
        val params = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), paramsJSON.toString())

        val call = APIObject.api.getDoctors(params)

        call.enqueue(object: Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                val message = "Failed to retrieve from database"
                mGetDoctorsFailureListener.getDoctorsFailure(message)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if ( response.isSuccessful )
                {
                    val jsonRes = JSONObject(response.body()!!.string())
                    val doctors = jsonRes.getJSONArray("user")

                    if(doctors.length() != 0) {
                        for (i in 0 until doctors.length()) {
                            val doctorJsonObject = doctors.getJSONObject(i)
                            val doctor = Doctor().fromJSON(doctorJsonObject)
                            mGetDoctorsSuccessListener.getDoctorsSuccess(doctor)
                        }
                    }
                    else{
                        val message = "No doctor found"
                        mGetDoctorsFailureListener.getDoctorsFailure(message)
                    }
                }
                else{
                    val message = "Failed to retrieve response as success"
                    mGetDoctorsFailureListener.getDoctorsFailure(message)
                }

            }
        })
    }

    //interfaces

    //Find Doctor By Id
    interface GetDoctorByIdSuccessListener
    {
        fun getDoctorByIDSuccess(doctor: Doctor)
    }

    interface GetDoctorByIdFailureListener
    {
        fun getDoctorByIDFailure()
    }

    //Find doctors
    interface GetDoctorsSuccessListener
    {
        fun getDoctorsSuccess(doctor: Doctor)
    }

    interface GetDoctorsFailureListener
    {
        fun getDoctorsFailure(message: String)
    }


    //interface setters

    //Find Doctor By Id
    fun setGetDoctorByIDSuccessListener(int: MainActivity)
    {
        this.mGetDoctorByIdSuccessListener = int
    }

    fun setGetDoctorByIDFailureListener(int: MainActivity)
    {
        this.mGetDoctorByIdFailureListener = int
    }

    //Find doctors
    fun setGetDoctorsSuccessListener(int: MainActivity)
    {
        this.mGetDoctorsSuccessListener = int
    }

    fun setGetDoctorsFailureListener(int: MainActivity)
    {
        this.mGetDoctorsFailureListener = int
    }
}