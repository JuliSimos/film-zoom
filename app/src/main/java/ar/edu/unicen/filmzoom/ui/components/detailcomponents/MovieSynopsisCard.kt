package ar.edu.unicen.filmzoom.ui.components.detailcomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unicen.filmzoom.ui.theme.FilmZoomTheme

@Composable
fun MovieSynopsisCard(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Título de la sección
        Text(
            text = "SINOPSIS",
            style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold),
        )

        // Contenedor de contenido (sin Surface adicional para mantener el mismo fondo)
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                //Text(text = movie.overview) // O podés cortarla con .take(100) + "..." si querés un resumen corto
                Text(
                    text = "Dom Cobb es un ladrón capaz de adentrarse en" +
                            "los sueños de los demás para robarles sus" +
                            "secretos más ocultos. Ahora tiene la" +
                            "oportunidad de recuperar su vida en una última" +
                            "misión: implantar una idea en lugar de robarla.",
                    style = TextStyle(fontSize = 14.sp),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Indicador del director
                    Text(
                        text = "🎬 Director: Christopher Nolan",
                        style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = "Ficha completa",
                        style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun MovieSynopsisCardLightPreview() {
    FilmZoomTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            MovieSynopsisCard(modifier = Modifier.padding(16.dp))
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun MovieSynopsisCardDarkPreview() {
    FilmZoomTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            MovieSynopsisCard(modifier = Modifier.padding(16.dp))
        }
    }
}