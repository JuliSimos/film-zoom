package ar.edu.unicen.filmzoom.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.unicen.filmzoom.ui.home.HomeScreen
import ar.edu.unicen.filmzoom.ui.detail.MovieDetailScreen


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
                    navController.navigate("MovieDetails")
                    //navController.navigate("MovieDetail/$movieId")
                }
            )
        }

        // Pantalla de Detalle recibiendo el id_movie
        composable (route = "MovieDetails") { //backStackEntry ->
            //val movieId = backStackEntry.arguments?.getString("id_movie")?.toIntOrNull() ?: 0
                // o getInt() si lo casteo
            // Opcional: Aquí luego le pasarías el movieId a tu ViewModel de detalle
            // val viewModel: MovieDetailViewModel = viewModel()
            // val movieState = viewModel.uiState // o la llamada para buscar por ID

            MovieDetailScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onShareClick = { /* Compartir */ },
                onProfileClick = { /* Perfil */ },
                onWishlistClick = { /* Deseados */ }
            )
        }
    }
}

