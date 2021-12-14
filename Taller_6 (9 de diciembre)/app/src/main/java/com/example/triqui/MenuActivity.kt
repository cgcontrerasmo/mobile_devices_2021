package com.example.triqui

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MenuActivity : AppCompatActivity() {
    private val online by lazy<TextView> { findViewById(R.id.online) }
    private val individual by lazy<TextView> { findViewById(R.id.individual) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val animationScale = AnimationUtils.loadAnimation(this, R.anim.button_choice_small)
        setListeners(animationScale)
    }

    private fun setListeners(animationScale: Animation) {
        online.setOnClickListener {
            val intent = Intent(this, SalaActivity::class.java)
            startActivity(intent)
        }
        individual.setOnClickListener {
            onBackPressed()
        }
    }



}