package com.example.calculator

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

private const val TEXT_VIEW_VALUE = "textViewValue"

class CalculatorFragment : Fragment() {
    private var actionOperation: Operation? = null
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("Create")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.run {
            textView = findViewById(R.id.text_view_result)
            savedInstanceState?.let { data ->
                textView.text = data.getString(TEXT_VIEW_VALUE)
            }
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
                addText(textView, "7")
            }

            btnEight.setOnClickListener {
                addText(textView, "8")
            }

            btnNine.setOnClickListener {
                addText(textView, "9")
            }

            btnDiv.setOnClickListener {
                addText(textView, "/")
            }

            btnFour.setOnClickListener {
                addText(textView, "4")
            }

            btnFive.setOnClickListener {
                addText(textView, "5")
            }

            btnSix.setOnClickListener {
                addText(textView, "6")
            }

            btnMulti.setOnClickListener {
                addText(textView, "*")
            }

            btnOne.setOnClickListener {
                addText(textView, "1")
            }

            btnTwo.setOnClickListener {
                addText(textView, "2")
            }

            btnThree.setOnClickListener {
                addText(textView, "3")
            }

            btnPlus.setOnClickListener {
                addText(textView, "+")
            }

            btnZero.setOnClickListener {
                addText(textView, "0")
            }

            btnPoint.setOnClickListener {
                addText(textView, ".")
            }

            btnClear.setOnClickListener {
                clear(textView)
            }

            btnMinus.setOnClickListener {
                addText(textView, "-")
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(TEXT_VIEW_VALUE, textView.text?.toString())
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