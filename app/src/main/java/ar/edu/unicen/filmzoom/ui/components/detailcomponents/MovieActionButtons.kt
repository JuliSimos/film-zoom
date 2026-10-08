package ar.edu.unicen.filmzoom.ui.components.detailcomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unicen.filmzoom.ui.theme.FilmZoomTheme

@Composable
fun MovieActionButtons(
    onWishlistClick: (Boolean) -> Unit = {},
    onWebClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    // Estado local para manejar si está en deseados o no
    var isWishlisted by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Botón principal de Agregar a deseados (cambia de estado al hacer clic)
        Button(
            onClick = {
                isWishlisted = !isWishlisted
                onWishlistClick(isWishlisted)
            },
            modifier = Modifier
                .weight(1f)
                .height(48.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isWishlisted) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = if (isWishlisted) "❤️  En tu lista" else "🤍  Agregar a deseados",
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium),
                color = if (isWishlisted) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.onPrimary
            )
        }

        // Botón redondo de sitio web
        Surface(
            modifier = Modifier
                .height(48.dp)
                .width(48.dp),
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            onClick = onWebClick
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "🌐", fontSize = 16.sp)
            }
        }

        // Botón redondo de compartir
        Surface(
            modifier = Modifier
                .height(48.dp)
                .width(48.dp),
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            onClick = onShareClick
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "🔗", fontSize = 16.sp)
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun MovieActionButtonsLightPreview() {
    FilmZoomTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            MovieActionButtons(modifier = Modifier.padding(16.dp))
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun MovieActionButtonsDarkPreview() {
    FilmZoomTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            MovieActionButtons(modifier = Modifier.padding(16.dp))
        }
    }
}