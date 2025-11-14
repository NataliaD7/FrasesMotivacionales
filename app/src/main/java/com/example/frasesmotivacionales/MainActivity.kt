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
    val randomWords = listOf(
        "El éxito es la suma de pequeños esfuerzos repetidos día tras día.",
        "Tu única limitación es tu mente.",
        "Cree en ti y en todo lo que eres.",
        "No te rindas, lo mejor está por venir.",
        "Cada día es una nueva oportunidad.",
        "Los sueños se trabajan, no se esperan.",
        "El fracaso es solo una oportunidad para empezar de nuevo.",
        "Si puedes soñarlo, puedes lograrlo.",
        "Hazlo con pasión o no lo hagas.",
        "El progreso es progreso, no importa cuán pequeño sea.",
        "Convierte tus heridas en sabiduría.",
        "La disciplina tarde o temprano vence al talento.",
        "Persiste hasta que algo suceda.",
        "Tu futuro se crea con lo que haces hoy.",
        "Empieza donde estás, usa lo que tienes, haz lo que puedes.",
        "No mires atrás, no vas por ese camino.",
        "El único modo de hacer un gran trabajo es amar lo que haces.",
        "Lo que hoy parece un sacrificio mañana será tu triunfo.",
        "La constancia es la clave del éxito.",
        "El éxito comienza con la decisión de intentarlo.",
        "Todo logro comienza con la decisión de actuar.",
        "Si fuera fácil, cualquiera lo haría.",
        "Rodéate de personas que te inspiren.",
        "Haz que cada día cuente.",
        "Actitud es igual a altitud.",
        "El momento perfecto es ahora.",
        "Si no te reta, no te cambia.",
        "Tú eres más fuerte de lo que piensas.",
        "Los límites solo existen en tu mente.",
        "Haz más de lo que te hace feliz.",
        "Transforma tus miedos en motivación.",
        "Lo mejor está por construirse.",
        "No esperes oportunidades, créalas.",
        "Tu vida mejora cuando tú mejoras.",
        "Acepta el reto y sigue adelante.",
        "El éxito no es para los que piensan en hacerlo, sino para los que lo hacen.",
        "Jamás te rindas sin haberlo intentado una vez más.",
        "Los grandes cambios requieren grandes esfuerzos.",
        "Donde hay ganas, hay un camino.",
        "El éxito es un viaje, no un destino.",
        "Sueña grande, trabaja duro.",
        "Los resultados tardan, pero llegan.",
        "Nada que valga la pena viene fácil.",
        "Levántate con determinación; acuéstate con satisfacción.",
        "La mejor inversión eres tú.",
        "Para lograr grandes cosas debes actuar.",
        "Siempre hay una manera de mejorar.",
        "Tu actitud define tu dirección.",
        "No tengas miedo de avanzar lento; teme a no avanzar.",
        "A veces un pequeño paso es el inicio de un gran viaje.",
        "Piensa en grande, comienza en pequeño.",
        "Hoy es el día perfecto para empezar.",
        "El éxito está hecho de detalles.",
        "Agradece, aprende y avanza.",
        "Los sueños se construyen con acción.",
        "Nunca es tarde para ser quien quieres ser.",
        "El miedo es temporal, el arrepentimiento es para siempre.",
        "Todo esfuerzo cuenta.",
        "La clave es empezar.",
        "Tu potencial es infinito.",
        "Brilla sin pedir permiso.",
        "Confía en el proceso.",
        "Cada paso te acerca a la meta.",
        "No te compares, tu camino es único.",
        "Trabaja en silencio, deja que el éxito haga ruido.",
        "Tu mejor versión te está esperando.",
        "Con disciplina todo es posible.",
        "El éxito comienza con una mentalidad positiva.",
        "Enfócate en lo que puedes controlar.",
        "La constancia vence lo imposible.",
        "Camina con propósito.",
        "Respira, confía y sigue.",
        "Los límites están para romperse.",
        "Haz que suceda.",
        "No pares hasta estar orgulloso.",
        "Todo gran logro empieza con un pequeño movimiento.",
        "Tu esfuerzo te define.",
        "Persiste incluso cuando sea difícil.",
        "Hoy puedes escribir una nueva historia.",
        "Haz algo que tu futuro yo agradecerá.",
        "Tu energía atrae lo que deseas.",
        "Las oportunidades no ocurren, las creas tú.",
        "Todo es posible cuando crees en ti.",
        "El éxito llega para quienes nunca renuncian.",
        "Cada día es una nueva página.",
        "Lo que haces hoy importa.",
        "Cree que puedes, y estarás a medio camino.",
        "Haz lo necesario hasta que lo posible se vuelva fácil.",
        "No existe éxito sin esfuerzo.",
        "Enfócate en tus metas, no en tus miedos.",
        "Aunque avances lento, sigues avanzando.",
        "Convierte los obstáculos en escalones.",
        "Tu determinación construye tu destino.",
        "Los sueños requieren acción.",
        "Todo cambio comienza contigo.",
        "Hoy elige avanzar.",
        "La vida premia a los valientes."
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
