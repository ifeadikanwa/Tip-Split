package com.ifyezedev.tipsplit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.lifecycle.Observer
import com.ifyezedev.tipsplit.calculator.CalculatorFragment
import com.ifyezedev.tipsplit.settings.SettingsFragment
import com.ifyezedev.tipsplit.settings.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frag_cont, CalculatorFragment())
            .commit()


    }
}