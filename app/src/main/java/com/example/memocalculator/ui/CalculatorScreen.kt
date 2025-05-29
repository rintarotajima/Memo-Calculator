package com.example.memocalculator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memocalculator.R

@Composable
fun CalculatorScreen(
    modifier: Modifier = Modifier
) {
    var displayValue by remember { mutableStateOf("0") }

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
    Column(modifier = modifier) {
        CalculationField(
            value = displayValue,
            modifier = Modifier.weight(1f)
        )
        Column(
            modifier = Modifier.weight(4f)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                CalculationButton(
                    label = stringResource(R.string.label_all_clear_button),
                    onClick = { displayValue = "0" },
                    modifier = Modifier.weight(1f)
                )
                CalculationButton(
                    label = stringResource(R.string.label_remainder_button),
                    onClick = {
                        if (displayValue.last() != '+' && displayValue.last() != '-' && displayValue.last() != '×' && displayValue.last() != '÷') {
                            displayValue += "%"
                        } else {
                            displayValue = displayValue.dropLast(1) + "%"
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
                CalculationButton(
                    label = stringResource(R.string.label_division_button),
                    onClick = {
                        if (displayValue.last() != '÷' && displayValue.last() != '×' && displayValue.last() != '-' && displayValue.last() != '+') {
                            displayValue += "÷"
                        } else {
                            displayValue = displayValue.dropLast(1) + "÷"
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                CalculationButton(
                    label = stringResource(R.string.label7_button),
                    onClick = {
                        if (displayValue == "0") {
                            displayValue = "7"
                        } else {
                            displayValue += "7"
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
                CalculationButton(
                    label = stringResource(R.string.label8_button),
                    onClick = {
                        if (displayValue == "0") {
                            displayValue = "8"
                        } else {
                            displayValue += "8"
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
                CalculationButton(
                    label = stringResource(R.string.label9_button),
                    onClick = {
                        if (displayValue == "0") {
                            displayValue = "9"
                        } else {
                            displayValue += "9"
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
                CalculationButton(
                    label = stringResource(R.string.label_multiplication_button),
                    onClick = {
                        if (displayValue.last() != '×' && displayValue.last() != '÷' && displayValue.last() != '-' && displayValue.last() != '+') {
                            displayValue += "×"
                        } else {
                            displayValue = displayValue.dropLast(1) + "×"
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                CalculationButton(
                    label = stringResource(R.string.label4_button),
                    onClick = {
                        if (displayValue == "0") {
                            displayValue = "4"
                        } else {
                            displayValue += "4"
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
                CalculationButton(
                    label = stringResource(R.string.label5_button),
                    onClick = {
                        if (displayValue == "0") {
                            displayValue = "5"
                        } else {
                            displayValue += "5"
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
                CalculationButton(
                    label = stringResource(R.string.label6_button),
                    onClick = {
                        if (displayValue == "0") {
                            displayValue = "6"
                        } else {
                            displayValue += "6"
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
                CalculationButton(
                    label = stringResource(R.string.label_subtraction_button),
                    onClick = {
                        if (displayValue.last() != '-' && displayValue.last() != '+') {
                            displayValue += "-"
                        } else {
                            displayValue = displayValue.dropLast(1) + "-"
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                CalculationButton(
                    label = stringResource(R.string.label1_button),
                    onClick = {
                        if (displayValue == "0") {
                            displayValue = "1"
                        } else {
                            displayValue += "1"
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
                CalculationButton(
                    label = stringResource(R.string.label2_button),
                    onClick = {
                        if (displayValue == "0") {
                            displayValue = "2"
                        } else {
                            displayValue += "2"
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
                CalculationButton(
                    label = stringResource(R.string.label3_button),
                    onClick = {
                        if (displayValue == "0") {
                            displayValue = "3"
                        } else {
                            displayValue += "3"
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
                CalculationButton(
                    label = stringResource(R.string.label_addition_button),
                    onClick = {
                        if (displayValue.last() != '+' && displayValue.last() != '-' && displayValue.last() != '×' && displayValue.last() != '÷') {
                            displayValue += "+"
                        } else {
                            displayValue = displayValue.dropLast(1) + "+"
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                CalculationButton(
                    label = stringResource(R.string.label0_button),
                    onClick = {
                        if (displayValue == "0") {
                            displayValue = "0"
                        } else {
                            displayValue += "0"
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
                CalculationButton(
                    label = stringResource(R.string.label_decimal_point_button),
                    onClick = { displayValue += "." },
                    modifier = Modifier.weight(1f)
                )
                CalculationButton(
                    label = "=",
                    onClick = { displayValue = calculate(displayValue) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun CalculationField(
    value: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(2.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Text(
            text = value,
            fontSize = 48.sp,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Composable
fun CalculationButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonColors(
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.onPrimary,
            MaterialTheme.colorScheme.onSecondary
        ),
        modifier = modifier
            .size(132.dp)
            .padding(4.dp)
    ) {
        Text(
            text = label,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
    }
}
