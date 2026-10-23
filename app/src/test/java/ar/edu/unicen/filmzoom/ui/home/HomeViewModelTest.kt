package ar.edu.unicen.filmzoom.ui.home

import ar.edu.unicen.filmzoom.R
import ar.edu.unicen.filmzoom.data.FakeMovieRepository
import ar.edu.unicen.filmzoom.data.repository.AppError
import ar.edu.unicen.filmzoom.data.repository.NetworkResult
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private lateinit var fakeRepository: FakeMovieRepository

    // Dispatcher para controlar los tiempos de las corrutinas en los tests
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = FakeMovieRepository()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `cuando el repositorio devuelve error de servidor, el uiState mapea la solucion 500`() = runTest {
        // GIVEN: Configuramos el repositorio falso para que falle con error de servidor
        fakeRepository.resultToReturn = NetworkResult.Error(
            exception = RuntimeException("Server Error 500"),
            errorType = AppError.SERVER
        )

        // WHEN: Inicializamos el ViewModel pasando el repositorio falso
        viewModel = HomeViewModel(movieRepository = fakeRepository)

        // Forzamos la ejecución de las corrutinas pendientes en el dispatcher de prueba
        testDispatcher.scheduler.advanceUntilIdle()

        // THEN: Verificamos que el estado sea Error y que la solución apunte al string 500
        val currentState = viewModel.uiState.value
        assert(currentState is HomeUiState.Error)
        assertEquals(R.string.error_500, (currentState as HomeUiState.Error).solution)
    }
}