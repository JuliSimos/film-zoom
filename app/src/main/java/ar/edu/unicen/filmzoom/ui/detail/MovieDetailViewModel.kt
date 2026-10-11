package ar.edu.unicen.filmzoom.ui.detail

import androidx.lifecycle.ViewModel
import ar.edu.unicen.filmzoom.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository
): ViewModel(){
    //lógica de estado, corrutinas y llamadas al repositorio acá




}