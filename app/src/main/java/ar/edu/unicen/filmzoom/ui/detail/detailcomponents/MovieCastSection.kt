package ar.edu.unicen.filmzoom.ui.detail.detailcomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unicen.filmzoom.domain.model.CastMember
import coil.compose.AsyncImage

@Composable
fun MovieCastSection(
    modifier: Modifier = Modifier,
    cast : List<CastMember>?
) {
    if (cast.isNullOrEmpty()) return

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Cabecera de la sección ("REPARTO PRINCIPAL", contador y "Ver todo >")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "REPARTO PRINCIPAL",
                    style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                // Badge de cantidad de roles
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = cast.size.toString(),
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        style = TextStyle(fontSize = 10.sp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Text(
                text = "Ver todo >",
                style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium),
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Lista horizontal de actores
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(cast.take(10)) { actor ->
                CastMemberItem(actor = actor)
            }

        }
    }
}

@Composable
fun CastMemberItem(
    actor: CastMember
) {
    Column(
        modifier = Modifier.width(72.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Círculo simulando la foto del actor
        Surface(
            modifier = Modifier
                .size(64.dp) // Ancho y alto iguales para garantizar un círculo perfecto
                .padding(vertical = 4.dp),
            shape = RoundedCornerShape(50),
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            AsyncImage(
                model = actor.profilePath,
                contentDescription = "foto de ${actor.name}",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop // Recorta la imagen para llenar el círculo sin deformarse
            )
        }

        Text(
            text = actor.name,
            style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = actor.character,
            style = TextStyle(fontSize = 10.sp),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

//@Preview(name = "Light Mode", showBackground = true)
//@Composable
//fun MovieCastSectionLightPreview() {
//    FilmZoomTheme(darkTheme = false) {
//        Surface(color = MaterialTheme.colorScheme.background) {
//            MovieCastSection(modifier = Modifier.padding(16.dp))
//        }
//    }
//}
//
//@Preview(name = "Dark Mode", showBackground = true)
//@Composable
//fun MovieCastSectionDarkPreview() {
//    FilmZoomTheme(darkTheme = true) {
//        Surface(color = MaterialTheme.colorScheme.background) {
//            MovieCastSection(modifier = Modifier.padding(16.dp))
//        }
//    }
//}