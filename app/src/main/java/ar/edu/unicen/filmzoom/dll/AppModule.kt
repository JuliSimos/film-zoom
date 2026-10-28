package ar.edu.unicen.filmzoom.dll

import android.content.Context
import androidx.room.Room
import ar.edu.unicen.filmzoom.BuildConfig
import ar.edu.unicen.filmzoom.data.local.FilmZoomDatabase
import ar.edu.unicen.filmzoom.data.local.dao.MovieDao
import ar.edu.unicen.filmzoom.data.remote.TmdbApiService
import ar.edu.unicen.filmzoom.data.repository.MovieRepositoryImpl
import ar.edu.unicen.filmzoom.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    // Creamos el interceptor de logs
    val loggingInterceptor = okhttp3.logging.HttpLoggingInterceptor().apply {
        level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY // Muestra todo el JSON y cabeceras
    }

    // 1. Proveemos el cliente HTTP (OkHttpClient) configurando el Token en las cabeceras
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val newRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer ${BuildConfig.TMDB_BEARER_TOKEN}")
                    .header("accept", "application/json")
                    .build()
                chain.proceed(newRequest)
            }
            .addInterceptor(loggingInterceptor)
            .build()
    }
    // 2. Proveemos la instancia de Retrofit usando el cliente HTTP anterior y Gson
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 3. Proveemos nuestra interfaz de servicios (TmdbApiService)
    @Provides
    @Singleton
    fun provideTmdbApiService(retrofit: Retrofit): TmdbApiService {
        return retrofit.create(TmdbApiService::class.java)
    }

    // 4. Proveemos el Repositorio de la API
    @Provides
    @Singleton
    fun provideMovieRepository(
        movieRepositoryImpl: MovieRepositoryImpl
    ): MovieRepository {
        return movieRepositoryImpl
    }
    // 5. Proveedores de Room  (Base de datos local)

    @Provides
    @Singleton
    fun provideFilmZoomDatabase(
        @ApplicationContext context: Context
    ): FilmZoomDatabase {
        return Room.databaseBuilder(
            context,
            FilmZoomDatabase::class.java,
            FilmZoomDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(database: FilmZoomDatabase): MovieDao {
        return database.movieDao()
    }
}