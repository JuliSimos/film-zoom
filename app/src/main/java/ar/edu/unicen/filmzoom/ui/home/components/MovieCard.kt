package ar.edu.unicen.filmzoom.ui.home.components

import coil.compose.AsyncImage
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unicen.filmzoom.R
import ar.edu.unicen.filmzoom.domain.model.Movie
import ar.edu.unicen.filmzoom.ui.theme.FilmZoomTheme
import ar.edu.unicen.filmzoom.ui.theme.RatingStarLight

@Composable
fun MovieCard(
    movie : Movie,
    onMovieClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onMovieClicked() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // PÓSTER DE LA PELÍCULA CON CONTENEDOR VIGENTE
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier
                    .width(90.dp)
                    .height(130.dp)
            ) {
                AsyncImage(
                    model = movie.posterPath, //no me gusta dejar una url absoluta
                    contentDescription = "Póster de ${movie.title}",
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(RoundedCornerShape(5.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // COLUMNA DE INFORMACIÓN
            Column(
                modifier = Modifier.weight(1f)
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)

                ) {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface // Adapta texto primario
                    )

                    // Chip Rating
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = RatingStarLight, // #FFB300
                                modifier = Modifier.size(16.dp)
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            Text(
                                text = movie.voteAverage.toString().take(3),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
                // TÍTULO

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Año
                    Text(
                        text = movie.releaseDate.take(4),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant // Texto secundario
                    )
                }

            }

            // FLECHA A LA DERECHA
            Icon(
                painter = painterResource(id = R.drawable.ic_single_arrow),
                contentDescription = "Ver detalles",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 4.dp)
            )
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun MovieCardLightPreview() {
    FilmZoomTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            MovieCard(
                movie = Movie(
                    id = 1,
                    title = "Inception",
                    posterPath = "/edv5bs1qYwqvqyJ6G2T2oQ1HQC8.jpg",
                    releaseDate = "2010-07-15",
                    voteAverage = 8.8,
                    runtime = 148
                ),
                onMovieClicked = {}
            )
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun MovieCardDarkPreview() {
    FilmZoomTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
                MovieCard(
                    movie = Movie(
                        id = 1,
                        title = "Inception",
                        posterPath = "/edv5bs1qYwqvqyJ6G2T2oQ1HQC8.jpg",
                        releaseDate = "2010-07-15",
                        voteAverage = 8.8,
                        runtime = 148
                ),
                onMovieClicked = {}
            )
        }
    }
}