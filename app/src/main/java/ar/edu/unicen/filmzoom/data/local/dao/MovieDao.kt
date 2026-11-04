package ar.edu.unicen.filmzoom.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ar.edu.unicen.filmzoom.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    // Obtener todas las películas de la wishlist
    @Query("SELECT * FROM wishlist_movies")
    fun getWishlistMovies(): Flow<List<MovieEntity>>

    // Insertar una película
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    // Eliminar una película específica
    @Query("DELETE FROM wishlist_movies WHERE id = :movieId")
    suspend fun deleteMovieById(movieId: Int)

    // Verificar si una película ya está en la wishlist
    @Query("SELECT EXISTS(SELECT * FROM wishlist_movies WHERE id = :movieId)")
    fun isMovieInWishlist(movieId: Int): Flow<Boolean>

}