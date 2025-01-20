package com.example.cambio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConverterApp()
        }
    }
}

@Composable
fun CurrencyConverterApp() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagem de dinheiro
            Image(
                painter = painterResource(id = R.drawable.ic_money), // Substitua por sua imagem
                contentDescription = stringResource(id = R.string.money_image_description),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
                // Campo para inserção do valor
                var inputValue by remember { mutableStateOf("") }
                BasicTextField(
                    value = inputValue,
                    onValueChange = { inputValue = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .background(Color.LightGray),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (inputValue.isEmpty()) Text(
                                stringResource(id = R.string.enter_value_hint),
                                color = Color.Gray
                            )
                            innerTextField()
                        }
                    }
               )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de seleção de conversão

            val conversionOptions = listOf(
                stringResource(id = R.string.euro_to_real),
                stringResource(id = R.string.real_to_euro),
                stringResource(id = R.string.dollar_to_real),
                stringResource(id = R.string.dollar_to_euro)
            )
            var selectedConversion by remember { mutableStateOf(conversionOptions.first()) }
            DropdownMenuField(
                options = conversionOptions,
                selectedOption = selectedConversion,
                onOptionSelected = { selectedConversion = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo para exibição do resultado da conversão
            Text(
                text = stringResource(id = R.string.result_text, (inputValue.toDoubleOrNull() ?: 0.0) * 5.0),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Lista de últimas cotações
            Text(
                text = stringResource(id = R.string.latest_quotes_title),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(6) { index ->
                    val label = if (index < 3) stringResource(id = R.string.euro_label) else stringResource(id = R.string.dollar_label)
                    val value = when (index) {
                        0 -> "6.55"
                        1 -> "6.58"
                        2 -> "6.50"
                        3 -> "6.10"
                        4 -> "6.06"
                        else -> "6.11"
                    }
                    Text(
                        text = stringResource(id = R.string.quote_item, label, value),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DropdownMenuField(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        Button(onClick = { expanded = true }) {
            Text(text = selectedOption)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                    text = { Text(option) }
                )
            }
        }
    }
}
