package ar.edu.unicen.filmzoom

import ar.edu.unicen.filmzoom.data.remote.TmdbApiService
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConnectionTest {

    @Test
    fun probarConexionTmdb() = runBlocking {
        // Token temporal para la prueba local de escritorio
        val token = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlYTk2MTQ3ZTk4OWQyYjdhNjdjYjE1NGQ1Y2JlZGFhNyIsIm5iZiI6MTc5MTQzMTg1OC42NzkwMDAxLCJzdWIiOiI2YWM3MTRiMjJhNGQ4ZmU0NDk3NjRlMTUiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.THaLJouV0AGAhLmBpT1HveI-2k6i1LnTPRu45OAud68"

        // Cliente OkHttp idéntico al de tu NetworkModule pero autónomo para el test
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("Authorization", "Bearer $token")
                    .header("accept", "application/json")
                    .build()
                chain.proceed(request)
            }
            .build()

        // Instancia directa de Retrofit
        val api = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TmdbApiService::class.java)

        try {
            // Hacemos la llamada de prueba
            val response = api.getPopularMovies(language = "es-ES")

            println("----------------------------------------")
            println(" ¡CONEXIÓN EXITOSA CON TMDB!")
            println(" Películas populares encontradas: ${response.results.size}")
            println("----------------------------------------")

            response.results.take(3).forEach { movie ->
                println(" - ${movie.title} (Voto: ${movie.voteAverage})")
            }

            assert(response.results.isNotEmpty())
        } catch (e: Exception) {
            println(" ERROR DE CONEXIÓN:")
            e.printStackTrace()
            throw e
        }
    }
}