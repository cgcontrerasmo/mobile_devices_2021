package com.example.triqui

import android.app.AlertDialog
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import java.lang.Math.random
import android.content.DialogInterface




class MainActivity : AppCompatActivity() {
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
    private val dificulty by lazy<TextView> { findViewById(R.id.dificulty) }
    private var turn = false
    private var mBoard = charArrayOf('1', '2', '3', '4', '5', '6', '7', '8', '9')
    lateinit var progressDialog: ProgressDialog
    val HUMAN_PLAYER = 'X'
    val COMPUTER_PLAYER = 'O'
    private var BOARD_SIZE = 9
    private var finish = false
    //private val mRand: Random? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressDialog = ProgressDialog(this)
        val animationScale = AnimationUtils.loadAnimation(this, R.anim.button_choice_small)
        setListeners(animationScale)
    }

    private fun checkForWinner(): Int {
        run {
            var i = 0
            while (i <= 6) {
                if (mBoard[i] == HUMAN_PLAYER && mBoard[i + 1] == HUMAN_PLAYER && mBoard[i + 2] == HUMAN_PLAYER
                ) {
                    Toast.makeText(this, "Ganó el Humano", Toast.LENGTH_LONG).show()
                    finish = true
                    return 2
                }
                if (mBoard[i] == COMPUTER_PLAYER && mBoard[i + 1] == COMPUTER_PLAYER && mBoard[i + 2] == COMPUTER_PLAYER
                ) {
                    Toast.makeText(this, "Ganó la máquina", Toast.LENGTH_LONG).show()
                    finish = true
                    return 3
                }
                i += 3
            }
        }
        for (i in 0..2) {
            if (mBoard[i] == HUMAN_PLAYER && mBoard[i + 3] == HUMAN_PLAYER && mBoard[i + 6] == HUMAN_PLAYER) {
                Toast.makeText(
                    this,
                    "Ganó el Humano",
                    Toast.LENGTH_LONG
                ).show()
                finish = true
                return 2
            }
            if (mBoard[i] == COMPUTER_PLAYER && mBoard[i + 3] == COMPUTER_PLAYER && mBoard[i + 6] == COMPUTER_PLAYER) {
                Toast.makeText(
                    this,
                    "Ganó la máquina",
                    Toast.LENGTH_LONG
                ).show()
                finish = true
                return 3
            }
        }
        if (mBoard[0] == HUMAN_PLAYER && mBoard[4] == HUMAN_PLAYER && mBoard[8] == HUMAN_PLAYER ||
            mBoard[2] == HUMAN_PLAYER && mBoard[4] == HUMAN_PLAYER && mBoard[6] == HUMAN_PLAYER
        ) {
            Toast.makeText(this, "Ganó el Humano", Toast.LENGTH_LONG).show()
            finish = true
            return 2
        }
        if (mBoard[0] == COMPUTER_PLAYER && mBoard[4] == COMPUTER_PLAYER && mBoard[8] == COMPUTER_PLAYER ||
            mBoard[2] == COMPUTER_PLAYER && mBoard[4] == COMPUTER_PLAYER && mBoard[6] == COMPUTER_PLAYER
        ) {
            Toast.makeText(this, "Ganó la máquina", Toast.LENGTH_LONG).show()
            finish = true
            return 3
        }
        if (BOARD_SIZE == 0) {
            Toast.makeText(this, "Empate", Toast.LENGTH_LONG).show()
            finish = true
            return 1
        }
        return 0
    }

    private fun checkForWinnerSimulacion(): Int {
        run {
            var i = 0
            while (i <= 6) {
                if (mBoard[i] == HUMAN_PLAYER && mBoard[i + 1] == HUMAN_PLAYER && mBoard[i + 2] == HUMAN_PLAYER
                ) {
                    return 2
                }
                if (mBoard[i] == COMPUTER_PLAYER && mBoard[i + 1] == COMPUTER_PLAYER && mBoard[i + 2] == COMPUTER_PLAYER
                ) {
                    return 3
                }
                i += 3
            }
        }
        for (i in 0..2) {
            if (mBoard[i] == HUMAN_PLAYER && mBoard[i + 3] == HUMAN_PLAYER && mBoard[i + 6] == HUMAN_PLAYER) {
                return 2
            }
            if (mBoard[i] == COMPUTER_PLAYER && mBoard[i + 3] == COMPUTER_PLAYER && mBoard[i + 6] == COMPUTER_PLAYER) {

                return 3
            }
        }
        if (mBoard[0] == HUMAN_PLAYER && mBoard[4] == HUMAN_PLAYER && mBoard[8] == HUMAN_PLAYER ||
            mBoard[2] == HUMAN_PLAYER && mBoard[4] == HUMAN_PLAYER && mBoard[6] == HUMAN_PLAYER
        ) {
            return 2
        }
        if (mBoard[0] == COMPUTER_PLAYER && mBoard[4] == COMPUTER_PLAYER && mBoard[8] == COMPUTER_PLAYER ||
            mBoard[2] == COMPUTER_PLAYER && mBoard[4] == COMPUTER_PLAYER && mBoard[6] == COMPUTER_PLAYER
        ) {
            return 3
        }
        if (BOARD_SIZE == 0) {
            return 1
        }
        return 0
    }

    private fun setListeners(animationScale: Animation) {
        zero.setOnClickListener {
            if (mBoard[0] == '1') {
                callFunction(animationScale, it, zero, 0)
                getComputerMove()
            }
        }
        one.setOnClickListener {
            if (mBoard[1] == '2') {
                callFunction(animationScale, it, one, 1)
                getComputerMove()
            }
        }
        two.setOnClickListener {
            if (mBoard[2] == '3') {
                callFunction(animationScale, it, two, 2)
                getComputerMove()
            }
        }
        three.setOnClickListener {
            if (mBoard[3] == '4') {
                callFunction(animationScale, it, three, 3)
                getComputerMove()
            }
        }
        four.setOnClickListener {
            if (mBoard[4] == '5') {
                callFunction(animationScale, it, four, 4)
                getComputerMove()
            }
        }
        five.setOnClickListener {
            if (mBoard[5] == '6') {
                callFunction(animationScale, it, five, 5)
                getComputerMove()
            }
        }
        six.setOnClickListener {
            if (mBoard[6] == '7') {
                callFunction(animationScale, it, six, 6)
                getComputerMove()
            }
        }
        seven.setOnClickListener {
            if (mBoard[7] == '8') {
                callFunction(animationScale, it, seven, 7)
                getComputerMove()
            }
        }
        eight.setOnClickListener {
            if (mBoard[8] == '9') {
                callFunction(animationScale, it, eight, 8)
                getComputerMove()
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
            BOARD_SIZE = 9
            finish = false
            turn = false
        }

        quit.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("¿Desea salir?")
            builder.setPositiveButton("Sí") { dialog, which ->
                onBackPressed()
            }
            builder.setNegativeButton("No") { dialog, which ->
            }
            builder.show()
            //builder.setMessage("¿Deseas suscribirte?")
            //
        }

        dificulty.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Seleccione la dificultad")
            val dificulty = arrayOf("Fácil", "Difícil", "Extrema")
            builder.setItems(dificulty,
                DialogInterface.OnClickListener { dialog, which ->
                    when (which) {
                        0->0
                        1->1
                        2->2
                    }
                })
            builder.show()
            //builder.setMessage("¿Deseas suscribirte?")
            //
        }
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

    private fun validarTextView(i: Int): TextView {
        when (i) {
            0 -> return zero
            1 -> return one
            2 -> return two
            3 -> return three
            4 -> return four
            5 -> return five
            6 -> return six
            7 -> return seven
            8 -> return eight
            else -> return zero
        }
    }

    private fun getComputerMove() {
        if (BOARD_SIZE != 0) {
            var move: Int
            val animationScale = AnimationUtils.loadAnimation(this, R.anim.button_choice_small)
            // First see if there's a move O can make to win
            for (i in 0..8) {
                if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) {
                    val curr = mBoard[i]
                    mBoard[i] = COMPUTER_PLAYER
                    if (checkForWinnerSimulacion() == 3) {
                        var textView = validarTextView(i)
                        callFunction(animationScale, textView, textView, i)
                        return
                    } else mBoard[i] = curr
                }
            }

            // See if there's a move O can make to block X from winning
            for (i in 0..8) {
                if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) {
                    val curr = mBoard[i] // Save the current number
                    mBoard[i] = HUMAN_PLAYER
                    if (checkForWinnerSimulacion() == 2) {
                        var textView = validarTextView(i)
                        callFunction(animationScale, textView, textView, i)
                        return
                    } else mBoard[i] = curr
                }
            }

            // Generate random move
            do {
                var mRand = (random() * 9).toInt()
                move = mRand //.nextInt(BOARD_SIZE)
            } while (mBoard[move] == HUMAN_PLAYER || mBoard[move] == COMPUTER_PLAYER)
            var textView = validarTextView(move)
            callFunction(animationScale, textView, textView, move)
        }
    }
}
