package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_layout, CalculatorFragment())
            .commit()
    }
}

enum class Operation {
    PLUS, MINUS, DIV, MULTI;

    companion object {
        val operations = listOf("+", "-", "/", "*")
        fun getOperation(operation: Operation?): String {
            return when(operation) {
                PLUS -> "+"
                MINUS -> "-"
                MULTI -> "*"
                DIV -> "/"
                else -> null.toString()
            }
        }
    }

    fun plus(textView: TextView, operation: Operation) {
        val numbers = getNumbersFromTextView(textView, this)

        if (numbers != null) {
            textView.text =
                "${numbers[0].toDouble() + numbers[1].toDouble()}${getOperation(operation)}"
        }
    }

    fun minus(textView: TextView, operation: Operation) {
        val numbers = getNumbersFromTextView(textView, this)

        if (numbers != null) {
            textView.text =
                "${numbers[0].toDouble() - numbers[1].toDouble()}${getOperation(operation)}"
        }
    }

    fun multi(textView: TextView, operation: Operation) {
        val numbers = getNumbersFromTextView(textView, this)

        if (numbers != null) {
            textView.text =
                "${numbers[0].toDouble() * numbers[1].toDouble()}${getOperation(operation)}"
        }
    }

    fun div(textView: TextView, operation: Operation) {
        val numbers = getNumbersFromTextView(textView, this)

        if (numbers != null) {
            if (numbers[1] == "0") return

            textView.text =
                "${numbers[0].toDouble() / numbers[1].toDouble()}${getOperation(operation)}"
        }
    }

    private fun getNumbersFromTextView(textView: TextView, operation: Operation): List<String>? {
        val sOperation = getOperation(operation)

        val numbers = textView.text.toString().split(sOperation)
        for (number in numbers) {
            if (number.isEmpty()) return null
        }

        return numbers
    }
}

fun String.countSymbol(symbol: Char): Int {
    if (this.isEmpty()) return 0

    var count = 0

    for (char in this) {
        if (char == symbol) {
            count++
        }
    }

    return count
}