package ar.edu.unicen.filmzoom.ui.screens
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unicen.filmzoom.ui.components.detailcomponents.MovieActionButtons
import ar.edu.unicen.filmzoom.ui.components.detailcomponents.MovieCastSection
import ar.edu.unicen.filmzoom.ui.components.detailcomponents.MovieDetailTopBar
import ar.edu.unicen.filmzoom.ui.components.detailcomponents.MovieGenresSection
import ar.edu.unicen.filmzoom.ui.components.detailcomponents.MovieHeaderSection
import ar.edu.unicen.filmzoom.ui.components.detailcomponents.MovieSynopsisCard
import ar.edu.unicen.filmzoom.ui.theme.FilmZoomTheme

@Composable
fun MovieDetailScreen(
    onBackClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onWishlistClick: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        // Vinculamos el color de fondo del Scaffold al tema para que cambie entre claro y oscuro correctamente
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            MovieDetailTopBar(
                title = "Detalle Película",
                onBackClick = onBackClick,
                onShareClick = onShareClick,
                onProfileClick = onProfileClick
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // 1. Cabecera (Banner + Póster superpuesto + Título + Rating)
            item {
                MovieHeaderSection()
            }

            // 2. Botones de acción principales (Deseados, Web, Compartir)
            item {
                MovieActionButtons(
                    onWishlistClick = onWishlistClick,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // 3. Géneros (Chips horizontales)
            item {
                MovieGenresSection(
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // 4. Sinopsis y Director
            item {
                MovieSynopsisCard(
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // 5. Reparto principal
            item {
                MovieCastSection(
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun MovieDetailScreenLightPreview() {
    FilmZoomTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            MovieDetailScreen()
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun MovieDetailScreenDarkPreview() {
    FilmZoomTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            MovieDetailScreen()
        }
    }
}