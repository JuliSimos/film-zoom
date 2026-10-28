package ar.edu.unicen.filmzoom.ui.detail.detailcomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unicen.filmzoom.R
import ar.edu.unicen.filmzoom.ui.theme.AppSpacing
import ar.edu.unicen.filmzoom.ui.theme.FilmZoomTheme

@Composable
fun MovieSynopsisCard(
    modifier: Modifier = Modifier,
    overview: String,
    director: String?,
    production: String
) {
    if (overview.isBlank() && director.isNullOrBlank()) return

    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(AppSpacing.small)
    ) {
        // Título de la sección
        Text(
            text = stringResource(R.string.detail_section_synopsis),
            style = MaterialTheme.typography.labelLarge.copy(
                letterSpacing = 0.sp),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        //Informacion
        Text(
            text = overview,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        // Fila para el Director (Ícono + Texto)
        if (!director.isNullOrBlank()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(AppSpacing.small),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Indicador del director
                Icon(
                    painter = painterResource(R.drawable.ic_single_arrow), // seguro cambie esta flecha
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = stringResource(
                        R.string.detail_director),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = director,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AppSpacing.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_single_arrow), // seguro cambie esta flecha
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = stringResource(
                    R.string.detail_production,
                    production),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = production,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun MovieSynopsisCardLightPreview() {
    FilmZoomTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            MovieSynopsisCard(
                modifier = Modifier.padding(16.dp),
                overview = "Esta es una sinopsis de ejemplo para la película.",
                director = "Christopher Nolan",
                production = "fox, hbo"
            )
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun MovieSynopsisCardDarkPreview() {
    FilmZoomTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            MovieSynopsisCard(
                modifier = Modifier.padding(16.dp),
                overview = "La Odisea (conocida originalmente en inglés como The Odyssey) es una película épica de acción británica-estadounidense, escrita, dirigida y producida por Christopher Nolan. Está basada en el poema griego homónimo de Homero y protagonizada por Matt Damon en el papel de Odiseo, el rey griego de Ítaca. Narra su largo y peligroso viaje de regreso a casa tras la guerra de Troya, así como sus encuentros con seres mitológicos mientras intenta reunirse con su esposa, Penélope, interpretada por Anne Hathaway. El elenco coral incluye a Tom Holland, Robert Pattinson, Lupita Nyong’o, Samantha Morton, Zendaya, Charlize Theron, Himesh Patel y Jon Bernthal. Nolan y su esposa, Emma Thomas, produjeron la película a través de su compañía Syncopy.",
                director = "Christopher Nolan",
                production = "fox, hbo"

            )
        }
    }
}