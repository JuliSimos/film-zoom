package ar.edu.unicen.filmzoom.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unicen.filmzoom.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
): ViewModel(){

    // 2. Estado mutable interno (privado para que nadie de afuera lo rompa)
    private val _uiState = MutableStateFlow(HomeUiState())

    // 3. Estado público de sólo lectura al que se suscribe la UI
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeData()
    }

    fun loadHomeData(){
        viewModelScope.launch {
            //estado de carga activado
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            try{
                //Traemos los datos del repositorio
                val popular = movieRepository.getPopularMovies()
                val trending = movieRepository.getTrendingMovies()

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    popularMovies = popular,
                    trendingMovies = trending
                )
            }catch (e: Exception){
                //Capturo error
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.localizedMessage ?: "Error desconocido"
                )
            }

        }
    }

}