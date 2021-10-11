package com.ifyezedev.tipsplit.settings

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.ifyezedev.tipsplit.R
import org.junit.Test
import org.junit.runner.RunWith


@MediumTest
@RunWith(AndroidJUnit4::class)
class SettingsFragmentTest {

    @Test
    fun displayUi() {
        launchFragmentInContainer<SettingsFragment>(null, R.style.Theme_TipSplit)
    }

}