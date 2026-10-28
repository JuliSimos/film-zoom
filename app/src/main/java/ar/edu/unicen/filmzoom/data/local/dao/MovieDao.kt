package ar.edu.unicen.filmzoom.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ar.edu.unicen.filmzoom.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

/**
 * @Dao: Le indica a Room que esta interfaz es un objeto de acceso a datos.
 *
 * @Query: Permite escribir consultas SQL puras. Usar Flow<List<MovieEntity>> en getWishlistMovies() hace que Room emita una nueva lista automáticamente cada vez que la tabla cambie.
 *
 * suspend: Las operaciones de escritura (insert y delete) se ejecutan en corrutinas para no bloquear el hilo principal de la app.
 */

@Dao
interface MovieDao {
    // 1. Obtener todas las películas de la wishlist de forma reactiva
    @Query("SELECT * FROM wishlist_movies")
    fun getWishlistMovies(): Flow<List<MovieEntity>>

    // 2. Insertar una película
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    // 3. Eliminar una película específica
    @Query("DELETE FROM wishlist_movies WHERE id = :movieId")
    suspend fun deleteMovieById(movieId: Int)

    // 4. Verificar si una película específica ya está en la wishlist
    @Query("SELECT EXISTS(SELECT * FROM wishlist_movies WHERE id = :movieId)")
    fun isMovieInWishlist(movieId: Int): Flow<Boolean>

}