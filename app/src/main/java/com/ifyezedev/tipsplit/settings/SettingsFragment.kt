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
import androidx.lifecycle.lifecycleScope
import com.android.billingclient.api.*
import com.google.android.play.core.review.ReviewManagerFactory
import com.ifyezedev.tipsplit.R
import com.ifyezedev.tipsplit.data.AppTheme
import com.ifyezedev.tipsplit.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private val TAG = "SETTINGS FRAGMENT"
    private lateinit var binding: FragmentSettingsBinding
    var coffee: SkuDetails? = null
    var ramen: SkuDetails? = null
    var fastfood: SkuDetails? = null
    private lateinit var billingClient: BillingClient


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

        initializeBillingClient()

        setupInAppPurchases()

        setupPurchaseButtons()

        return binding.root
    }

    private fun setupPurchaseButtons() {
        binding.cardViewCoffee.setOnClickListener{
            launchPurchaseFlow(coffee)
        }

        binding.cardViewRamen.setOnClickListener{
            launchPurchaseFlow(ramen)
        }

        binding.cardViewFastfood.setOnClickListener{
            launchPurchaseFlow(fastfood)
        }
    }

    private val purchasesUpdatedListener = PurchasesUpdatedListener{ billingResult, purchases ->
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (purchase in purchases) {
                handlePurchase(purchase)
            }
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            // Handle an error caused by a user cancelling the purchase flow.
        } else {
            // Handle any other error codes.
        }
    }

    private fun handlePurchase(purchase: Purchase?) {
        if(purchase != null) {
            if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged) {

            }
        }
    }

    private fun initializeBillingClient() {
        billingClient = BillingClient.newBuilder(requireActivity())
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()
    }

    private fun setupInAppPurchases() {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                    getProductsDetails()
                }
            }

            override fun onBillingServiceDisconnected() {
                //retry
                setupInAppPurchases()
            }
        })
    }

    private fun getProductsDetails() {
        val productIds = ArrayList<String>()
        productIds.add("support.small")
        productIds.add("support.medium")
        productIds.add("support.big")

        val skuDetailsParams = SkuDetailsParams.newBuilder()
            .setSkusList(productIds)
            .setType(BillingClient.SkuType.INAPP)

        lifecycleScope.launch {
            val skuDetailsResult = withContext(Dispatchers.IO) {
                billingClient.querySkuDetails(skuDetailsParams.build())
            }

            val skuDetailsList = skuDetailsResult.skuDetailsList

            coffee = skuDetailsList?.get(2)
            ramen = skuDetailsList?.get(1)
            fastfood = skuDetailsList?.get(0)

            binding.coffeePrice.text = coffee?.price
            binding.ramenPrice.text = ramen?.price
            binding.fastfoodPrice.text = fastfood?.price
        }

    }

    private fun launchPurchaseFlow(skuDetails: SkuDetails?) {
        if (skuDetails != null){
            val flowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(skuDetails)
                .build()

            val responseCode =
                billingClient.launchBillingFlow(requireActivity(), flowParams).responseCode

            Log.i(TAG,"purchase flow successful: " + (responseCode==BillingClient.BillingResponseCode.OK).toString())
        }

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