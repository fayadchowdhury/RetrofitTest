package com.example.retrofittest.databasing

import com.example.retrofittest.models.Doctor
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DoctorDB {

    //interface variables
    lateinit var mGetDoctorByIdSuccessListener: GetDoctorByIdSuccessListener
    lateinit var mGetDoctorByIdFailureListener: GetDoctorByIdFailureListener


    //functions
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


    //interfaces
    interface GetDoctorByIdSuccessListener
    {
        fun getDoctorByIDSuccess(doctor: Doctor)
    }

    interface GetDoctorByIdFailureListener
    {
        fun getDoctorByIDFailure()
    }


    //interface setters
    fun setGetDoctorByIDSuccessListener(int: GetDoctorByIdSuccessListener)
    {
        this.mGetDoctorByIdSuccessListener = int
    }

    fun setGetDoctorByIDFailureListener(int: GetDoctorByIdFailureListener)
    {
        this.mGetDoctorByIdFailureListener = int
    }
}