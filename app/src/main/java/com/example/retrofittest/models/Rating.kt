package com.example.retrofittest.models

import org.json.JSONObject

class Rating
{
    lateinit var doctorId: String
    /*var numRatings: Double? = 0.0
    var sumRatings: Double? = 0.0
    var average: Double? = 0.0*/
    lateinit var numRatings: String
    lateinit var sumRatings: String
    lateinit var average: String

    fun fromJSON(res: JSONObject) : Rating
    {
        val rating = Rating()

        if ( !res.isNull("doctorId") )
            rating.doctorId = res.get("doctorId").toString()

        if ( !res.isNull("numRatings"))
            rating.numRatings = res.get("numRatings").toString()

        if ( !res.isNull("sumRatings"))
            rating.sumRatings = res.get("sumRatings").toString()

        if ( !res.isNull("average"))
            rating.average = res.get("average").toString()

        return rating
    }
}