package com.example.taller_7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateBussines : AppCompatActivity() {
    private val BusinessName by lazy<EditText> { findViewById(R.id.BusinessName) }
    private val BusinessURL by lazy<EditText> { findViewById(R.id.BusinessURL) }
    private val BusinessTelephone by lazy<EditText> { findViewById(R.id.BusinessTelephone) }
    private val BusinessEmail by lazy<EditText> { findViewById(R.id.BusinessEmail) }
    private val BusinessProducts by lazy<EditText> { findViewById(R.id.BusinessProducts) }
    private val create by lazy<TextView> { findViewById(R.id.create) }
    private val type by lazy<RadioGroup> { findViewById(R.id.radioButton1) }
    private lateinit var selectedRadioButton: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_bussines)
        val database = AppDatabase.getDatabase(this)
        setListeners(database)
    }

    private fun setListeners(database: AppDatabase) {
        create.setOnClickListener {
            val BusinessName = BusinessName.text.toString()
            val BusinessURL = BusinessURL.text.toString()
            val BusinessTelephone = BusinessTelephone.text.toString()
            val BusinessEmail = BusinessEmail.text.toString()
            val BusinessProducts = BusinessProducts.text.toString()
            /*
            val selectedRadioButtonId: Int = type.checkedRadioButtonId
            lateinit var type: String
            if (selectedRadioButtonId != -1) {
                selectedRadioButton = findViewById(selectedRadioButtonId)
                type = selectedRadioButton.text.toString()
                Log.d("type", type)
            }else{
                type="indefinido"
            }*/
            var type = "ejemplo"
            val bussiness = Business(
                BusinessName,
                BusinessURL,
                BusinessTelephone,
                BusinessEmail,
                BusinessProducts,
                type
            )
            Log.d("elemento", bussiness.toString())
            CoroutineScope(Dispatchers.IO).launch {
                database.business().insertAll(bussiness)
                this@CreateBussines.finish()
            }
        }
    }
}
