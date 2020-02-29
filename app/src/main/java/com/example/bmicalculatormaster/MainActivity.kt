package com.example.bmicalculatormaster

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        buttonCalculateBMI.setOnClickListener {
            calculateBMI(
                heightEditText.text.toString(),
                weightEditText.text.toString()
            )
        }
    }

    private fun calculateBMI(height: String, weight: String) {
        if (!validateDataAndShowErrors(height, weight)) {
            return
        }
        val heightDouble = height.toDouble() / 100
        val bmi = (weight.toDouble() / heightDouble.pow(heightDouble))
        displayBMIResult(bmi)
    }

    private fun validateDataAndShowErrors(height: String, weight: String): Boolean {
        if (height.isEmpty()) {
            showHideError(textInputLayoutHeight)
        } else if (weight.isEmpty()) {
            showHideError(textInputLayoutWeight)
        }
        return height.isNotEmpty() && weight.isNotEmpty()
    }

    private fun displayBMIResult(bmi: Double) {
        val bmiText = "Your BIM is: ${getString(R.string.format_string).format(bmi)}"
        textViewResult.apply {
            text = bmiText
            visibility = View.VISIBLE
            clearAnimation()
            startAnimation(AnimationUtils.loadAnimation(context, R.anim.appear_animation))
        }
    }

    private fun showHideError(textInputLayout: TextInputLayout) {
        when (textInputLayout) {
            textInputLayoutHeight -> textInputLayout.error = getString(R.string.height_error)
            textInputLayoutWeight -> textInputLayout.error = getString(R.string.weight_error)
        }
        Handler().postDelayed({ textInputLayout.error = "" }, 3000)
    }

}
