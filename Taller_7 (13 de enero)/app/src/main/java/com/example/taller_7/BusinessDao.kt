package com.example.taller_7

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BusinessDao {
    @Query("SELECT * FROM Business")
    fun getAll(): LiveData<List<Business>>

    @Query("SELECT * FROM Business WHERE idBussiness=:id")
    fun get(id:Int): LiveData<Business>

    @Insert
    fun insertAll(vararg business: Business)

    @Update
    fun update(business: Business)

    @Delete
    fun delete(business: Business)
}