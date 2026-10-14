package ar.edu.unicen.filmzoom.ui.detail.detailcomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unicen.filmzoom.R

@Composable
fun MovieSynopsisCard(
    modifier: Modifier = Modifier,
    overview: String,
    director: String?
) {
    if (overview.isBlank() && director.isNullOrBlank()) return

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
            text = stringResource(R.string.detail_section_synopsis),
            style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // Contenedor de info
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = overview,
                    style = TextStyle(fontSize = 14.sp),
                    color = MaterialTheme.colorScheme.onSurface
                )
                // Fila para el Director (Ícono + Texto)
                if (!director.isNullOrBlank()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Indicador del director
                        Icon(
                            painter = painterResource(R.drawable.ic_film),
                            contentDescription = null,
                            modifier = modifier.size(18.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = stringResource(
                                R.string.detail_director_format,
                                director),
                            style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

//@Preview(name = "Light Mode", showBackground = true)
//@Composable
//fun MovieSynopsisCardLightPreview() {
//    FilmZoomTheme(darkTheme = false) {
//        Surface(color = MaterialTheme.colorScheme.background) {
//            MovieSynopsisCard(modifier = Modifier.padding(16.dp))
//        }
//    }
//}
//
//@Preview(name = "Dark Mode", showBackground = true)
//@Composable
//fun MovieSynopsisCardDarkPreview() {
//    FilmZoomTheme(darkTheme = true) {
//        Surface(color = MaterialTheme.colorScheme.background) {
//            MovieSynopsisCard(modifier = Modifier.padding(16.dp))
//        }
//    }
//}