package com.example.tippy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 15

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sbTip.progress = INITIAL_TIP_PERCENT
        tvtipPercent.text = "$INITIAL_TIP_PERCENT%"

        sbTip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvtipPercent.text = "$progress%"
                computeTipAndTotal()
                updateTipDescription(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        etBase.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                computeTipAndTotal()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun updateTipDescription(tipPercent: Int) {
        val tipDescription : String
        when (tipPercent) {
            in 0..9 -> tipDescription = "\uD83D\uDE10"
            in 10..14 -> tipDescription = "\uD83D\uDE42"
            in 15..19 -> tipDescription = "\uD83D\uDE03"
            else -> tipDescription = "\uD83E\uDD29"
        }
        tvTipDescription.text = tipDescription
    }

    private fun computeTipAndTotal() {
        if (etBase.text.isEmpty()) {
            tvTipAmount.text = ""
            textView4.text = ""
            return
        }
        val baseAmount = etBase.text.toString().toDouble()
        val tipPercent = sbTip.progress
        val tipAmount = baseAmount*(tipPercent/100.0)
        val totalAmount = tipAmount + baseAmount
        tvTipAmount.text = "%.2f".format(tipAmount)
        textView4.text = "%.2f".format(totalAmount)
    }
}