package com.example.triqui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import java.lang.Math.random

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
    private var zeroState = false
    private var oneState = false
    private var twoState = false
    private var threeState = false
    private var fourState = false
    private var fiveState = false
    private var sixState = false
    private var sevenState = false
    private var eightState = false
    private var turn = false
    private var numero = (random() * 10 + 1).toInt()
    private val mBoard = charArrayOf('1', '2', '3', '4', '5', '6', '7', '8', '9')
    val HUMAN_PLAYER = 'X'
    val COMPUTER_PLAYER = 'O'
    private var BOARD_SIZE = 9
    private var finish = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val animationScale = AnimationUtils.loadAnimation(this, R.anim.button_choice_small)
        setListeners(animationScale)
    }

    // Check for a winner.  Return
    //  0 if no winner or tie yet
    //  1 if it's a tie
    //  2 if X won
    //  3 if O won


    private fun checkForWinner() {
        run {
            var i = 0
            while (i <= 6) {
                if (mBoard[i] === HUMAN_PLAYER && mBoard[i + 1] === HUMAN_PLAYER && mBoard[i + 2] === HUMAN_PLAYER
                ) {
                    Toast.makeText(this, "Ganó el Humano", Toast.LENGTH_LONG).show()
                    finish = true
                }
                if (mBoard[i] === COMPUTER_PLAYER && mBoard[i + 1] === COMPUTER_PLAYER && mBoard[i + 2] === COMPUTER_PLAYER
                ) {
                    Toast.makeText(this, "Ganó la máquina", Toast.LENGTH_LONG).show()
                    finish = true
                }
                i += 3
            }
        }
        for (i in 0..2) {
            if (mBoard[i] === HUMAN_PLAYER && mBoard[i + 3] === HUMAN_PLAYER && mBoard[i + 6] === HUMAN_PLAYER) {
                Toast.makeText(
                    this,
                    "Ganó el Humano",
                    Toast.LENGTH_LONG
                ).show()
                finish = true
            }
            if (mBoard[i] === COMPUTER_PLAYER && mBoard[i + 3] === COMPUTER_PLAYER && mBoard[i + 6] === COMPUTER_PLAYER) {
                Toast.makeText(
                    this,
                    "Ganó la máquina",
                    Toast.LENGTH_LONG
                ).show()
                finish = true
            }
        }
        if (mBoard[0] === HUMAN_PLAYER && mBoard[4] === HUMAN_PLAYER && mBoard[8] === HUMAN_PLAYER ||
            mBoard[2] === HUMAN_PLAYER && mBoard[4] === HUMAN_PLAYER && mBoard[6] === HUMAN_PLAYER
        ) {
            Toast.makeText(this, "Ganó el Humano", Toast.LENGTH_LONG).show()
            finish=true
        }
        if (mBoard[0] === COMPUTER_PLAYER && mBoard[4] === COMPUTER_PLAYER && mBoard[8] === COMPUTER_PLAYER ||
            mBoard[2] === COMPUTER_PLAYER && mBoard[4] === COMPUTER_PLAYER && mBoard[6] === COMPUTER_PLAYER
        ) {
            Toast.makeText(this, "Ganó la máquina", Toast.LENGTH_LONG).show()
            finish=true
        }
        for (i in 0 until BOARD_SIZE) {
            if (mBoard[i] !== HUMAN_PLAYER && mBoard[i] !== COMPUTER_PLAYER) {

            }
        }
        //Toast.makeText(this, "Empate", Toast.LENGTH_LONG).show()
    }

    private fun setListeners(animationScale: Animation) {
        zero.setOnClickListener {
            if (!zeroState && !finish) {
                it.startAnimation(animationScale)
                if (!turn) {
                    zero.background = resources.getDrawable(R.drawable.x)
                    mBoard[0] = HUMAN_PLAYER
                } else {
                    zero.background = resources.getDrawable(R.drawable.o)
                    mBoard[0] = COMPUTER_PLAYER
                }
                zeroState = true
                turn = !turn
                BOARD_SIZE--
                checkForWinner()
            }
        }
        one.setOnClickListener {
            if (!oneState && !finish) {
                it.startAnimation(animationScale)
                if (!turn) {
                    one.background = resources.getDrawable(R.drawable.x)
                    mBoard[1] = HUMAN_PLAYER
                } else {
                    one.background = resources.getDrawable(R.drawable.o)
                    mBoard[1] = COMPUTER_PLAYER
                }
                oneState = true
                turn = !turn
                BOARD_SIZE--
                checkForWinner()
            }
        }
        two.setOnClickListener {
            if (!twoState && !finish) {
                it.startAnimation(animationScale)
                if (!turn) {
                    two.background = resources.getDrawable(R.drawable.x)
                    mBoard[2] = HUMAN_PLAYER
                } else {
                    two.background = resources.getDrawable(R.drawable.o)
                    mBoard[2] = COMPUTER_PLAYER
                }
                twoState = true
                turn = !turn
                BOARD_SIZE--
                checkForWinner()
            }
        }
        three.setOnClickListener {
            if (!threeState && !finish) {
                it.startAnimation(animationScale)
                if (!turn) {
                    three.background = resources.getDrawable(R.drawable.x)
                    mBoard[3] = HUMAN_PLAYER
                } else {
                    three.background = resources.getDrawable(R.drawable.o)
                    mBoard[3] = COMPUTER_PLAYER
                }
                threeState = true
                turn = !turn
                BOARD_SIZE--
                checkForWinner()
            }
        }
        four.setOnClickListener {
            if (!fourState && !finish) {
                it.startAnimation(animationScale)
                if (!turn) {
                    four.background = resources.getDrawable(R.drawable.x)
                    mBoard[4] = HUMAN_PLAYER
                } else {
                    four.background = resources.getDrawable(R.drawable.o)
                    mBoard[4] = COMPUTER_PLAYER
                }
                fourState = true
                turn = !turn
                BOARD_SIZE--
                checkForWinner()
            }
        }
        five.setOnClickListener {
            if (!fiveState && !finish) {
                it.startAnimation(animationScale)
                if (!turn) {
                    five.background = resources.getDrawable(R.drawable.x)
                    mBoard[5] = HUMAN_PLAYER
                } else {
                    five.background = resources.getDrawable(R.drawable.o)
                    mBoard[5] = COMPUTER_PLAYER
                }
                fiveState = true
                turn = !turn
                BOARD_SIZE--
                checkForWinner()
            }
        }
        six.setOnClickListener {
            if (!sixState && !finish) {
                it.startAnimation(animationScale)
                if (!turn) {
                    six.background = resources.getDrawable(R.drawable.x)
                    mBoard[6] = HUMAN_PLAYER
                } else {
                    six.background = resources.getDrawable(R.drawable.o)
                    mBoard[6] = COMPUTER_PLAYER
                }
                sixState = true
                turn = !turn
                BOARD_SIZE--
                checkForWinner()
            }
        }
        seven.setOnClickListener {
            if (!sevenState && !finish) {
                it.startAnimation(animationScale)
                if (!turn) {
                    seven.background = resources.getDrawable(R.drawable.x)
                    mBoard[7] = HUMAN_PLAYER
                } else {
                    seven.background = resources.getDrawable(R.drawable.o)
                    mBoard[7] = COMPUTER_PLAYER
                }
                sevenState = true
                turn = !turn
                BOARD_SIZE--
                checkForWinner()
            }
        }
        eight.setOnClickListener {
            if (!eightState && !finish) {
                it.startAnimation(animationScale)
                if (!turn) {
                    eight.background = resources.getDrawable(R.drawable.x)
                    mBoard[7] = HUMAN_PLAYER
                } else {
                    eight.background = resources.getDrawable(R.drawable.o)
                    mBoard[7] = COMPUTER_PLAYER
                }
                eightState = true
                turn = !turn
                BOARD_SIZE--
                checkForWinner()
            }
        }
    }

    private fun press() {

    }
}