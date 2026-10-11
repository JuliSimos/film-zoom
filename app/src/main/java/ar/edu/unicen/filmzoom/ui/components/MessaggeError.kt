package ar.edu.unicen.filmzoom.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MessaggeError(
    error: String
){

    Text(
        modifier = Modifier
            .padding(8.dp),
        text = error,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.error
    )

}

@Preview(showBackground = true)
@Composable
fun MessaggeErrorPreview() {
    MessaggeError(error = "Ocurrió un error inesperado")
}