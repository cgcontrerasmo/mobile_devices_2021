package com.example.triqui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
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
    private val BOARD_SIZE = 9

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val animationScale = AnimationUtils.loadAnimation(this, R.anim.button_choice_small)
        setListeners(animationScale)
    }

    private fun setListeners(animationScale: Animation) {
        zero.setOnClickListener {
            if (!zeroState) {
                it.startAnimation(animationScale)
                if (!turn) zero.background = resources.getDrawable(R.drawable.x)
                else zero.background = resources.getDrawable(R.drawable.o)
                zeroState = true
                turn = !turn
            }
        }
        one.setOnClickListener {
            if (!oneState) {
                it.startAnimation(animationScale)
                if (!turn) one.background = resources.getDrawable(R.drawable.x)
                else one.background = resources.getDrawable(R.drawable.o)
                oneState = true
                turn = !turn
            }
        }
        two.setOnClickListener {
            if (!twoState) {
                it.startAnimation(animationScale)
                if (!turn) two.background = resources.getDrawable(R.drawable.x)
                else two.background = resources.getDrawable(R.drawable.o)
                twoState = true
                turn = !turn
            }
        }
        three.setOnClickListener {
            if (!threeState) {
                it.startAnimation(animationScale)
                if (!turn) three.background = resources.getDrawable(R.drawable.x)
                else three.background = resources.getDrawable(R.drawable.o)
                threeState = true
                turn = !turn
            }
        }
        four.setOnClickListener {
            if (!fourState) {
                it.startAnimation(animationScale)
                if (!turn) four.background = resources.getDrawable(R.drawable.x)
                else four.background = resources.getDrawable(R.drawable.o)
                fourState = true
                turn = !turn
            }
        }
        five.setOnClickListener {
            if (!fiveState) {
                it.startAnimation(animationScale)
                if (!turn) five.background = resources.getDrawable(R.drawable.x)
                else five.background = resources.getDrawable(R.drawable.o)
                fiveState = true
                turn = !turn
            }
        }
        six.setOnClickListener {
            if (!sixState) {
                it.startAnimation(animationScale)
                if (!turn) six.background = resources.getDrawable(R.drawable.x)
                else six.background = resources.getDrawable(R.drawable.o)
                sixState = true
                turn = !turn
            }
        }
        seven.setOnClickListener {
            if (!sevenState) {
                it.startAnimation(animationScale)
                if (!turn)
                    seven.background = resources.getDrawable(R.drawable.x)
                else seven.background = resources.getDrawable(R.drawable.o)
                sevenState = true
                turn = !turn
            }
        }
        eight.setOnClickListener {
            if (!eightState) {
                it.startAnimation(animationScale)
                if (!turn) eight.background = resources.getDrawable(R.drawable.x)
                else eight.background = resources.getDrawable(R.drawable.o)
                eightState = true
                turn = !turn
            }
        }
    }

    private fun press(){

    }
}