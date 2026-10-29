package ar.edu.unicen.filmzoom.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ar.edu.unicen.filmzoom.domain.model.Movie
import ar.edu.unicen.filmzoom.ui.home.components.LoadingCardsWireframe

class MoviePagingSource(
    private val fetchMovies: suspend (page: Int) -> List<Movie> // O tu DTO/Modelo de API
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        return try {
            val movies = fetchMovies(page)

            // Si la API devuelve una lista vacía, cortamos la paginación acá y evitamos crashes
            val nextKey = if (movies.isEmpty()) null else page + 1

            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            // Si ocurre un error (por ejemplo, pasarse del límite de páginas de Trending),
            // en lugar de crashear, devolvemos LoadResult.Error o cerramos el flujo si es fin de datos.
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}