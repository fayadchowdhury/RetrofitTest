package com.example.retrofittest.models

import org.json.JSONObject

class Patient
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
    lateinit var past: String

    fun fromJSON(res: JSONObject) : Patient
    {
        val patient = Patient()

        if ( res.has("id"))
            patient.id = res.get("id").toString()

        if ( res.has("name"))
            patient.name = res.get("name").toString()

        if ( res.has("email"))
            patient.email = res.get("email").toString()

        if ( res.has("pass"))
            patient.pass = res.get("pass").toString()

        if ( res.has("phone"))
            patient.phone = res.get("phone").toString()

        if ( res.has("dob"))
            patient.dob = res.get("dob").toString()

        if ( res.has("gender"))
            patient.gender = res.get("gender").toString()

        if ( res.has("blood"))
            patient.blood = res.get("blood").toString()

        if ( res.has("address"))
            patient.address = res.get("address").toString()

        if ( res.has("past"))
            patient.past = res.get("past").toString()

        return patient
    }
}