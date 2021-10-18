package com.ifyezedev.tipsplit.calculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ifyezedev.tipsplit.R
import com.ifyezedev.tipsplit.databinding.FragmentCalculatorBinding


class CalculatorFragment : Fragment() {
    lateinit var binding: FragmentCalculatorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calculator, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        val viewModel = ViewModelProvider(this).get(CalculatorViewModel::class.java)

        binding.viewModel = viewModel

        binding.billTotalEditText.editText?.doOnTextChanged { text, start, before, count ->
            viewModel.calculate(
                text,
                binding.splitSeekbar.progress,
                binding.tipSeekbar.progress
            )
        }

        binding.tipSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(tipSeekBar: SeekBar?, tipProgress: Int, fromUser: Boolean) {
                viewModel.calculate(
                    binding.billTotalEditText.editText?.text.toString(),
                    binding.splitSeekbar.progress,
                    tipProgress
                )
            }

            override fun onStartTrackingTouch(tipSeekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(tipSeekBar: SeekBar?) {
            }

        })

        binding.splitSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(splitSeekBar: SeekBar?, splitProgress: Int, fromUser: Boolean) {
                viewModel.calculate(
                    binding.billTotalEditText.editText?.text.toString(),
                    splitProgress,
                    binding.tipSeekbar.progress
                )
            }

            override fun onStartTrackingTouch(splitSeekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(splitSeekBar: SeekBar?) {
            }

        })

        return binding.root
    }


}