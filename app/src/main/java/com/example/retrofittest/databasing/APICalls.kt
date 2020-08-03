//This is where we're going to post to all the routes

package com.example.retrofittest.databasing

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface APICalls {

    //doctor routes

    @POST("doctor/findById")
    fun getDoctorById(@Body params: RequestBody): Call<ResponseBody>

    @POST("doctor/find")
    fun getDoctors(@Body params: RequestBody): Call<ResponseBody>

    @POST("doctor/findTop")
    fun getTopDoctors(@Body params: RequestBody): Call<ResponseBody>

    @POST("doctor/findTopInAllCategories")
    fun getTopDoctorsInAllCategories(@Body params: RequestBody): Call<ResponseBody>

    @POST("doctor/updateProfile")
    fun updateProfileById(@Header("Authorization") jwt: String, @Body params: RequestBody): Call<ResponseBody>

    @POST("doctor/deleteById")
    fun deleteDoctorById(@Header("Authorization") jwt: String, @Body params: RequestBody): Call<ResponseBody>


    //patient routes


    //slot routes


    //appointment routes


    //rating routes
}