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
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.model.ReviewErrorCode
import com.ifyezedev.tipsplit.R
import com.ifyezedev.tipsplit.data.AppTheme
import com.ifyezedev.tipsplit.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private val TAG = "SETTINGS FRAGMENT"
    private lateinit var binding: FragmentSettingsBinding

    //because we used the @HiltViewModel on our view models we can just call them like this
    //in our fragment/activity and hilt knows how to instantiate them
    private val settingsViewModel by viewModels<SettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = settingsViewModel

        setupRadioButtonChangeListener()

        setupModeObserver()

        setupInAppReview()

        return binding.root
    }

    private fun setupInAppReview() {
        binding.rateTextView.setOnClickListener{
            //The ReviewManager is the interface that lets your app start an in-app review flow.
            val manager = ReviewManagerFactory.create(requireContext())

            //Request a ReviewInfo object
            val request = manager.requestReviewFlow()
            request.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // We got the ReviewInfo object
                    val reviewInfo = task.result

                    //launch the in app review flow
                    val flow = manager.launchReviewFlow(requireActivity(), reviewInfo)
                    flow.addOnCompleteListener {
                        Log.i(TAG, "flow is complete")
                    }
                }
                else {
                    // There was some problem, log or handle the error code.
                    val reviewErrorCode = task.exception
                    Log.i(TAG, reviewErrorCode.toString())
                }
            }
        }
    }

    private fun setupRadioButtonChangeListener() {
        binding.appThemeRadioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { _, checkedId ->
            val appTheme =
                when(checkedId){
                    R.id.light_mode -> AppTheme.LIGHT
                    R.id.dark_mode -> AppTheme.DARK
                    else -> AppTheme.SYSTEM_DEFAULT
                }

            settingsViewModel.changeAppTheme(appTheme)
        })
    }

    private fun setupModeObserver() {
        settingsViewModel.mode.observe(viewLifecycleOwner, Observer {
            AppCompatDelegate.setDefaultNightMode(it)

            when(it) {
                AppTheme.DARK.mode -> binding.appThemeRadioGroup.check(binding.darkMode.id)
                AppTheme.LIGHT.mode -> binding.appThemeRadioGroup.check(binding.lightMode.id)
                AppTheme.SYSTEM_DEFAULT.mode -> binding.appThemeRadioGroup.check(binding.systemDefaultMode.id)
            }
        })
    }
}