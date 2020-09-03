package com.example.retrofittest.databasing

import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager
import com.example.retrofittest.models.Rating
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RatingDB(val context: Context) {
    //Get Rating by Id
    lateinit var mGetRatingsByIdSuccessListener: GetRatingsByIdSuccessListener
    lateinit var mGetRatingsByIdFailureListener: GetRatingsByIdFailureListener

    //update Rating by Id
    lateinit var mUpdateRatingSuccessListener: UpdateRatingSuccessListener
    lateinit var mUpdateRatingFailureListener: UpdateRatingFailureListener

    /*** Get Rating by ID***/
    fun getRatingsById(id: String)
    {
        val paramsJSON = JSONObject()
        paramsJSON.put("doctorId", id)
        val params = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), paramsJSON.toString())

        val call = APIObject.api.getRatingsById(params)

        call.enqueue(object: Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                mGetRatingsByIdFailureListener.getRatingsByIDFailure()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if ( response.isSuccessful )
                {
                    val jsonRes = JSONObject(response.body()!!.string())
                    val rating = Rating().fromJSON(jsonRes.getJSONArray("docRating").getJSONObject(0))

                    mGetRatingsByIdSuccessListener.getRatingsByIDSuccess(rating)
                }
                else
                    mGetRatingsByIdFailureListener.getRatingsByIDFailure()
            }
        })
    }

    fun editRatingsById(updOpts: Map<String, String>)
    {
        val sh = PreferenceManager.getDefaultSharedPreferences(context)
        val jwt = sh.getString("jwt", "NONE FOUND").toString()
        val uid = sh.getString("uid", "NONE FOUND").toString()
        if ( jwt == "NONE FOUND" || uid == "NONE FOUND" )
        {
            //don't go any further
            mUpdateRatingFailureListener.updateRatingFailure()
        }
        else
        {
            val paramsJSON = JSONObject()
            paramsJSON.put("doctorId", uid)
            paramsJSON.put("rating",updOpts["rating"].toString())
            //manually check the updOpts map; could possibly be done with an existing converter but I cannot be bothered at this point

            val params = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), paramsJSON.toString())

            val headerJwt = "Bearer $jwt"
            val call = APIObject.api.updatePatientProfileById(headerJwt, params)

            call.enqueue(object: Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    mUpdateRatingFailureListener.updateRatingFailure()
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if ( response.isSuccessful )
                    {
                        mUpdateRatingSuccessListener.updateRatingSuccess()
                    }
                    else
                    {
                        mUpdateRatingFailureListener.updateRatingFailure()
                    }
                }
            })
        }
    }

    /*** interfaces ***/
    //find rating by id
    interface GetRatingsByIdSuccessListener
    {
        fun getRatingsByIDSuccess(rating: Rating)
    }

    interface GetRatingsByIdFailureListener
    {
        fun getRatingsByIDFailure()
    }

    //update rating by id
    interface UpdateRatingSuccessListener
    {
        fun updateRatingSuccess()
    }

    interface UpdateRatingFailureListener
    {
        fun updateRatingFailure()
    }
    /***************interface setters************/

    //Find Rating By Id
    fun setGetRatingsByIDSuccessListener(int: GetRatingsByIdSuccessListener)
    {
        this.mGetRatingsByIdSuccessListener = int
    }

    fun setGetRatingsByIDFailureListener(int: GetRatingsByIdFailureListener)
    {
        this.mGetRatingsByIdFailureListener = int
    }

    //Update Rating by Id
    fun setUpdateRatingSuccessListener(int: UpdateRatingSuccessListener)
    {
        this.mUpdateRatingSuccessListener = int
    }

    fun setUpdateRatingFailureListener(int: UpdateRatingFailureListener)
    {
        this.mUpdateRatingFailureListener = int
    }
}