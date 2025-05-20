package com.eshaan.historicalapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.eshaan.historicalapp.data.repository.EventRepository
import com.eshaan.historicalapp.ui.login.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repository: EventRepository
    private lateinit var viewModel: LoginViewModel

    @Before fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock()
        viewModel = LoginViewModel(repository)
    }

    @After fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test fun `login with empty credentials should return error`() {
        viewModel.login("", "")
        val state = viewModel.loginState.value
        assertTrue(state is LoginViewModel.LoginState.Error)
        assertEquals("Username and password are required",
            (state as LoginViewModel.LoginState.Error).message)
    }

    @Test fun `login with valid credentials should return success`() = runTest {
        // Stub inside onBlocking { } using Kotlin DSL
        whenever(repository.login(any(), any())) doReturn Result.success("history")
        viewModel.login("Eshaan", "s8093457")
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.loginState.value
        assertTrue(state is LoginViewModel.LoginState.Success)
        assertEquals("history", viewModel.keypass.value)
    }

    @Test fun `login with invalid credentials should return error`() = runTest {
        whenever(repository.login(any(), any())) doReturn Result.failure(Exception("Invalid credentials"))
        viewModel.login("wronguser", "wrongpass")
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.loginState.value
        assertTrue(state is LoginViewModel.LoginState.Error)
        assertEquals("Invalid credentials",
            (state as LoginViewModel.LoginState.Error).message)
    }
}
