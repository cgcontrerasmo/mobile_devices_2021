package com.example.triqui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.widget.ArrayAdapter
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ListView
import android.widget.TextView
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SalaActivity : AppCompatActivity() {
    var salasArry = ArrayList<String>()
    var salasArryId = ArrayList<String>()
    lateinit var listView: ListView
    private val newSala by lazy<TextView> { findViewById(R.id.newSala) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sala)
        listView = findViewById<View>(R.id.listSalas) as ListView
        getSalas()
        val animationScale = AnimationUtils.loadAnimation(this, R.anim.button_choice_small)
        setListeners(animationScale)
    }

    fun getSalas() {
        val db = Firebase.firestore
        db.collection("salasdejuego")
            .get()
            .addOnSuccessListener { result ->
                val list = ArrayList<QueryDocumentSnapshot>()
                for (document in result) {
                    list.add(document)
                    Log.d("DEBUG", "${document.id} => ${document.data}")
                    salasArry.add(document.data.get("nombresala").toString())
                    salasArryId.add(document.id.toString())
                }
                var adapter: ArrayAdapter<*> = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1, salasArry
                )
                listView.setAdapter(adapter)
            }
            .addOnFailureListener { exception ->
                Log.w("DEBUG", "Error getting documents.", exception)
            }
    }

    private fun setListeners(animationScale: Animation) {
        newSala.setOnClickListener {
            val intent = Intent(this, NewSalaActivity::class.java)
            startActivity(intent)
        }
        listView.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(this, TableroTriquiOnline::class.java)
            intent.putExtra("sala", salasArry[i])
            intent.putExtra("jugador2", "true")
            intent.putExtra("participanteActual", "jugador2")
            startActivity(intent)
        }
    }
}