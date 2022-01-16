package com.example.taller_7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns


class MainActivity : AppCompatActivity() {
    private val create by lazy<TextView> { findViewById(R.id.create) }
    private val listBusiness by lazy<TextView> { findViewById(R.id.listBusiness) }
    private val update by lazy<TextView> { findViewById(R.id.update) }
    private val delete by lazy<TextView> { findViewById(R.id.delete) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners()
    }

    private fun setListeners() {
        create.setOnClickListener {
            val intent = Intent(this, CreateBussines::class.java)
            startActivity(intent)
        }
        listBusiness.setOnClickListener {
            val intent = Intent(this, ListBussiness::class.java)
            intent.putExtra("action", "List")
            startActivity(intent)
        }
        update.setOnClickListener {
            val intent = Intent(this, ListBussiness::class.java)
            intent.putExtra("action", "Update")
            startActivity(intent)
        }
        delete.setOnClickListener {
            val intent = Intent(this, ListBussiness::class.java)
            intent.putExtra("action", "Delete")
            startActivity(intent)
        }
    }
}