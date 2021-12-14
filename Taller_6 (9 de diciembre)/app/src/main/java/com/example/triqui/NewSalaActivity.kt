package com.example.triqui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NewSalaActivity : AppCompatActivity() {
    private val create by lazy<TextView> { findViewById(R.id.create) }
    private val editTextTextPersonName by lazy<TextView> { findViewById(R.id.editTextTextPersonName) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_sala)
        val animationScale = AnimationUtils.loadAnimation(this, R.anim.button_choice_small)
        setListeners(animationScale)
    }

    private fun setListeners(animationScale: Animation) {
        create.setOnClickListener {
            val text = editTextTextPersonName.text.toString()
            val sala = hashMapOf(
                "nombresala" to text,
                "participanteactual" to "jugador1",
                "posicion1" to "",
                "posicion2" to "",
                "posicion3" to "",
                "posicion4" to "",
                "posicion5" to "",
                "posicion6" to "",
                "posicion7" to "",
                "posicion8" to "",
                "posicion9" to "",
                "puntuacionjugador1" to 0,
                "puntuacionjugador2" to 0,
                "puntuacionempates" to 0,
                "participante1" to true,
                "participante2" to false,

            )
            val db = Firebase.firestore
            db.collection("salasdejuego").document(text).set(sala)
                .addOnSuccessListener { result ->
                    Log.w("DEBUG", "creada satisfactoriamente.")
                    val intent = Intent(this, TableroTriquiOnline::class.java)
                    intent.putExtra("sala", text)
                    intent.putExtra("jugador2", "false")
                    intent.putExtra("participanteActual", "jugador1")
                    startActivity(intent)
                }
                .addOnFailureListener { exception ->
                    Log.w("DEBUG", "Error getting documents.", exception)
                }
        }
    }
}