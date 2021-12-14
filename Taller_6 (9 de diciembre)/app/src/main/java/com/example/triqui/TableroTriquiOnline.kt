package com.example.triqui

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TableroTriquiOnline : AppCompatActivity() {
    private val zero by lazy<TextView> { findViewById(R.id.zero) }
    private val one by lazy<TextView> { findViewById(R.id.one) }
    private val two by lazy<TextView> { findViewById(R.id.two) }
    private val three by lazy<TextView> { findViewById(R.id.three) }
    private val four by lazy<TextView> { findViewById(R.id.four) }
    private val five by lazy<TextView> { findViewById(R.id.five) }
    private val six by lazy<TextView> { findViewById(R.id.six) }
    private val seven by lazy<TextView> { findViewById(R.id.seven) }
    private val eight by lazy<TextView> { findViewById(R.id.eight) }
    private val reset by lazy<TextView> { findViewById(R.id.reset) }
    private val quit by lazy<TextView> { findViewById(R.id.quit) }
    private val humanoView by lazy<TextView> { findViewById(R.id.scoreHumano) }
    private val maquinaView by lazy<TextView> { findViewById(R.id.scoreMaquina) }
    private val empateView by lazy<TextView> { findViewById(R.id.scoreEmpates) }
    private var turn = false
    private var mBoard = charArrayOf('1', '2', '3', '4', '5', '6', '7', '8', '9')
    lateinit var progressDialog: ProgressDialog
    val HUMAN_PLAYER = 'X'
    val COMPUTER_PLAYER = 'O'
    private var BOARD_SIZE = 9
    private var empates = 0
    private var humano = 0
    private var maquina = 0
    private var finish = false
    private var participante1 = true
    private var participante2 = false
    lateinit var sharedPreference: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    val db = Firebase.firestore
    lateinit var juegoActualEscribir: DocumentReference
    lateinit var participante: String
    var participanteFire = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tablero_triqui_online)
        progressDialog = ProgressDialog(this)
        val sala = intent.getStringExtra("sala").toString()
        juegoActualEscribir = db.collection("salasdejuego").document(sala)
        val jugadorDos = intent.getStringExtra("jugador2")
        if (jugadorDos == "true") {
            juegoActualEscribir.update("participante2", true)
        }
        participante = intent.getStringExtra("participanteActual").toString()
        val animationScale = AnimationUtils.loadAnimation(this, R.anim.button_choice_small)
        setListeners(animationScale)
        sharedPreference = getSharedPreferences("TICTACTOC", Context.MODE_PRIVATE)
        editor = sharedPreference.edit()
        humanoView.text = humano.toString()
        maquinaView.text = maquina.toString()
        empateView.text = empates.toString()
        juegoActualEscribir.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("TAG", "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                updateBoard(snapshot, animationScale)
            } else {
                Log.d("TAG", "Current data: null")
            }
        }
    }

    private fun checkForWinner(): Int {

        run {
            var i = 0
            while (i <= 6) {
                if (mBoard[i] == HUMAN_PLAYER && mBoard[i + 1] == HUMAN_PLAYER && mBoard[i + 2] == HUMAN_PLAYER
                ) {
                    Toast.makeText(this, "Ganó el jugador 1", Toast.LENGTH_LONG).show()
                    finish = true
                    humano += 1
                    juegoActualEscribir.update("puntuacionjugador1", humano)
                    humanoView.text = humano.toString()
                    return 2
                }
                if (mBoard[i] == COMPUTER_PLAYER && mBoard[i + 1] == COMPUTER_PLAYER && mBoard[i + 2] == COMPUTER_PLAYER
                ) {
                    Toast.makeText(this, "Ganó el jugador 2", Toast.LENGTH_LONG).show()
                    finish = true
                    maquina += 1
                    juegoActualEscribir.update("puntuacionjugador2", maquina)
                    maquinaView.text = maquina.toString()
                    return 3
                }
                i += 3
            }
        }
        for (i in 0..2) {
            if (mBoard[i] == HUMAN_PLAYER && mBoard[i + 3] == HUMAN_PLAYER && mBoard[i + 6] == HUMAN_PLAYER) {
                Toast.makeText(
                    this,
                    "Ganó el jugador 1",
                    Toast.LENGTH_LONG
                ).show()
                finish = true
                humano += 1
                juegoActualEscribir.update("puntuacionjugador1", humano)
                humanoView.text = humano.toString()
                return 2
            }
            if (mBoard[i] == COMPUTER_PLAYER && mBoard[i + 3] == COMPUTER_PLAYER && mBoard[i + 6] == COMPUTER_PLAYER) {
                Toast.makeText(
                    this,
                    "Ganó la jugador 2",
                    Toast.LENGTH_LONG
                ).show()
                finish = true
                maquina += 1
                juegoActualEscribir.update("puntuacionjugador2", maquina)
                maquinaView.text = maquina.toString()
                return 3
            }
        }
        if (mBoard[0] == HUMAN_PLAYER && mBoard[4] == HUMAN_PLAYER && mBoard[8] == HUMAN_PLAYER ||
            mBoard[2] == HUMAN_PLAYER && mBoard[4] == HUMAN_PLAYER && mBoard[6] == HUMAN_PLAYER
        ) {
            Toast.makeText(this, "Ganó el jugador 1", Toast.LENGTH_LONG).show()
            finish = true
            humano += 1
            juegoActualEscribir.update("puntuacionjugador1", humano)
            humanoView.text = humano.toString()
            return 2
        }
        if (mBoard[0] == COMPUTER_PLAYER && mBoard[4] == COMPUTER_PLAYER && mBoard[8] == COMPUTER_PLAYER ||
            mBoard[2] == COMPUTER_PLAYER && mBoard[4] == COMPUTER_PLAYER && mBoard[6] == COMPUTER_PLAYER
        ) {
            Toast.makeText(this, "Ganó la jugador 2", Toast.LENGTH_LONG).show()
            finish = true
            maquina += 1
            juegoActualEscribir.update("puntuacionjugador2", maquina)
            maquinaView.text = maquina.toString()
            return 3
        }
        if (BOARD_SIZE == 0) {
            Toast.makeText(this, "Empate", Toast.LENGTH_LONG).show()
            finish = true
            empates += 1
            juegoActualEscribir.update("puntuacionempates", maquina)
            empateView.text = empates.toString()
            return 1
        }
        return 0
    }

    private fun callFunction(
        animationScale: Animation,
        view: View,
        text: View,
        i: Int
    ): Boolean {
        if (!finish && (mBoard[i] != HUMAN_PLAYER || mBoard[i] != COMPUTER_PLAYER)) {
            view.startAnimation(animationScale)
            if (!turn) {
                text.background = resources.getDrawable(R.drawable.x)
                mBoard[i] = HUMAN_PLAYER
            } else {
                text.background = resources.getDrawable(R.drawable.o)
                mBoard[i] = COMPUTER_PLAYER
            }
            turn = !turn
            BOARD_SIZE--
            checkForWinner()
            return true
        } else {
            return false
        }
    }


    private fun updateBoard(snapshot: DocumentSnapshot?, animationScale: Animation) {
        if (snapshot != null) {
            participanteFire = snapshot.data?.get("participanteactual") as String
            if (snapshot.data?.get("participante2") == true && !participante2) {
                Toast.makeText(
                    this,
                    "Entró un jugador, comienza a jugar",
                    Toast.LENGTH_LONG
                ).show()
                participante2 = true
            }
            if (mBoard[0] == '1') {
                if (snapshot.data?.get("posicion1") == "O" || snapshot.data?.get("posicion1") == "X") {
                    callFunction(animationScale, zero, zero, 0)
                }
            }
            if (mBoard[1] == '2') {
                if (snapshot.data?.get("posicion2") == "O" || snapshot.data?.get("posicion2") == "X") {
                    callFunction(animationScale, one, one, 1)
                }
            }
            if (mBoard[2] == '3') {
                if (snapshot.data?.get("posicion3") == "O" || snapshot.data?.get("posicion3") == "X") {
                    callFunction(animationScale, two, two, 2)
                }
            }
            if (mBoard[3] == '4') {
                if (snapshot.data?.get("posicion4") == "O" || snapshot.data?.get("posicion4") == "X") {
                    callFunction(animationScale, three, three, 3)
                }
            }
            if (mBoard[4] == '5') {
                if (snapshot.data?.get("posicion5") == "O" || snapshot.data?.get("posicion5") == "X") {
                    callFunction(animationScale, four, four, 4)
                }
            }
            if (mBoard[5] == '6') {
                if (snapshot.data?.get("posicion6") == "O" || snapshot.data?.get("posicion6") == "X") {
                    callFunction(animationScale, five, five, 5)
                }
            }
            if (mBoard[6] == '7') {
                if (snapshot.data?.get("posicion7") == "O" || snapshot.data?.get("posicion7") == "X") {
                    callFunction(animationScale, six, six, 6)
                }
            }
            if (mBoard[7] == '8') {
                if (snapshot.data?.get("posicion8") == "O" || snapshot.data?.get("posicion8") == "X") {
                    callFunction(animationScale, seven, seven, 7)
                }
            }
            if (mBoard[8] == '9') {
                if (snapshot.data?.get("posicion9") == "O" || snapshot.data?.get("posicion9") == "X") {
                    callFunction(animationScale, eight, eight, 8)
                }
            }
        }
    }

    private fun setListeners(animationScale: Animation) {

        zero.setOnClickListener {
            if (participante1 && participante2 && participanteFire == participante) {
                if (mBoard[0] == '1') {
                    callFunction(animationScale, it, zero, 0)
                    if (!turn) {
                        juegoActualEscribir.update("posicion1", "X")
                    } else {
                        juegoActualEscribir.update("posicion1", "O")
                    }
                    if (participanteFire == "jugador1") {
                        juegoActualEscribir.update("participanteactual", "jugador2")
                    } else {
                        juegoActualEscribir.update("participanteactual", "jugador1")
                    }
                }
            } else {
                juegoActualEscribir.get().addOnSuccessListener { document ->
                    participante2 =
                        document.data?.get("participante2") as Boolean
                }
            }
        }
        one.setOnClickListener {
            if (participante1 && participante2 && participanteFire == participante) {
                if (mBoard[1] == '2') {
                    callFunction(animationScale, it, one, 1)
                    if (!turn) {
                        juegoActualEscribir.update("posicion2", "X")
                    } else {
                        juegoActualEscribir.update("posicion2", "O")
                    }
                    if (participanteFire == "jugador1") {
                        juegoActualEscribir.update("participanteactual", "jugador2")
                    } else {
                        juegoActualEscribir.update("participanteactual", "jugador1")
                    }
                }
            } else {
                juegoActualEscribir.get().addOnSuccessListener { document ->
                    participante2 =
                        document.data?.get("participante2") as Boolean
                }
            }
        }
        two.setOnClickListener {
            if (participante1 && participante2 && participanteFire == participante) {
                if (mBoard[2] == '3') {
                    callFunction(animationScale, it, two, 2)
                    if (!turn) {
                        juegoActualEscribir.update("posicion3", "X")
                    } else {
                        juegoActualEscribir.update("posicion3", "O")
                    }
                }
                if (participanteFire == "jugador1") {
                    juegoActualEscribir.update("participanteactual", "jugador2")
                } else {
                    juegoActualEscribir.update("participanteactual", "jugador1")
                }
            } else {
                juegoActualEscribir.get().addOnSuccessListener { document ->
                    participante2 =
                        document.data?.get("participante2") as Boolean
                }
            }
        }
        three.setOnClickListener {
            if (participante1 && participante2 && participanteFire == participante) {
                if (mBoard[3] == '4') {
                    callFunction(animationScale, it, three, 3)
                    if (!turn) {
                        juegoActualEscribir.update("posicion4", "X")
                    } else {
                        juegoActualEscribir.update("posicion4", "O")
                    }
                }
                if (participanteFire == "jugador1") {
                    juegoActualEscribir.update("participanteactual", "jugador2")
                } else {
                    juegoActualEscribir.update("participanteactual", "jugador1")
                }
            } else {
                juegoActualEscribir.get().addOnSuccessListener { document ->
                    participante2 =
                        document.data?.get("participante2") as Boolean
                }
            }
        }
        four.setOnClickListener {
            if (participante1 && participante2 && participanteFire == participante) {
                if (mBoard[4] == '5') {
                    callFunction(animationScale, it, four, 4)
                    if (!turn) {
                        juegoActualEscribir.update("posicion5", "X")
                    } else {
                        juegoActualEscribir.update("posicion5", "O")
                    }
                }
                if (participanteFire == "jugador1") {
                    juegoActualEscribir.update("participanteactual", "jugador2")
                } else {
                    juegoActualEscribir.update("participanteactual", "jugador1")
                }
            } else {
                juegoActualEscribir.get().addOnSuccessListener { document ->
                    participante2 =
                        document.data?.get("participante2") as Boolean
                }
            }
        }
        five.setOnClickListener {
            if (participante1 && participante2 && participanteFire == participante) {
                if (mBoard[5] == '6') {
                    callFunction(animationScale, it, five, 5)
                    if (!turn) {
                        juegoActualEscribir.update("posicion6", "X")
                    } else {
                        juegoActualEscribir.update("posicion6", "O")
                    }
                }
                if (participanteFire == "jugador1") {
                    juegoActualEscribir.update("participanteactual", "jugador2")
                } else {
                    juegoActualEscribir.update("participanteactual", "jugador1")
                }
            } else {
                juegoActualEscribir.get().addOnSuccessListener { document ->
                    participante2 =
                        document.data?.get("participante2") as Boolean
                }
            }
        }
        six.setOnClickListener {
            if (participante1 && participante2 && participanteFire == participante) {
                if (mBoard[6] == '7') {
                    callFunction(animationScale, it, six, 6)
                    if (!turn) {
                        juegoActualEscribir.update("posicion7", "X")
                    } else {
                        juegoActualEscribir.update("posicion7", "O")
                    }
                }
                if (participanteFire == "jugador1") {
                    juegoActualEscribir.update("participanteactual", "jugador2")
                } else {
                    juegoActualEscribir.update("participanteactual", "jugador1")
                }
            } else {
                juegoActualEscribir.get().addOnSuccessListener { document ->
                    participante2 =
                        document.data?.get("participante2") as Boolean
                }
            }
        }
        seven.setOnClickListener {
            if (participante1 && participante2 && participanteFire == participante) {
                if (mBoard[7] == '8') {
                    callFunction(animationScale, it, seven, 7)
                    if (!turn) {
                        juegoActualEscribir.update("posicion8", "X")
                    } else {
                        juegoActualEscribir.update("posicion8", "O")
                    }
                }
                if (participanteFire == "jugador1") {
                    juegoActualEscribir.update("participanteactual", "jugador2")
                } else {
                    juegoActualEscribir.update("participanteactual", "jugador1")
                }
            } else {
                juegoActualEscribir.get().addOnSuccessListener { document ->
                    participante2 =
                        document.data?.get("participante2") as Boolean
                }
            }
        }
        eight.setOnClickListener {
            if (participante1 && participante2 && participanteFire == participante) {
                if (mBoard[8] == '9') {
                    callFunction(animationScale, it, eight, 8)
                    if (!turn) {
                        juegoActualEscribir.update("posicion9", "X")
                    } else {
                        juegoActualEscribir.update("posicion9", "O")
                    }
                }
                if (participanteFire == "jugador1") {
                    juegoActualEscribir.update("participanteactual", "jugador2")
                } else {
                    juegoActualEscribir.update("participanteactual", "jugador1")
                }
            } else {
                juegoActualEscribir.get().addOnSuccessListener { document ->
                    participante2 =
                        document.data?.get("participante2") as Boolean
                }
            }
        }
        reset.setOnClickListener {
            mBoard = charArrayOf('1', '2', '3', '4', '5', '6', '7', '8', '9')
            zero.background = resources.getDrawable(R.drawable.vacio)
            one.background = resources.getDrawable(R.drawable.vacio)
            two.background = resources.getDrawable(R.drawable.vacio)
            three.background = resources.getDrawable(R.drawable.vacio)
            four.background = resources.getDrawable(R.drawable.vacio)
            five.background = resources.getDrawable(R.drawable.vacio)
            six.background = resources.getDrawable(R.drawable.vacio)
            seven.background = resources.getDrawable(R.drawable.vacio)
            eight.background = resources.getDrawable(R.drawable.vacio)
            juegoActualEscribir.update("posicion1", "")
            juegoActualEscribir.update("posicion2", "")
            juegoActualEscribir.update("posicion3", "")
            juegoActualEscribir.update("posicion4", "")
            juegoActualEscribir.update("posicion5", "")
            juegoActualEscribir.update("posicion6", "")
            juegoActualEscribir.update("posicion7", "")
            juegoActualEscribir.update("posicion8", "")
            juegoActualEscribir.update("posicion9", "")
            BOARD_SIZE = 9
            finish = false
            turn = false
        }

        quit.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("¿Desea salir?")
            builder.setPositiveButton("Sí") { dialog, which ->
                juegoActualEscribir.delete()
                    .addOnSuccessListener { onBackPressed() }
                    .addOnFailureListener { e -> Log.w("TAG", "Error deleting document", e) }
            }
            builder.setNegativeButton("No") { dialog, which ->
            }
            builder.show()
        }
    }
}

