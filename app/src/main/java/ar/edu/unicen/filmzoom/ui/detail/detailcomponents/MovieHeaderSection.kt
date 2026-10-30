package ar.edu.unicen.filmzoom.ui.detail.detailcomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unicen.filmzoom.R
import ar.edu.unicen.filmzoom.domain.model.MovieDetails
import ar.edu.unicen.filmzoom.ui.components.RatingChip
import ar.edu.unicen.filmzoom.ui.theme.AppSpacing
import ar.edu.unicen.filmzoom.ui.theme.FilmZoomTheme
import coil.compose.AsyncImage

@Composable
fun MovieHeaderSection(
    modifier: Modifier = Modifier,
    detailMovie: MovieDetails?
) {
    // Si la película es nula (está cargando), podemos retornar un contenedor vacío o un placeholder
    if (detailMovie == null) return

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = AppSpacing.small),
    ) {
        // 1. Imagen de fondo superior (Backdrop) con degradado hacia abajo

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            AsyncImage(
                model = detailMovie.backdropPath,
                contentDescription = stringResource(R.string.cd_movie_backdrop, detailMovie.title),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.background.copy(alpha = 0.2f),
                                MaterialTheme.colorScheme.background
                            )
                        )
                    )
            )


        }

        // 2. Fila con el póster superpuesto y los datos principales de la película
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppSpacing.medium)
                .padding(top = 130.dp),
            horizontalArrangement = Arrangement.spacedBy(AppSpacing.medium),
            verticalAlignment = Alignment.Bottom
        ) {
            // Póster flotante
            Surface(
                modifier = Modifier
                    .width(110.dp)
                    .height(160.dp),
                shape = MaterialTheme.shapes.small,
                shadowElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                AsyncImage(
                    model = detailMovie.posterPath,
                    contentDescription = stringResource(R.string.cd_movie_poster, detailMovie.title),
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            // Textos descriptivos (Productora, Título, Año y Duración)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = AppSpacing.extraSmall),
                verticalArrangement = Arrangement.spacedBy(AppSpacing.extraSmall)
            ) {

                RatingChip(detailMovie.voteAverage)


                Text(
                    text = detailMovie.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = stringResource(R.string.detail_movie_metadata_format, detailMovie.releaseDate.take(4), detailMovie.runtime),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun MovieHeaderSectionLightPreview() {
    FilmZoomTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            MovieHeaderSection(
                detailMovie = MovieDetails(
                    id = 1,
                    title = "Inception",
                    backdropPath = "",
                    posterPath = "",
                    releaseDate = "2010",
                    runtime = "148",
                    voteAverage = 8.8,
                    production = "Warner Bros.",
                    overview = "Un ladrón que roba secretos...",
                    homepage = "",
                    genres = emptyList(),
                    director = "Christopher Nolan",
                    cast = emptyList()
                )
            )
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun MovieHeaderSectionDarkPreview() {
    FilmZoomTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            MovieHeaderSection(
                detailMovie = MovieDetails(
                    id = 1,
                    title = "Inception",
                    backdropPath = "",
                    posterPath = "",
                    releaseDate = "2010",
                    runtime = "148",
                    voteAverage = 8.8,
                    production = "Warner Bros.",
                    overview = "Un ladrón que roba secretos...",
                    homepage = "",
                    genres = emptyList(),
                    director = "Christopher Nolan",
                    cast = emptyList()
                )
            )
        }
    }
}