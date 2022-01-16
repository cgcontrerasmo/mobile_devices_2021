package com.example.taller_7

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Business")
class Business(
    val name: String,
    val URL: String,
    val telephone: String,
    val email: String,
    val product: String,
    val type: String,
    @PrimaryKey(autoGenerate = true)
    var idBussiness: Int = 0
) : Serializable