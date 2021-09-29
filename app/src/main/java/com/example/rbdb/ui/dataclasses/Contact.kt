package com.example.rbdb.ui.dataclasses

import java.io.Serializable

data class Contact(val avatar: Int, val name: String, val company: String, val phone: String) :
    Serializable
