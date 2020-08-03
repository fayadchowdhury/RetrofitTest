package com.example.retrofittest.models

import org.json.JSONObject

class Doctor
{
    lateinit var id: String
    lateinit var name: String
    lateinit var email: String
    lateinit var pass: String
    lateinit var phone: String
    lateinit var dob: String
    lateinit var gender: String
    lateinit var blood: String
    lateinit var address: String
    lateinit var specialty: String
    lateinit var bmdc: String

    fun fromJSON(res: JSONObject) : Doctor
    {
        val doctor = Doctor()

        if ( res.has("id"))
            doctor.id = res.get("id").toString()

        if ( res.has("name"))
            doctor.name = res.get("name").toString()

        if ( res.has("email"))
            doctor.email = res.get("email").toString()

        if ( res.has("pass"))
            doctor.pass = res.get("pass").toString()

        if ( res.has("phone"))
            doctor.phone = res.get("phone").toString()

        if ( res.has("dob"))
            doctor.dob = res.get("dob").toString()

        if ( res.has("gender"))
            doctor.gender = res.get("gender").toString()

        if ( res.has("blood"))
            doctor.blood = res.get("blood").toString()

        if ( res.has("address"))
            doctor.address = res.get("address").toString()

        if ( res.has("specialty"))
            doctor.specialty = res.get("specialty").toString()

        if ( res.has("bmdc"))
            doctor.bmdc = res.get("bmdc").toString()

        return doctor
    }
}