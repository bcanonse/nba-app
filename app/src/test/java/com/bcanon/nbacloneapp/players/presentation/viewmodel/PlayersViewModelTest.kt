package com.bcanon.nbacloneapp.players.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.bcanon.nbacloneapp.players.domain.usecase.GetPlayersUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule

//TOdo: Agregar testing.
@ExperimentalCoroutinesApi
class PlayersViewModelTest {

    @RelaxedMockK
    private lateinit var useCase: GetPlayersUseCase

    @RelaxedMockK
    private lateinit var savedStateHandle: SavedStateHandle

    private lateinit var viewModel: PlayersViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = PlayersViewModel(
            getPlayersUseCase = useCase,
            savedStateHandle = savedStateHandle
        )
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


}