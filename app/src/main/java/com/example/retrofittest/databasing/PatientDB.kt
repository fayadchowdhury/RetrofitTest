package com.example.retrofittest.databasing

import android.content.Context
import android.util.Log
import com.example.retrofittest.MainActivity
import com.example.retrofittest.models.Patient
import androidx.preference.PreferenceManager
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PatientDB(val context: Context) {
    //interface variables
    //Find Patient By Id
    lateinit var mGetPatientByIdSuccessListener: GetPatientByIdSuccessListener
    lateinit var mGetPatientByIdFailureListener: GetPatientByIdFailureListener

    //Update Patient Profile
    lateinit var mUpdatePatientProfileSuccessListener: UpdatePatientProfileSuccessListener
    lateinit var mUpdatePatientProfileFailureListener: UpdatePatientProfileFailureListener

    //Delete Patient bBy Id
    lateinit var mDeletePatientByIdSuccessListener: DeletePatientByIdSuccessListener
    lateinit var mDeletePatientByIdFailureListener: DeletePatientByIdFailureListener


    //functions

    /*******Find Patient By Id ********/
    fun getPatientById(id: String)
    {
        val paramsJSON = JSONObject()
        paramsJSON.put("patientId", id)
        val params = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), paramsJSON.toString())

        val call = APIObject.api.getPatientById(params)

        call.enqueue(object: Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                mGetPatientByIdFailureListener.getPatientByIdFailure()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if ( response.isSuccessful )
                {
                    val jsonRes = JSONObject(response.body()!!.string())
                    val patient = Patient().fromJSON(jsonRes.getJSONObject("userObj"))
                    mGetPatientByIdSuccessListener.getPatientByIdSuccess(patient)
                }
                else
                    mGetPatientByIdFailureListener.getPatientByIdFailure()
            }
        })
    }


    fun updatePatientProfile(updOpts: Map<String, String>)
    {
        val sh = PreferenceManager.getDefaultSharedPreferences(context)
        val jwt = sh.getString("jwt", "NONE FOUND").toString()
        val uid = sh.getString("uid", "NONE FOUND").toString()
        if ( jwt == "NONE FOUND" || uid == "NONE FOUND" )
        {
            //don't go any further
            mUpdatePatientProfileFailureListener.updatePatientProfileFailure()
        }
        else
        {
            val paramsJSON = JSONObject()
            paramsJSON.put("id", uid)

            //manually check the updOpts map; could possibly be done with an existing converter but I cannot be bothered at this point
            if ( updOpts.containsKey("name") )
                paramsJSON.put("name", updOpts["name"].toString())
            if ( updOpts.containsKey("email") )
                paramsJSON.put("email", updOpts["email"].toString())
            if ( updOpts.containsKey("phone") )
                paramsJSON.put("phone", updOpts["phone"].toString())
            if ( updOpts.containsKey("dob") )
                paramsJSON.put("dob", updOpts["dob"].toString())
            if ( updOpts.containsKey("gender") )
                paramsJSON.put("gender", updOpts["gender"].toString())
            if ( updOpts.containsKey("blood") )
                paramsJSON.put("blood", updOpts["blood"].toString())
            if ( updOpts.containsKey("address") )
                paramsJSON.put("address", updOpts["address"].toString())



            val params = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), paramsJSON.toString())

            val headerJwt = "Bearer $jwt"
            val call = APIObject.api.updatePatientProfileById(headerJwt, params)

            call.enqueue(object: Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    mUpdatePatientProfileFailureListener.updatePatientProfileFailure()
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if ( response.isSuccessful )
                    {
                        mUpdatePatientProfileSuccessListener.updatePatientProfileSuccess()
                    }
                    else
                    {
                        mUpdatePatientProfileFailureListener.updatePatientProfileFailure()
                    }
                }
            })
        }
    }

    // Delete Patient by id

    fun deletePatientById()
    {
        val sh = PreferenceManager.getDefaultSharedPreferences(context)
        val jwt = sh.getString("jwt", "NONE FOUND").toString()
        val uid = sh.getString("uid", "NONE FOUND").toString()
        if ( jwt == "NONE FOUND" || uid == "NONE FOUND" )
        {
            //don't go any further
            Log.d("DELETEAPI", "$jwt $uid")
            mDeletePatientByIdFailureListener.deletePatientByIdFailure()
        }
        else {
            Log.d("DELETEAPI", "$jwt $uid")
            val paramsJSON = JSONObject()
            paramsJSON.put("patientId", uid)
            val params = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), paramsJSON.toString())

            val headerJwt = "Bearer $jwt"
            val call = APIObject.api.deletePatientById(headerJwt, params)

            call.enqueue(object: Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    mDeletePatientByIdFailureListener.deletePatientByIdFailure()
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if ( response.isSuccessful )
                    {
                        mDeletePatientByIdSuccessListener.deletePatientByIdSuccess()
                    }
                    else
                    {
                        mDeletePatientByIdFailureListener.deletePatientByIdFailure()
                    }
                }
            })
        }
    }



    /***************interfaces**************/
    //Find Patient By Id
    interface GetPatientByIdSuccessListener
    {
        fun getPatientByIdSuccess(patient: Patient)
    }

    interface GetPatientByIdFailureListener
    {
        fun getPatientByIdFailure()
    }

    //Update Patient Profile
    interface UpdatePatientProfileSuccessListener
    {
        fun updatePatientProfileSuccess()
    }
    interface UpdatePatientProfileFailureListener
    {
        fun updatePatientProfileFailure()
    }
    //Delete Patient by Id
    interface DeletePatientByIdSuccessListener
    {
        fun deletePatientByIdSuccess()
    }

    interface DeletePatientByIdFailureListener
    {
        fun deletePatientByIdFailure()
    }


    /***************interface setters************/

    //Get Patient By Id
    fun setGetPatientByIdSuccessListener(int: MainActivity)
    {
        this.mGetPatientByIdSuccessListener = int
    }
    fun setGetPatientByIdFailureListener(int: MainActivity)
    {
        this.mGetPatientByIdFailureListener = int
    }


   //Update Patient Profile

      fun setUpdatePatientProfileSuccessListener(int: MainActivity)
      {
          this.mUpdatePatientProfileSuccessListener = int
      }
      fun setUpdatePatientProfileFailureListener(int: MainActivity)
      {
          this.mUpdatePatientProfileFailureListener = int
      }


    //Delete Patient by Id

    fun setDeletePatientByIdSuccessListener(int: DeletePatientByIdSuccessListener)
    {
        this.mDeletePatientByIdSuccessListener = int
    }

    fun setDeletePatientByIdFailureListener(int: DeletePatientByIdFailureListener)
    {
        this.mDeletePatientByIdFailureListener = int
    }




}