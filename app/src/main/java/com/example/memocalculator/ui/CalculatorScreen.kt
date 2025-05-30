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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.memocalculator.R

@Composable
fun CalculatorScreen(
    modifier: Modifier = Modifier,
    calcViewModel: CalcViewModel = viewModel()
) {
    val uiState by calcViewModel.uiState.collectAsState()
    val displayValue = uiState.displayValue

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
                    onClick = { calcViewModel.onAllClearClick() },
                    modifier = Modifier.weight(1f)
                )
                CalculationButton(
                    label = stringResource(R.string.label_remainder_button),
                    onClick = { calcViewModel.onOperatorClick("%") },
                    modifier = Modifier.weight(1f)
                )
                CalculationButton(
                    label = stringResource(R.string.label_division_button),
                    onClick = { calcViewModel.onOperatorClick("÷")},
                    modifier = Modifier.weight(1f)
                )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                CalculationButton(
                    label = stringResource(R.string.label7_button),
                    onClick = { calcViewModel.onDigitClick("7")},
                    modifier = Modifier.weight(1f)
                )
                CalculationButton(
                    label = stringResource(R.string.label8_button),
                    onClick = { calcViewModel.onDigitClick("8")},
                    modifier = Modifier.weight(1f)
                )
                CalculationButton(
                    label = stringResource(R.string.label9_button),
                    onClick = { calcViewModel.onDigitClick("9")},
                    modifier = Modifier.weight(1f)
                )
                CalculationButton(
                    label = stringResource(R.string.label_multiplication_button),
                    onClick = { calcViewModel.onOperatorClick("×")},
                    modifier = Modifier.weight(1f)
                )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                CalculationButton(
                    label = stringResource(R.string.label4_button),
                    onClick = { calcViewModel.onDigitClick("4")},
                    modifier = Modifier.weight(1f)
                )
                CalculationButton(
                    label = stringResource(R.string.label5_button),
                    onClick = { calcViewModel.onDigitClick("5")},
                    modifier = Modifier.weight(1f)
                )
                CalculationButton(
                    label = stringResource(R.string.label6_button),
                    onClick = { calcViewModel.onDigitClick("6")},
                    modifier = Modifier.weight(1f)
                )
                CalculationButton(
                    label = stringResource(R.string.label_subtraction_button),
                    onClick = { calcViewModel.onOperatorClick("-")},
                    modifier = Modifier.weight(1f)
                )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                CalculationButton(
                    label = stringResource(R.string.label1_button),
                    onClick = { calcViewModel.onDigitClick("1")},
                    modifier = Modifier.weight(1f)
                )
                CalculationButton(
                    label = stringResource(R.string.label2_button),
                    onClick = { calcViewModel.onDigitClick("2")},
                    modifier = Modifier.weight(1f)
                )
                CalculationButton(
                    label = stringResource(R.string.label3_button),
                    onClick = { calcViewModel.onDigitClick("3")},
                    modifier = Modifier.weight(1f)
                )
                CalculationButton(
                    label = stringResource(R.string.label_addition_button),
                    onClick = { calcViewModel.onOperatorClick("+")},
                    modifier = Modifier.weight(1f)
                )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                CalculationButton(
                    label = stringResource(R.string.label0_button),
                    onClick = { calcViewModel.onDigitClick("0")},
                    modifier = Modifier.weight(1f)
                )
                CalculationButton(
                    label = stringResource(R.string.label_decimal_point_button),
                    onClick = { calcViewModel.onDecimalPointClick() },
                    modifier = Modifier.weight(1f)
                )
                CalculationButton(
                    label = "=",
                    onClick = { calcViewModel.onEqualsClick() },
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
