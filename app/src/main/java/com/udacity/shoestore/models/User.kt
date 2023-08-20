package com.udacity.shoestore.models

import java.io.Serializable

data class User(val email: String, val password: String) : Serializable