package com.example.taller_7

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.lifecycle.Observer

class ListBussiness : AppCompatActivity() {
    lateinit var typeAction: String
    lateinit var listView: ListView
    var nameListBussiness = ArrayList<String>()
    var listBusiness = ArrayList<Business>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_bussiness)

        listView = findViewById<ListView>(R.id.listBusiness)

        val database = AppDatabase.getDatabase(this)
        database.business().getAll().observe(this, Observer {
            it.forEach{
                listBusiness.add(it)
            }
            listBusiness.forEach {
                nameListBussiness.add(it.name.toString())
            }
            var adapter: ArrayAdapter<*> = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1, nameListBussiness
            )
            listView.setAdapter(adapter)
        })
        typeAction = intent.getStringExtra("action").toString()
    }

    private fun setListeners() {
            listView.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(this, TableroTriquiOnline::class.java)
            intent.putExtra("sala", salasArry[i])
            intent.putExtra("jugador2", "true")
            intent.putExtra("participanteActual", "jugador2")
            startActivity(intent)
        }
    }
}
