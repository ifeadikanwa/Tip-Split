package com.ifyezedev.tipsplit.calculator

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorViewModel: ViewModel() {
    private val TAG = "CalculatorViewModel"

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

    init {
        val initialSeekBarsValue = 0
        _currentTipPercentage.value = "$initialSeekBarsValue%"
        _currentSplitNumber.value = initialSeekBarsValue.toString()

        resetAllBillValues()
    }

    fun calculate(userBillString: CharSequence?, splitNumber: Int, tipPercentage: Int) {
        _currentSplitNumber.value = splitNumber.toString()
        _currentTipPercentage.value = "$tipPercentage%"

        if (!userBillString.isNullOrEmpty()) {
            val userBillVar = "$userBillString".toDouble()
            _userBill.value = String.format("%.2f", userBillVar)

            val userTipsVar = calculateTips(tipPercentage, userBillVar)
            _userBillTips.value = String.format("%.2f", userTipsVar)

            _finalBillTotal.value = String.format("%.2f", userBillVar + userTipsVar)

            val (splitBillVar, splitTipsVar) = calculateSplit(splitNumber, userBillVar, userTipsVar)
            _splitPerPersonBill.value = String.format("%.2f", splitBillVar)
            _splitPerPersonTips.value = String.format("%.2f", splitTipsVar)
            _finalSplitPerPersonBill.value = String.format("%.2f", splitBillVar + splitTipsVar)
        }
        else {
            resetAllBillValues()
        }
    }

    private fun resetAllBillValues() {
        val zeroDouble = "0.00"

        _userBill.value = zeroDouble
        _userBillTips.value = zeroDouble
        _finalBillTotal.value = zeroDouble

        _splitPerPersonBill.value = zeroDouble
        _splitPerPersonTips.value = zeroDouble
        _finalSplitPerPersonBill.value = zeroDouble
    }


    private fun calculateTips(tipPercentage: Int, userBill: Double): Double {
        return (tipPercentage.toDouble() / 100.00) * userBill
    }

    private fun calculateSplit(splitNumber: Int, totalBill: Double, totalTips: Double) : Pair<Double,Double> {
        return if (splitNumber == 0) {
            Pair(totalBill, totalTips)
        } else {
            val splitBill = totalBill / splitNumber
            val splitTips = totalTips / splitNumber

            Pair(splitBill, splitTips)
        }

    }
}