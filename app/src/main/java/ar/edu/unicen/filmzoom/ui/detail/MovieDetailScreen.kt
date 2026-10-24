package ar.edu.unicen.filmzoom.ui.detail
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import ar.edu.unicen.filmzoom.ui.components.MessaggeError
import ar.edu.unicen.filmzoom.ui.detail.detailcomponents.LoadingDetailWireframe
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Mostramos los wireframes si está cargando o hay un error
            if (state is DetailUiState.Loading || state is DetailUiState.Error) {
                LoadingDetailWireframe()
            }

            when (val currentState = state) {
                is DetailUiState.Loading -> {
                }

                is DetailUiState.Error -> {
                    MessaggeError(
                        message = stringResource(id = currentState.message),
                        solution = stringResource(id = currentState.solution),
                        buttonText = stringResource(id = currentState.buttonText),
                        onActionClick = {
                            if (currentState.isRetry) {
                                viewModel.retry()
                            } else {
                                onBackClick()
                            }
                        },
                        onDismiss = { onBackClick() }
                    )
                }

                is DetailUiState.Success -> {
                    val details = currentState.details
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 32.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        // 1. Cabecera (Banner + Póster superpuesto + Título + Rating)
                        item {
                            MovieHeaderSection(
                                detailMovie = details
                            )
                        }

                        // 2. Botones de acción principales (Deseados, Web, Compartir)
                        val hasWebsite = !details.homepage.isNullOrBlank()
                        item {
                            MovieActionButtons(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                hasWebsite = hasWebsite,
                                onWebsiteClick = {
                                    val url = details.homepage
                                    if (hasWebsite && !url.isNullOrBlank()) {
                                        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                                        context.startActivity(intent)
                                    }
                                },
                                onShareClick = onShareClick,
                                onWishlistClick = onWishlistClick
                            )
                        }

                        // 3. Géneros (Chips horizontales)
                        item {
                            MovieGenresSection(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                genres = details.genres ?: emptyList()
                            )
                        }

                        // 4. Sinopsis y Director
                        item {
                            MovieSynopsisCard(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                overview = details.overview ?: "",
                                director = details.director
                            )
                        }

                        // 5. Reparto principal
                        item {
                            MovieCastSection(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                cast = details.cast
                            )
                        }
                    }
                }
            }
        }
    }
}
