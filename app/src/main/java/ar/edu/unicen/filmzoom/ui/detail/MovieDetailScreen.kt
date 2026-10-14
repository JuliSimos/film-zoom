package ar.edu.unicen.filmzoom.ui.detail
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import ar.edu.unicen.filmzoom.ui.detail.detailcomponents.MovieActionButtons
import ar.edu.unicen.filmzoom.ui.detail.detailcomponents.MovieCastSection
import ar.edu.unicen.filmzoom.ui.detail.detailcomponents.MovieDetailTopBar
import ar.edu.unicen.filmzoom.ui.detail.detailcomponents.MovieGenresSection
import ar.edu.unicen.filmzoom.ui.detail.detailcomponents.MovieHeaderSection
import ar.edu.unicen.filmzoom.ui.detail.detailcomponents.MovieSynopsisCard

@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieDetailViewModel,
    onBackClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onWishlistClick: (Boolean) -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            MovieDetailTopBar(
                onBackClick = onBackClick
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
                MovieHeaderSection(
                    detailMovie = state.detailMovie
                )
            }
            // 2. Botones de acción principales (Deseados, Web, Compartir)
            val hasWebsite = !state.detailMovie?.homepage.isNullOrBlank()
            item {
                MovieActionButtons(
                    hasWebsite = hasWebsite,
                    onWebsiteClick = {
                        val url = state.detailMovie?.homepage
                        if (hasWebsite) {
                            val intent = Intent(Intent.ACTION_VIEW, url!!.toUri())
                            context.startActivity(intent)
                        }
                    },
                    onShareClick = {
                        // Lógica de compartir
                    },
                    onWishlistClick = { isWishlisted ->
                        // Lógica de favoritos/deseados
                    }
                )
            }

            // 3. Géneros (Chips horizontales)
            item {
                MovieGenresSection(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    genres = state.detailMovie?.genres ?: emptyList()
                )
            }

            // 4. Sinopsis y Director
            item {
                MovieSynopsisCard(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    overview = state.detailMovie?.overview ?: "",
                    director = state.detailMovie?.director
                )
            }

            // 5. Reparto principal
            item {
                MovieCastSection(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    cast = state.detailMovie?.cast
                )
            }
        }
    }
}

//@Preview(name = "Light Mode", showBackground = true)
//@Composable
//fun MovieDetailScreenLightPreview() {
//    FilmZoomTheme(darkTheme = false) {
//        Surface(color = MaterialTheme.colorScheme.background) {
//            MovieDetailScreen()
//        }
//    }
//}
//
//@Preview(name = "Dark Mode", showBackground = true)
//@Composable
//fun MovieDetailScreenDarkPreview() {
//    FilmZoomTheme(darkTheme = true) {
//        Surface(color = MaterialTheme.colorScheme.background) {
//            MovieDetailScreen()
//        }
//    }
//}