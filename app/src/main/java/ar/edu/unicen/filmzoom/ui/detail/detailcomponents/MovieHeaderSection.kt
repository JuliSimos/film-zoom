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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unicen.filmzoom.R
import ar.edu.unicen.filmzoom.domain.model.MovieDetails
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
            .padding(bottom = 8.dp),
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
                modifier = Modifier
                    .fillMaxSize(),
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
                .padding(horizontal = 16.dp)
                .padding(top = 130.dp), // Empuja la fila hacia abajo para que el póster pise el banner
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            // Póster flotante
            Surface(
                modifier = Modifier
                    .width(110.dp)
                    .height(160.dp),
                shape = RoundedCornerShape(12.dp),
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
                    .padding(bottom = 4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                Text(
                        text = detailMovie.production,
                        style = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    // Badge de calificación (Estrella amarilla y puntaje) arriba a la derecha
                    Surface(
                        modifier = Modifier
                            .padding(4.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFF3D086) // Color amarillo suave de la captura
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "★", fontSize = 14.sp, color = Color.Black)
                            Text(
                                text = detailMovie.voteAverage.toString().take(3),
                                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold),
                                color = Color.Black
                            )
                        }
                    }
                }

                Text(
                    text = detailMovie.title,
                    style = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = stringResource(R.string.detail_movie_metadata_format, detailMovie.releaseDate.take(4), detailMovie.runtime),
                    style = TextStyle(fontSize = 13.sp),
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