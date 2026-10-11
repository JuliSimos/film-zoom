package ar.edu.unicen.filmzoom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier

import ar.edu.unicen.filmzoom.ui.navigation.FilmZoomNavGraph
import ar.edu.unicen.filmzoom.ui.theme.FilmZoomTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Permite que la UI ocupe toda la pantalla (incluyendo barras del sistema)
        //enableEdgeToEdge()

        setContent {

            FilmZoomTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    // Llamamos a nuestro grafo de navegación separado
                    FilmZoomNavGraph()
                }
            }
        }
    }
}