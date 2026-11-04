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

    // Config el Token en las cabeceras
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
            .build()
    }
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideTmdbApiService(retrofit: Retrofit): TmdbApiService {
        return retrofit.create(TmdbApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        movieRepositoryImpl: MovieRepositoryImpl
    ): MovieRepository {
        return movieRepositoryImpl
    }

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