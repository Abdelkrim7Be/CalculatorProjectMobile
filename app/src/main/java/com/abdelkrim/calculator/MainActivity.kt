package com.abdelkrim.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etnumber: EditText
    private lateinit var tvHistory: TextView
    private lateinit var bu0: Button
    private lateinit var bu1: Button
    private lateinit var bu2: Button
    private lateinit var bu3: Button
    private lateinit var bu4: Button
    private lateinit var bu5: Button
    private lateinit var bu6: Button
    private lateinit var bu7: Button
    private lateinit var bu8: Button
    private lateinit var bu9: Button
    private lateinit var budot: Button
    private lateinit var bumul: Button
    private lateinit var budiv: Button
    private lateinit var buplus: Button
    private lateinit var buminus: Button
    private lateinit var buEqual: Button
    private lateinit var buClear: Button

    private var decidot = 1
    private var op = "none"
    private var oldNumber = "0"
    private var newOp = true
    private var finalNumber: Double? = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views using findViewById
        etnumber = findViewById(R.id.etnumber)
        tvHistory = findViewById(R.id.tvHistory)
        bu0 = findViewById(R.id.bu0)
        bu1 = findViewById(R.id.bu1)
        bu2 = findViewById(R.id.bu2)
        bu3 = findViewById(R.id.bu3)
        bu4 = findViewById(R.id.bu4)
        bu5 = findViewById(R.id.bu5)
        bu6 = findViewById(R.id.bu6)
        bu7 = findViewById(R.id.bu7)
        bu8 = findViewById(R.id.bu8)
        bu9 = findViewById(R.id.bu9)
        budot = findViewById(R.id.budot)
        bumul = findViewById(R.id.bumul)
        budiv = findViewById(R.id.budiv)
        buplus = findViewById(R.id.buplus)
        buminus = findViewById(R.id.buminus)
        buEqual = findViewById(R.id.buEqual)
        buClear = findViewById(R.id.buClear)

        // Disable editing on the EditText
        etnumber.isEnabled = false
        etnumber.isFocusable = false
    }

    fun NumberEvent(view: View) {
        try {
            if (newOp) {
                etnumber.setText("")
                decidot = 1
            }
            newOp = false
            val buSelect = view as Button
            var buClickValue: String = etnumber.text.toString()

            when (buSelect.id) {
                bu0.id -> buClickValue += "0"
                bu1.id -> buClickValue += "1"
                bu2.id -> buClickValue += "2"
                bu3.id -> buClickValue += "3"
                bu4.id -> buClickValue += "4"
                bu5.id -> buClickValue += "5"
                bu6.id -> buClickValue += "6"
                bu7.id -> buClickValue += "7"
                bu8.id -> buClickValue += "8"
                bu9.id -> buClickValue += "9"
                budot.id -> {
                    if (decidot == 1) {
                        buClickValue = if (buClickValue == "") "0." else "$buClickValue."
                        decidot = 0
                    }
                }
            }
            etnumber.setText(buClickValue)
            finalNumber = buClickValue.toDouble()

            // Update history
            tvHistory.text = tvHistory.text.toString() + buSelect.text
        } catch (ex: Exception) {
            Toast.makeText(this, ex.message, Toast.LENGTH_LONG).show()
        }
    }

    fun OpEvent(view: View) {
        val buSelect = view as Button
        when (buSelect.id) {
            bumul.id -> op = "*"
            budiv.id -> op = "/"
            buplus.id -> op = "+"
            buminus.id -> op = "-"
        }
        oldNumber = etnumber.text.toString()
        newOp = true
        decidot = 1

        // Update history
        tvHistory.text = tvHistory.text.toString() + " " + buSelect.text + " "
    }

    fun buEqual(view: View) {
        try {
            if (etnumber.text.toString().isEmpty() || etnumber.text.toString() == ".") {
                etnumber.setText("0.0")
            }

            val newNumber = etnumber.text.toString()

            finalNumber = when (op) {
                "*" -> oldNumber.toDouble() * newNumber.toDouble()
                "/" -> oldNumber.toDouble() / newNumber.toDouble()
                "+" -> oldNumber.toDouble() + newNumber.toDouble()
                "-" -> oldNumber.toDouble() - newNumber.toDouble()
                else -> newNumber.toDouble()
            }

            // Display the full calculation inside the input field
            etnumber.setText(finalNumber.toString())

            // Reset values for next calculation
            oldNumber = finalNumber.toString()
            op = "none"
            decidot = 1
            newOp = true

        } catch (ex: Exception) {
            Toast.makeText(this, "Error: ${ex.message}", Toast.LENGTH_LONG).show()
        }
    }


    fun buclear(view: View) {
        etnumber.setText("")
        oldNumber = "0"
        finalNumber = 0.0
        op = "none"
        decidot = 1
        newOp = true
    }

}
