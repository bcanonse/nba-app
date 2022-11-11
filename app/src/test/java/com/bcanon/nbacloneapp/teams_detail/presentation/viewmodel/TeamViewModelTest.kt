package com.bcanon.nbacloneapp.teams_detail.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bcanon.nbacloneapp.core.StateWrapper
import com.bcanon.nbacloneapp.teams.domain.model.Teams
import com.bcanon.nbacloneapp.teams_detail.domain.usecase.GetTeamUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertNotNull
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
class TeamViewModelTest {

    private companion object {
        private val mockTeam = Teams(
            id = 1,
            name = "Atlanta",
            abbreviation = "Hawks",
            conference = "East",
            fullName = "Atlanta Hawks"
        )

        private val mockResult = Result.success(mockTeam)
        private val mockResultNull = Result.success(null)
    }

    @RelaxedMockK
    private lateinit var useCase: GetTeamUseCase

    private lateinit var viewModel: TeamViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = TeamViewModel(
            getTeamUseCase = useCase
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
        coEvery { useCase(any()) } returns mockResult


        var values = StateWrapper<Teams>()
        viewModel.state.observeForever {
            values = it
        }
        //When
        assert(values.isLoading)
        viewModel.getTeam(
            id = 1
        )

        //Then
        assert(mockResult.isSuccess)
        assert(!values.isLoading)
        assertNotNull(values.data)
    }

    @Test
    fun `When the last state the viewmodel is get object team`() = runTest {
        //Given
        coEvery { useCase(any()) } returns mockResult
        //When
        viewModel.getTeam(id = 1)
        //Then
        assert(mockResult.isSuccess)
        assert(
            viewModel.state.value == StateWrapper(
                isLoading = false,
                data = mockTeam,
                error = null
            )
        )
        assert(viewModel.state.value?.data == mockTeam)
    }

    @Test
    fun `if get team and return null keep the last value`(): Unit = runTest {
        //Given
        coEvery { useCase(any()) } returns mockResultNull

        //When
        //Then
        assert(viewModel.state.value?.data == null)
    }
}