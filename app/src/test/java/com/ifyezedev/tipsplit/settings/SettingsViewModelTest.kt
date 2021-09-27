package com.ifyezedev.tipsplit.settings

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ifyezedev.tipsplit.data.AppTheme
import com.ifyezedev.tipsplit.data.FakeRepository
import com.ifyezedev.tipsplit.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.MatcherAssert.*
import org.hamcrest.core.IsEqual
import org.junit.After
import org.junit.Before
import org.junit.Rule

import org.junit.Test

@ExperimentalCoroutinesApi
class SettingsViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var repository: FakeRepository
    lateinit var settingsViewModel: SettingsViewModel
    val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setupViewModel() {
        Dispatchers.setMain(dispatcher)

        val theme1 = AppTheme.DARK
        repository = FakeRepository(theme1)

        settingsViewModel = SettingsViewModel(repository)
    }

    @Test
    fun mode_LiveDataUpdatesToNewThemeMode() {
        //when the app theme is updated
        val theme3 = AppTheme.LIGHT
        settingsViewModel.changeAppTheme(theme3)

        //the observed livedata emits the updated theme mode
        val mode = settingsViewModel.mode.getOrAwaitValue()

        assertThat(mode, IsEqual(theme3.mode))
    }

    @Test
    fun changeAppTheme_RepositoryReturnsTheme2() {
        val theme2 = AppTheme.SYSTEM_DEFAULT

        //when theme is changed
        settingsViewModel.changeAppTheme(theme2)

        //then repository should now store new theme
        assertThat(repository.theme, IsEqual(theme2))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}