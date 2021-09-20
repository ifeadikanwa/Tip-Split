package com.ifyezedev.tipsplit.settings

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ifyezedev.tipsplit.R
import com.ifyezedev.tipsplit.data.AppTheme
import com.ifyezedev.tipsplit.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    lateinit var binding: FragmentSettingsBinding


    //because we used the @HiltViewModel on our view models we can just call them like this
    //in our fragment/activity and hilt knows how to instantiate them
    val settingsViewModel by viewModels<SettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        val viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        binding.viewModel = viewModel

        binding.appThemeRadioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, checkedId ->
            val appTheme =
                when(checkedId){
                    R.id.light_mode -> AppTheme.LIGHT
                    R.id.dark_mode -> AppTheme.DARK
                    else -> AppTheme.SYSTEM_DEFAULT
                }

            viewModel.changeAppTheme(appTheme)
        })

        settingsViewModel.mode.observe(viewLifecycleOwner, Observer {
            AppCompatDelegate.setDefaultNightMode(it)

            when(it) {
                AppTheme.DARK.mode -> binding.appThemeRadioGroup.check(binding.darkMode.id)
                AppTheme.LIGHT.mode -> binding.appThemeRadioGroup.check(binding.lightMode.id)
                AppTheme.SYSTEM_DEFAULT.mode -> binding.appThemeRadioGroup.check(binding.systemDefaultMode.id)
            }
        })

        return binding.root
    }

}