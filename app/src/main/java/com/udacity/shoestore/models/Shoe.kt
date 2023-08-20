package com.udacity.shoestore.models

import java.io.Serializable

data class Shoe(var name: String, var size: Double, var company: String, var description: String) : Serializable