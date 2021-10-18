package com.ifyezedev.tipsplit.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.ceil
import kotlin.math.floor

class CalculatorViewModel: ViewModel() {

    private var _userBill = MutableLiveData<String>()
    val userBill: LiveData<String>
        get() = _userBill

    private var _userBillTips = MutableLiveData<String>()
    val userBillTips: LiveData<String>
        get() = _userBillTips

    private var _finalBillTotal = MutableLiveData<String>()
    val finalBillTotal: LiveData<String>
        get() = _finalBillTotal

    private var _splitPerPersonBill = MutableLiveData<String>()
    val splitPerPersonBill: LiveData<String>
        get() = _splitPerPersonBill

    private var _splitPerPersonTips = MutableLiveData<String>()
    val splitPerPersonTips: LiveData<String>
        get() = _splitPerPersonTips

    private var _finalSplitPerPersonBill = MutableLiveData<String>()
    val finalSplitPerPersonBill: LiveData<String>
        get() = _finalSplitPerPersonBill

    private var _currentTipPercentage = MutableLiveData<String>()
    val currentTipPercentage: LiveData<String>
        get() = _currentTipPercentage

    private var _currentSplitNumber = MutableLiveData<String>()
    val currentSplitNumber: LiveData<String>
        get() = _currentSplitNumber

    private val zeroDouble = "0.00"

    init {
        //set seekbar initial values
        val initialSeekBarValue = "0"
        _currentTipPercentage.value = "$initialSeekBarValue%"
        _currentSplitNumber.value = initialSeekBarValue

        resetAllBillValues()
    }

    fun calculate(userBillString: CharSequence?, splitNumber: Int, tipPercentage: Int) {
        _currentSplitNumber.value = splitNumber.toString()
        _currentTipPercentage.value = "$tipPercentage%"

        if (!userBillString.isNullOrEmpty()) {
            val userBill = "$userBillString".toDouble()

            val userTips = calculateTips(tipPercentage, userBill)

            setFinalBillValues(userBill, userTips)

            calculateSplit(splitNumber, userBill, userTips)

        }
        else {
            resetAllBillValues()
        }
    }

    private fun resetAllBillValues() {
        _userBill.value = zeroDouble
        _userBillTips.value = zeroDouble
        _finalBillTotal.value = zeroDouble

        _splitPerPersonBill.value = zeroDouble
        _splitPerPersonTips.value = zeroDouble
        _finalSplitPerPersonBill.value = zeroDouble
    }

    private fun setFinalBillValues(userBill: Double, userTips: Double) {
        _userBill.value = String.format("%.2f", userBill)
        _userBillTips.value = String.format("%.2f", userTips)
        _finalBillTotal.value = String.format("%.2f", userBill + userTips)
    }

    private fun calculateTips(tipPercentage: Int, userBill: Double): Double {
        return (tipPercentage.toDouble() / 100.00) * userBill
    }

    private fun calculateSplit(splitNumber: Int, totalBill: Double, totalTips: Double) {
        val splitBill = if(splitNumber != 0) totalBill / splitNumber else totalBill
        val splitTips = if(splitNumber != 0) totalTips / splitNumber else totalTips

        _splitPerPersonBill.value = String.format("%.2f", splitBill)
        _splitPerPersonTips.value = String.format("%.2f", splitTips)
        _finalSplitPerPersonBill.value = String.format("%.2f", splitBill + splitTips)

    }

    fun roundUp(userBillOnlyString: String?, finalBillTotalString: String?, splitNumber: Int) {

        if (!userBillOnlyString.isNullOrEmpty() && !finalBillTotalString.isNullOrEmpty()){
            if (userBillOnlyString != zeroDouble || finalBillTotalString != zeroDouble){

                val finalBillTotal = finalBillTotalString.toDouble()
                val userBillOnly = userBillOnlyString.toDouble()

                val roundedBillTotal = ceil(finalBillTotal)

                val newTips = roundedBillTotal - userBillOnly

                val newTipPercentage = (newTips / userBillOnly) * 100.0

                _currentTipPercentage.value = "${newTipPercentage.toInt()}%"

                setFinalBillValues(userBillOnly, newTips)

                calculateSplit(splitNumber, userBillOnly, newTips)

            }
        }
    }

    fun roundDown(userBillOnlyString: String?, finalBillTotalString: String?, splitNumber: Int) {
        if (!userBillOnlyString.isNullOrEmpty() && !finalBillTotalString.isNullOrEmpty()){
            if (userBillOnlyString != zeroDouble || finalBillTotalString != zeroDouble){

                val finalBillTotal = finalBillTotalString.toDouble()
                val userBillOnly = userBillOnlyString.toDouble()

                val roundedBillTotal = floor(finalBillTotal)

                val newTips = roundedBillTotal - userBillOnly

                val newTipPercentage = (newTips / userBillOnly) * 100.0

                _currentTipPercentage.value = "${newTipPercentage.toInt()}%"

                setFinalBillValues(userBillOnly, newTips)

                calculateSplit(splitNumber, userBillOnly, newTips)

            }
        }
    }

    fun getCalculatedInformation(splitNumber: Int) : String {
        val finalBillTemplate = "Final bill: $${_finalBillTotal.value} = Bill: $${_userBill.value} + Tips: $${_userBillTips.value}"
        val splitBillTemplate = if (splitNumber > 1)"\nBill per person = $${_finalSplitPerPersonBill.value}" else ""

        return finalBillTemplate + splitBillTemplate
    }
}