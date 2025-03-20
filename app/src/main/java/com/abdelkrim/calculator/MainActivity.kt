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

    private var currentInput = "0"
    private var currentOperation: String? = null
    private var lastNumber: Double = 0.0
    private var shouldResetInput = false

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
        buEqual = findViewById(R.id.buequal)
        buClear = findViewById(R.id.buclear)

        // Disable editing on the EditText
        etnumber.isEnabled = false
        etnumber.isFocusable = false
    }

    fun NumberEvent(view: View) {
        val digit = (view as Button).text.toString()
        
        if (shouldResetInput) {
            currentInput = digit
            shouldResetInput = false
        } else {
            if (currentInput == "0" && digit != ".") {
                currentInput = digit
            } else if (digit == "." && !currentInput.contains(".")) {
                currentInput += digit
            } else if (digit != ".") {
                currentInput += digit
            }
        }
        updateDisplay()
    }

    fun OpEvent(view: View) {
        val newOp = when ((view as Button).id) {
            R.id.bumul -> "*"
            R.id.budiv -> "/"
            R.id.buplus -> "+"
            R.id.buminus -> "-"
            else -> return
        }

        if (!shouldResetInput) {
            if (currentOperation != null) {
                calculate()
            }
            lastNumber = currentInput.toDouble()
        }
        currentOperation = newOp
        shouldResetInput = true
    }

    private fun calculate() {
        if (currentOperation == null) return
        
        val current = currentInput.toDouble()
        val result = when (currentOperation) {
            "+" -> lastNumber + current
            "-" -> lastNumber - current
            "*" -> lastNumber * current
            "/" -> if (current != 0.0) lastNumber / current else {
                Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT).show()
                return
            }
            else -> return
        }
        
        currentInput = formatResult(result)
        updateDisplay()
    }

    fun buEqual(view: View) {
        calculate()
        currentOperation = null
        shouldResetInput = true
    }

    private fun formatResult(number: Double): String {
        return if (number % 1 == 0.0) {
            number.toLong().toString()
        } else {
            "%.8f".format(number).trimEnd('0').trimEnd('.')
        }
    }

    private fun updateDisplay() {
        etnumber.setText(currentInput)
    }

    fun buclear(view: View) {
        currentInput = "0"
        currentOperation = null
        lastNumber = 0.0
        shouldResetInput = false
        updateDisplay()
    }

    fun signChange(view: View) {
        if (currentInput != "0") {
            currentInput = if (currentInput.startsWith("-")) {
                currentInput.substring(1)
            } else {
                "-$currentInput"
            }
            updateDisplay()
        }
    }

    fun bupercent(view: View) {
        val value = currentInput.toDouble() / 100
        currentInput = formatResult(value)
        updateDisplay()
    }

}
