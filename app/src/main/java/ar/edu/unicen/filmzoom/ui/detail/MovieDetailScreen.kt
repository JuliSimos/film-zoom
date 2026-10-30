package ar.edu.unicen.filmzoom.ui.detail

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
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
import ar.edu.unicen.filmzoom.R
import ar.edu.unicen.filmzoom.ui.components.MessaggeError
import ar.edu.unicen.filmzoom.ui.detail.detailcomponents.LoadingDetailWireframe
import ar.edu.unicen.filmzoom.ui.detail.detailcomponents.MovieActionButtons
import ar.edu.unicen.filmzoom.ui.detail.detailcomponents.MovieCastSection
import ar.edu.unicen.filmzoom.ui.detail.detailcomponents.MovieDetailTopBar
import ar.edu.unicen.filmzoom.ui.detail.detailcomponents.MovieGenresSection
import ar.edu.unicen.filmzoom.ui.detail.detailcomponents.MovieHeaderSection
import ar.edu.unicen.filmzoom.ui.detail.detailcomponents.MovieRecommendationsSection
import ar.edu.unicen.filmzoom.ui.detail.detailcomponents.MovieRecommendationsWireframe
import ar.edu.unicen.filmzoom.ui.detail.detailcomponents.MovieSynopsisCard
import ar.edu.unicen.filmzoom.ui.home.components.LoadingCardsWireframe
import ar.edu.unicen.filmzoom.ui.theme.AppSpacing
import java.util.Locale

@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieDetailViewModel,
    onMovieClick: (Int) -> Unit,
    onBackClick: () -> Unit = {},
) {
    val state by viewModel.uiState.collectAsState()
    val isWishlisted by viewModel.isWishlisted.collectAsState()
    val recommendations by viewModel.recommendations.collectAsState()
    val isLoadingRecommendations by viewModel.isRecommendationsLoading.collectAsState()
    val context = LocalContext.current

    val releaseLabel = stringResource(R.string.share_release_label)
    val footerText = stringResource(R.string.share_footer)
    val chooserTitle = stringResource(R.string.share_chooser_title)

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
                        contentPadding = PaddingValues(bottom = AppSpacing.extraLarge),
                        verticalArrangement = Arrangement.spacedBy(AppSpacing.medium)
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
                                modifier = Modifier
                                    .padding(horizontal = AppSpacing.medium)
                                    .widthIn(max = 480.dp),
                                hasWebsite = hasWebsite,
                                isWishlisted = isWishlisted,
                                onWishlistClick = {
                                    viewModel.onWishlistClick()
                                },
                                onWebsiteClick = {
                                    val url = details.homepage
                                    if (hasWebsite && !url.isNullOrBlank()) {
                                        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                                        context.startActivity(intent)
                                    }
                                },


                                onShareClick = {
                                    val formattedVote = String.format(Locale.US, "%.1f", details.voteAverage)
                                    val maxOverviewLenght = 140
                                    val trimmedOverview = if (details.overview.length > maxOverviewLenght){
                                        details.overview.take(maxOverviewLenght).trimEnd() + "..."
                                    }else{
                                        details.overview
                                    }

                                    val shareText = buildString{
                                        append("${details.title} (${formattedVote})\n")
                                        if (details.releaseDate.isNotBlank()){
                                            val year = details.releaseDate.take(4)
                                            append("$releaseLabel$year\n\n")
                                        }
                                        if (trimmedOverview.isNotBlank()){
                                            append("\"$trimmedOverview\"\n\n")
                                        }
                                        append(footerText)
                                    }

                                    val sendIntent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        putExtra(Intent.EXTRA_TEXT, shareText)
                                        type = "text/plain"
                                    }
                                    val shareIntent = Intent.createChooser(sendIntent, chooserTitle)
                                    context.startActivity(shareIntent)
                                }
                            )
                        }

                        // 3. Géneros (Chips horizontales)
                        item {
                            MovieGenresSection(
                                modifier = Modifier.padding(horizontal = AppSpacing.medium),
                                genres = details.genres
                            )
                        }

                        // 4. Sinopsis y Director
                        item {
                            MovieSynopsisCard(
                                modifier = Modifier.padding(horizontal = AppSpacing.medium),
                                overview = details.overview,
                                director = details.director,
                                production = details.production
                            )
                        }

                        // 5. Reparto principal
                        item {
                            MovieCastSection(
                                modifier = Modifier.padding(horizontal = AppSpacing.medium),
                                cast = details.cast
                            )
                        }
                        // 6. Sección de Recomendaciones hacia abajo (máximo 6 ítems)
                        if (isLoadingRecommendations || !recommendations.isNullOrEmpty()) {
                            item {
                                if (isLoadingRecommendations) {
                                    MovieRecommendationsWireframe(
                                        modifier = Modifier.padding(horizontal = AppSpacing.medium)
                                    )
                                } else {
                                    MovieRecommendationsSection(
                                        modifier = Modifier.padding(horizontal = AppSpacing.medium),
                                        movies = recommendations!!,
                                        onMovieClick = { recommendedMovieId ->
                                            onMovieClick(recommendedMovieId)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
