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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unicen.filmzoom.R
import ar.edu.unicen.filmzoom.domain.model.Movie
import ar.edu.unicen.filmzoom.ui.components.RatingChip
import ar.edu.unicen.filmzoom.ui.theme.AppSpacing
import ar.edu.unicen.filmzoom.ui.theme.FilmZoomTheme


@Composable
fun MovieCard(
    modifier: Modifier,
    movie : Movie,
    onMovieClicked: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .semantics(mergeDescendants = true) {} // Fusiona el texto para TalkBack en un solo anuncio claro
            .clickable { onMovieClicked() },
        shape = MaterialTheme.shapes.small,
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
            // PÓSTER DE LA PELÍCULA CON CONTENEDOR
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier
                    .width(90.dp)
                    .height(130.dp)
            ) {
                AsyncImage(
                    model = movie.posterPath,
                    contentDescription = stringResource(R.string.cd_movie_poster, movie.title),
                    modifier = Modifier,
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(AppSpacing.small))

            // COLUMNA DE INFORMACIÓN
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(AppSpacing.small),
                verticalArrangement = Arrangement.spacedBy(AppSpacing.extraSmall)
            ) {
                // TÍTULO
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                // FILA INFERIOR: AÑO Y RATING
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(AppSpacing.small)
                ) {
                    // Año
                    if (movie.releaseDate.length >= 4) {
                        Text(
                            text = movie.releaseDate.take(4),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    RatingChip(movie.voteAverage)
                }
            }

            // FLECHA A LA DERECHA
            Icon(
                painter = painterResource(id = R.drawable.ic_single_arrow),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = AppSpacing.small)
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
                modifier = Modifier,
                movie = Movie(
                    id = 1,
                    title = "Inception",
                    posterPath = "/edv5bs1qYwqvqyJ6G2T2oQ1HQC8.jpg",
                    releaseDate = "2010-07-15",
                    voteAverage = 8.8,
                ),
                onMovieClicked = {},
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
                    modifier = Modifier,
                    movie = Movie(
                        id = 1,
                        title = "The Lord of the Rings: The Fellowship of the Ring",
                        posterPath = "/edv5bs1qYwqvqyJ6G2T2oQ1HQC8.jpg",
                        releaseDate = "2010-07-15",
                        voteAverage = 8.8,
                ),
                onMovieClicked = {}
            )
        }
    }
}