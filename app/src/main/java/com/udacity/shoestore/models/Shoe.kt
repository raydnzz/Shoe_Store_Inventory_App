package com.udacity.shoestore.models

import java.io.Serializable

data class Shoe(var name: String = "", var size: String = "0", var company: String = "", var description: String = "") : Serializable