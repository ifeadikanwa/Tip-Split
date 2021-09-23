package com.ifyezedev.tipsplit.data

import androidx.lifecycle.asLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.*
import org.hamcrest.core.IsEqual
import org.junit.Before

import org.junit.Test

@ExperimentalCoroutinesApi
class RepositoryTest {
    val theme1 = FakeAppTheme.SYSTEM_DEFAULT
    val theme2 = FakeAppTheme.DARK
    val theme3 = FakeAppTheme.LIGHT
    lateinit var appThemePreference: FakeAppThemePreference

    //class under test
    private lateinit var repository: Repository

    @Before
    fun createRepository(){
        appThemePreference = FakeAppThemePreference(theme1)
        repository = Repository(appThemePreference)
    }

    @Test
    fun saveAppTheme_storeThemeInPreference_themeEqualsTheme2() = runBlocking {
        //When theme is saved using the repository
        repository.saveAppTheme(theme2)
        //Then it should be stored in the preference
        assertThat(appThemePreference.appTheme, IsEqual(theme2))
    }

    @Test
    fun getTheme_requestsThemeFromPreference_themeEqualsTheme1Mode() = runBlocking {
        var themeMode: Int? = null

        //Given
        repository.saveAppTheme(theme3)

        //when we request the theme from preference
        repository.getThemeFlow().collect{
            themeMode = it
        }


        //then themeMode equals app theme mode in the preference
        assertThat(themeMode, IsEqual(appThemePreference.appTheme.mode))

    }
}