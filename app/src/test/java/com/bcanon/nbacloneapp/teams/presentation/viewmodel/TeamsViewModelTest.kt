package com.bcanon.nbacloneapp.teams.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bcanon.nbacloneapp.teams.domain.model.Teams
import com.bcanon.nbacloneapp.teams.domain.usecase.GetTeamsUseCase
import com.bcanon.nbacloneapp.teams.presentation.ui.TeamsState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TeamsViewModelTest {

    private companion object {
        private val mockList = listOf(
            Teams(
                id = 1,
                name = "Atlanta",
                abbreviation = "Hawks",
                conference = "East",
                fullName = "Atlanta Hawks"
            )
        )

        private val mockResultList = Result.success(mockList)
    }

    @RelaxedMockK
    private lateinit var useCase: GetTeamsUseCase

    private lateinit var viewModel: TeamsViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = TeamsViewModel(
            getTeamsUseCase = useCase
        )
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `When manage correctly state then ViewModel`() = runTest {
        //Given
        coEvery { useCase() } returns mockResultList


        var values = TeamsState()
        viewModel.state.observeForever {
            values = it
        }
        //When
        assert(values.isLoading)
        viewModel.getTeams()

        //Then
        assert(mockResultList.isSuccess)
        assert(!values.isLoading)
        assert(values.teams.isNotEmpty())
    }

    @Test
    fun `When the last state the viewmodel is get list all teams`() = runTest {
        //Given
        coEvery { useCase() } returns mockResultList
        //When
        viewModel.getTeams()
        //Then
        assert(mockResultList.isSuccess)
        assert(
            viewModel.state.value == TeamsState(
                isLoading = false,
                teams = mockList,
                error = null
            )
        )
        assert(viewModel.state.value?.teams == mockList)
    }
}