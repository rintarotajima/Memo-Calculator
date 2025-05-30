package com.example.memocalculator.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CalcViewModel : ViewModel()  {
    private val _uiState = MutableStateFlow(CalcUiState())
    val uiState: StateFlow<CalcUiState> = _uiState.asStateFlow()

    // 数字ボタンが押された時の処理
    fun onDigitClick(digit: String) {
        val currentDisplay = _uiState.value.displayValue
        val newDisplay = if (currentDisplay == "0") {
            digit
        } else {
            currentDisplay + digit
        }
        _uiState.value = CalcUiState(displayValue = newDisplay)
    }

    // 演算子が押された時の処理
    fun onOperatorClick(operator: String) {
        val currentDisplay = _uiState.value.displayValue
        val lastChar = currentDisplay.lastOrNull()

        val newDisplay = if (lastChar != null && lastChar.isOperator()) {
            currentDisplay.dropLast(1) + operator
        } else {
            currentDisplay + operator
        }
        _uiState.value = CalcUiState(displayValue = newDisplay)
    }

    // 小数点が押された時の処理
    fun onDecimalPointClick() {
        val currentDisplay = _uiState.value.displayValue
        if (!currentDisplay.contains(".")) {
            _uiState.value = CalcUiState(displayValue = "$currentDisplay.")
        } else {
            val lastOperatorIndex = currentDisplay.indexOfLast { it.isOperator() }
            val currentNumberPart = if (lastOperatorIndex != -1) {
                currentDisplay.substring(lastOperatorIndex + 1)
            } else {
                currentDisplay
            }
            if (!currentNumberPart.contains(".")) {
                _uiState.value = CalcUiState(displayValue = "$currentDisplay.")
            }
        }
    }

    // AllClearボタンが押された時の処理
    fun onAllClearClick() {
        _uiState.value = CalcUiState(displayValue = "0")
    }

    // 等号ボタンが押された時の処理
    fun onEqualsClick() {
        val currentDisplay = _uiState.value.displayValue
        val result = calculate(currentDisplay)
        _uiState.value = CalcUiState(displayValue = result)
    }

    // 演算処理
    fun calculate(expression: String): String {
        try {
            val tokens = mutableListOf<String>()
            var currentNumber = StringBuilder()
            for (char in expression) {
                if (char.isDigit() || char == '.') {
                    currentNumber.append(char)
                } else if (char in listOf('+', '-', '×', '÷', '%')) {
                    if (currentNumber.isNotEmpty()) {
                        tokens.add(currentNumber.toString())
                        currentNumber = StringBuilder()
                    }
                    tokens.add(char.toString())
                }
            }
            if (currentNumber.isNotEmpty()) {
                tokens.add(currentNumber.toString())
            }

            val highPriorityOps = listOf("×", "÷", "%")
            val intermediateTokens = mutableListOf<String>()
            var i = 0
            if (tokens.isNotEmpty() && tokens[0] !in highPriorityOps) {
                intermediateTokens.add(tokens[0])
                i = 1
            }
            while (i < tokens.size) {
                val operator = tokens[i]
                if (i + 1 >= tokens.size) return "Error"
                val right = tokens[i + 1]
                if (operator in highPriorityOps) {
                    if (intermediateTokens.isEmpty()) return "Error"
                    val left = intermediateTokens.last().toDouble()
                    val rightValue = right.toDouble()
                    val result = when (operator) {
                        "×" -> left * rightValue
                        "÷" -> left / rightValue
                        "%" -> left % rightValue
                        else -> 0.0
                    }
                    intermediateTokens.removeAt(intermediateTokens.size - 1)
                    intermediateTokens.add(result.toString())
                } else {
                    intermediateTokens.add(operator)
                    intermediateTokens.add(right)
                }
                i += 2
            }

            var result = intermediateTokens[0].toDouble()
            i = 1
            while (i < intermediateTokens.size) {
                val op = intermediateTokens[i]
                if (i + 1 >= intermediateTokens.size) return "Error"
                val right = intermediateTokens[i + 1].toDouble()
                result = when (op) {
                    "+" -> result + right
                    "-" -> result - right
                    else -> return "Error"
                }
                i += 2
            }

            return if (result % 1.0 == 0.0) {
                result.toInt().toString()
            } else {
                result.toString()
            }
        } catch (e: Exception) {
            return "Error"
        }
    }
}

// 演算子があるかないかの判定を行う拡張変数　＊警告文：Condition 'this == '+' always false
fun Char.isOperator() : Boolean {
    return this == '+' || this == '-' || this == '×' || this == '÷' || this == '%'
}
