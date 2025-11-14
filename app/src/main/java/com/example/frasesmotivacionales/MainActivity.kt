package com.example.frasesmotivacionales

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextAlign
import com.example.frasesmotivacionales.ui.theme.ExampleTheme
import kotlinx.coroutines.Job

/*
* Imports adicionales necesarios (ya incluidos) en el build Gradle de la APP:
* implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android")
* implementation("androidx.lifecycle:lifecycle-viewmodel-compose")
* */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExampleTheme {
                val messageViewModel: MessageViewModel = viewModel()
                LaunchedEffect(Unit) {
                    messageViewModel.startGeneratingMessage()
                }
                MessageScreen()
            }
        }
    }
}

fun returnRandomMessage(): String {
    // En esta lista colocas los mensajes que me habías mandado
    val randomWords = listOf(
        "Hola",
        "Buenos días",
        "Buenas tardes",
        "Buenas noches",
        "Gatito",
        "Android",
        "No sé qué poner",
        "Osea para colocar, haces así",
        "Otra frase",
        "Otra más",
        "Y otra",
        "Y otra"
    )
    return randomWords.random()
}

class MessageViewModel : ViewModel() {

    private val _message = MutableStateFlow("Mensaje inicial")
    val message: StateFlow<String> get() = _message

    private var job: Job? = null
    var isGenerating by mutableStateOf(true)

    fun startGeneratingMessage() {
        if (job?.isActive == true) return  // Evita duplicar corutinas

        job = viewModelScope.launch {
            while (isGenerating) {
                val newMessage = returnRandomMessage()
                _message.update { newMessage }
                delay(2000)
            }
        }
    }

    fun toggleMessageGeneration() {
        if (isGenerating) {
            isGenerating = false
            job?.cancel()
        } else {
            isGenerating = true
            startGeneratingMessage()
        }
    }
}


@Composable
fun MessageScreen(messageViewModel: MessageViewModel = viewModel()) {
    val message by messageViewModel.message.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { messageViewModel.toggleMessageGeneration() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(text = if (messageViewModel.isGenerating) "Pausar" else "Reanudar",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

// Las preview en Android Studio no permiten las corutinas por lo que
// No se podrá mostrar correctamente el mensaje a menos de que se ejecute la app
// En un emulador o dispositivo físico.
// Se puede forzar el generar otro mensaje dandole al botón de reanudar después de pausarlo.
@Preview(showBackground = true)
@Composable
fun MessageScreenPreview() {
    ExampleTheme {
        val messageViewModel: MessageViewModel = viewModel()
        LaunchedEffect(Unit) {
            messageViewModel.startGeneratingMessage()
        }
        MessageScreen()
    }
}
