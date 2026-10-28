package ar.edu.unicen.filmzoom.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ar.edu.unicen.filmzoom.ui.home.HomeScreen
import ar.edu.unicen.filmzoom.ui.detail.MovieDetailScreen
import ar.edu.unicen.filmzoom.ui.detail.MovieDetailViewModel
import dagger.hilt.android.lifecycle.HiltViewModel


@Composable
fun FilmZoomNavGraph(
    modifier : Modifier = Modifier,
    navController : NavHostController = rememberNavController()
) {

    NavHost(
        navController = navController,
        startDestination = "Movies",
        modifier = modifier
    ) {
        // Pantalla Principal (Home / Movies)
        composable(route = "Movies") {
            HomeScreen(
                onMovieClick = { movieId ->
                    navController.navigate("movie_detail/$movieId")
                }
            )
        }

        // Pantalla de Detalle recibiendo el id_movie
        composable(
            route = "movie_detail/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val viewModel: MovieDetailViewModel = hiltViewModel()

            MovieDetailScreen(
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                },
            )
        }
    }
}

