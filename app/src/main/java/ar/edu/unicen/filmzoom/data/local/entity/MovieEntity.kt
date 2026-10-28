package ar.edu.unicen.filmzoom.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ar.edu.unicen.filmzoom.domain.model.Movie


@Entity(tableName = "wishlist_movies")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
)

fun MovieEntity.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
    )
}
fun Movie.toEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
    )
}