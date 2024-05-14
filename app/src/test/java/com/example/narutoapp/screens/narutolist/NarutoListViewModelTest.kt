package com.example.narutoapp.screens.narutolist

import com.example.narutoapp.data.models.ListEntry
import com.example.narutoapp.data.remote.responses.Character
import com.example.narutoapp.data.remote.responses.Debut
import com.example.narutoapp.data.remote.responses.Family
import com.example.narutoapp.repository.NarutoRepository
import com.example.narutoapp.util.Resourse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

@ExperimentalCoroutinesApi
class NarutoListViewModelTest {

    private lateinit var viewModel: NarutoListViewModel
    private lateinit var repository: NarutoRepository
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        kotlinx.coroutines.Dispatchers.setMain(testDispatcher)

        repository = mockk()
        viewModel = NarutoListViewModel(repository)
    }

    @After
    fun tearDown() {
        kotlinx.coroutines.Dispatchers.resetMain()
    }

    @Test
    fun `loadCharacters success`() = testDispatcher.runBlockingTest {
        val characters = listOf(
            Character(
                1,
                "Naruto",
                listOf("https://static.wikia.nocookie.net/naruto/images/e/e6/Ten-Tails_emerges.png"),
                Debut("", "", "", "", "", "", ""),
                Family("", "", "", "", "", "", "", "", "", "", ""),
                listOf(),
                listOf()
            )
        )
        val entr = ListEntry(characters, 1, 20, 20)
        val expectedResult = Resourse.Success(entr)

        coEvery { repository.getALlCharacter(1, 20) } returns expectedResult

        viewModel.loadCharacters()

        assertEquals(characters.size, viewModel.charactersList.size)
    }
}