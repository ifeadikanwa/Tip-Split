package com.ifyezedev.tipsplit.calculator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ifyezedev.tipsplit.R
import com.ifyezedev.tipsplit.databinding.FragmentCalculatorBinding


class CalculatorFragment : Fragment() {
    lateinit var binding: FragmentCalculatorBinding
    lateinit var viewModel: CalculatorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //let activity know fragment has an options menu
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calculator, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        viewModel = ViewModelProvider(this).get(CalculatorViewModel::class.java)

        binding.viewModel = viewModel

        binding.billTotalEditText.editText?.doOnTextChanged { text, _, _, _ ->
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

        binding.roundUpButton.setOnClickListener {
            viewModel.roundUp(
                binding.billOnlyTextview.text.toString(),
                binding.finalBillTotalTextview.text.toString(),
                binding.splitSeekbar.progress
            )
        }

        binding.roundDownButton.setOnClickListener {
            viewModel.roundDown(
                binding.billOnlyTextview.text.toString(),
                binding.finalBillTotalTextview.text.toString(),
                binding.splitSeekbar.progress
            )
        }

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.app_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.copy -> {
                copyInformation()
                true
            }
            R.id.share -> {
                shareInformation()
                true
            }
            R.id.settings -> {
                goToSettingFragment()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun copyInformation() {
        // Gets a handle to the clipboard service.
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        val information = viewModel.getCalculatedInformation(binding.splitSeekbar.progress)

        // Creates a new text clip to put on the clipboard
        val clip: ClipData = ClipData.newPlainText("Tip & Split Calculation", information)

        // Set the clipboard's primary clip.
        clipboard.setPrimaryClip(clip)

        Toast.makeText(requireContext(), "Copied!", Toast.LENGTH_SHORT).show()
    }

    private fun shareInformation() {
    }

    private fun goToSettingFragment() {

    }
}