package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private var actionOperation: Operation? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val resultTextView: TextView = findViewById(R.id.text_view_result)
        val btnSeven: Button = findViewById(R.id.button_seven)
        val btnEight: Button = findViewById(R.id.button_eight)
        val btnNine: Button = findViewById(R.id.button_nine)
        val btnDiv: Button = findViewById(R.id.button_div)
        val btnFour: Button = findViewById(R.id.button_four)
        val btnFive: Button = findViewById(R.id.button_five)
        val btnSix: Button = findViewById(R.id.button_six)
        val btnMulti: Button = findViewById(R.id.button_multi)
        val btnOne: Button = findViewById(R.id.button_one)
        val btnTwo: Button = findViewById(R.id.button_two)
        val btnThree: Button = findViewById(R.id.button_three)
        val btnPlus: Button = findViewById(R.id.button_plus)
        val btnZero: Button = findViewById(R.id.button_zero)
        val btnPoint: Button = findViewById(R.id.button_point)
        val btnClear: Button = findViewById(R.id.button_clear)
        val btnMinus: Button = findViewById(R.id.button_minus)

        btnSeven.setOnClickListener {
            addText(resultTextView, "7")
        }

        btnEight.setOnClickListener {
            addText(resultTextView, "8")
        }

        btnNine.setOnClickListener {
            addText(resultTextView, "9")
        }

        btnDiv.setOnClickListener {
            addText(resultTextView, "/")
        }

        btnFour.setOnClickListener {
            addText(resultTextView, "4")
        }

        btnFive.setOnClickListener {
            addText(resultTextView, "5")
        }

        btnSix.setOnClickListener {
            addText(resultTextView, "6")
        }

        btnMulti.setOnClickListener {
            addText(resultTextView, "*")
        }

        btnOne.setOnClickListener {
            addText(resultTextView, "1")
        }

        btnTwo.setOnClickListener {
            addText(resultTextView, "2")
        }

        btnThree.setOnClickListener {
            addText(resultTextView, "3")
        }

        btnPlus.setOnClickListener {
            addText(resultTextView, "+")
        }

        btnZero.setOnClickListener {
            addText(resultTextView, "0")
        }

        btnPoint.setOnClickListener {
            addText(resultTextView, ".")
        }

        btnClear.setOnClickListener {
            clear(resultTextView)
        }

        btnMinus.setOnClickListener {
            addText(resultTextView, "-")
        }
    }

    private fun addText(tv: TextView, text: String) {
        val tvText = tv.text.toString()
        if (actionOperation == null && tvText.isNotEmpty()) {
            actionOperation = selectOperation(text)
        } else if (isOperation(text)) {
            val sBeforeOperation = actionOperation?.let {
                Operation.getOperation(it)
            }
            if (sBeforeOperation?.let { operationIsLastSymbol(tvText, it) } == true) {
                tv.text = tv.text.toString().replace(sBeforeOperation, text)
                actionOperation = selectOperation(text)
                return
            } else {
                actionOperation?.let {
                    val nextOperation = selectOperation(text)
                    if (nextOperation != null) {
                        tv.action(it, nextOperation)
                    }
                }
                return
            }
        }

        if (text == ".") {
            val pointCount = tvText.countSymbol('.')
            if (pointCount == 2 || (pointCount == 1 && actionOperation == null)) return

            if (tvText.isEmpty()) {
                tv.text = "0${text}"
                return
            } else if (actionOperation != null && !tvText.last().isDigit()) {
                tv.text = "${tvText}0${text}"
                return
            }
        }
        tv.text = "${tvText}${text}"
    }

    private fun clear(tv: TextView) {
        tv.text = ""
        actionOperation = null
    }

    private fun operationIsLastSymbol(text: String, operation: String): Boolean =
        isOperation(operation) && text.lastIndex == text.indexOf(operation)

    private fun isOperation(text: String): Boolean = Operation.operations.contains(text)

    private fun TextView.action(beforeOperation: Operation, nextOperation: Operation) {
        actionOperation = nextOperation
        when (beforeOperation) {
            Operation.PLUS -> beforeOperation.plus(this, nextOperation)
            Operation.MINUS -> beforeOperation.minus(this, nextOperation)
            Operation.MULTI -> beforeOperation.multi(this, nextOperation)
            Operation.DIV -> beforeOperation.div(this, nextOperation)
        }
    }

    private fun selectOperation(symbol: String): Operation? {
        return when (symbol) {
            "+" -> Operation.PLUS
            "-" -> Operation.MINUS
            "*" -> Operation.MULTI
            "/" -> Operation.DIV
            else -> null
        }
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