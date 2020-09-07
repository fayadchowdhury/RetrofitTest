package com.example.retrofittest.databasing

import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager
import com.example.retrofittest.models.Slot
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SlotDB(val context: Context) {

    //interface variables
    lateinit var mCreateSlotSuccessListener: createSlotSuccessListener
    lateinit var mCreateSlotFailureListener: createSlotFailureListener

    lateinit var mDeleteDoctorSlotsFailureListener: deleteDoctorSlotsFailureListener
    lateinit var mDeleteDoctorSlotsSuccessListener: deleteDoctorSlotsSuccessListener

    lateinit var mDeleteSlotByIdFailureListener: deleteSlotByIdFailureListener
    lateinit var mDeleteSlotByIdSuccessListener: deleteSlotByIdSuccessListener

    // functions
    fun getSlotById()
    {

    }

    fun viewAllSlotsByDoctorId()
    {

    }

    //TODO: Make this function cleaner parameter-wise
    fun createSlot(date: String, startTime: String, endTime: String, numSlots: Int, status: Int)
    {
        val slotArray = arrayListOf<Slot>()

        val sh = PreferenceManager.getDefaultSharedPreferences(context)
        val jwt = sh.getString("jwt", "NONE FOUND").toString()
        val uid = sh.getString("uid", "NONE FOUND").toString()
        if ( jwt == "NONE FOUND" || uid == "NONE FOUND" )
        {
            //don't go any further
            Log.d("CREATESLOTS", "$jwt $uid")
            mCreateSlotFailureListener.createSlotFailure()
        }
        else
        {
            val paramsJSON = JSONObject()
            paramsJSON.put("doctorId", uid)
            paramsJSON.put("dateOfSlot", date)
            paramsJSON.put("startTime", startTime)
            paramsJSON.put("endTime", endTime)
            paramsJSON.put("status", status.toString())
            paramsJSON.put("numSlots", numSlots.toString())

            val params = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), paramsJSON.toString())

            val headerJwt = "Bearer $jwt"

            val call = APIObject.api.createSlot(headerJwt, params)

            call.enqueue(object: Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    val jsonRes = JSONObject(response.body()!!.string())
                    val slots = jsonRes.getJSONArray("slots")
                    Log.d("DEBUGSLOTS", "$slots")
                    for ( i in 0 until slots.length() )
                    {
                        val slot = Slot().fromJSON(slots.getJSONObject(i))
                        slotArray.add(slot)
                    }
                    mCreateSlotSuccessListener.createSlotSuccess(slotArray)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    mCreateSlotFailureListener.createSlotFailure()
                }
            })
        }
    }

   //delete slot by ID

    fun deleteSlotById(slotId: String)
    {
        val sh = PreferenceManager.getDefaultSharedPreferences(context)
       // val jwt = sh.getString("jwt", "NONE FOUND").toString()
        val jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJBcHBPaW50bWVudCIsInN1YiI6IjI4ZWJkOTYyLTgwZDUtNDY3Yy1iMDkyLTQzMWEyOGViNjQ5MyIsImlhdCI6MTU5OTQ2Njg2OTI2NH0.d9pdQ77wDIEbECEiluMxpXUK9WP5fD2mtsjqopLY2Pg"
        //val uid = sh.getString("uid", "NONE FOUND").toString()
        if ( jwt == "NONE FOUND"  )
        {
            //don't go any further
            Log.d("DELETEAPI", "$jwt ")
            mDeleteSlotByIdFailureListener.deleteSlotByIdFailure()
        }
        else {
            Log.d("DELETEAPI", "$jwt ")
            val paramsJSON = JSONObject()
            paramsJSON.put("slotId", slotId)
            Log.d("Paramsjson", "$paramsJSON ")
            val params = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), paramsJSON.toString())

            val headerJwt = "Bearer $jwt"
            val call = APIObject.api.deleteSlotById(headerJwt, params)

            call.enqueue(object: Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    mDeleteSlotByIdFailureListener.deleteSlotByIdFailure()
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if ( response.isSuccessful )
                    {
                        Log.d("Response", "$response ")
                        mDeleteSlotByIdSuccessListener.deleteSlotByIdSuccess()
                    }
                    else
                    {
                        mDeleteSlotByIdFailureListener.deleteSlotByIdFailure()
                    }
                }
            })
        }
    }

    //delete all slots by doctor ID

    fun deleteSlotsByDoctorId()
    {
        val sh = PreferenceManager.getDefaultSharedPreferences(context)
        val jwt = sh.getString("jwt", "NONE FOUND").toString()
        val uid = sh.getString("uid", "NONE FOUND").toString()
        if ( jwt == "NONE FOUND" || uid == "NONE FOUND" )
        {
            //don't go any further
            Log.d("CREATESLOTS", "$jwt $uid")
            mDeleteDoctorSlotsFailureListener.deleteDoctorSlotsFailure()
        }
        else
        {
            val paramsJSON = JSONObject()
            paramsJSON.put("doctorId", uid)
            val params = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), paramsJSON.toString())

            val headerJwt = "Bearer $jwt"

            val call = APIObject.api.deleteSlotByDoctorId(headerJwt, params)

            call.enqueue(object: Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    mDeleteDoctorSlotsSuccessListener.deleteDoctorSlotsSuccess()
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    mDeleteDoctorSlotsFailureListener.deleteDoctorSlotsFailure()
                }
            })
        }
    }

    //interfaces
    interface createSlotSuccessListener
    {
        fun createSlotSuccess(slotArray: ArrayList<Slot>)
    }

    interface createSlotFailureListener
    {
        fun createSlotFailure()
    }

    interface deleteDoctorSlotsSuccessListener
    {
        fun deleteDoctorSlotsSuccess()
    }

    interface deleteDoctorSlotsFailureListener
    {
        fun deleteDoctorSlotsFailure()
    }
    //delete slot by Id
    interface deleteSlotByIdSuccessListener
    {
        fun deleteSlotByIdSuccess()
    }

    interface deleteSlotByIdFailureListener
    {
        fun deleteSlotByIdFailure()
    }

    //interface setters
    fun setCreateSlotSuccessListener(int: createSlotSuccessListener)
    {
        this.mCreateSlotSuccessListener = int
    }

    fun setCreateSlotFailureListener(int: createSlotFailureListener)
    {
        this.mCreateSlotFailureListener = int
    }

    fun setDeleteDoctorSlotsSuccessListener(int: deleteDoctorSlotsSuccessListener)
    {
        this.mDeleteDoctorSlotsSuccessListener = int
    }

    fun setDeleteDoctorSlotsFailureListener(int: deleteDoctorSlotsFailureListener)
    {
        this.mDeleteDoctorSlotsFailureListener = int
    }
    //Delete slot by Id
    fun setDeleteSlotByIdSuccessListener(int: deleteSlotByIdSuccessListener)
    {
        this.mDeleteSlotByIdSuccessListener = int
    }

    fun setDeleteSlotByIdFailureListener(int: deleteSlotByIdFailureListener)
    {
        this.mDeleteSlotByIdFailureListener = int
    }
}