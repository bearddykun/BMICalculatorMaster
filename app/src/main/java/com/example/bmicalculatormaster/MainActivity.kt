package com.example.bmicalculatormaster

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //onStart is called after the application is started and UI is becomes visible to user
    override fun onStart() {
        super.onStart()
        buttonCalculateBMI.setOnClickListener {
            calculateBMI(
                heightEditText.text.toString(),
                weightEditText.text.toString()
            )
        }
    }

    //calculates bmi
    private fun calculateBMI(height: String, weight: String) {
        if (!validInputData(height, weight)) {
            return
        }
        val heightDouble = height.toDouble() / 100
        val bmi = weight.toDouble() / (heightDouble * heightDouble)
        displayBMIResult(bmi)
    }

    //shows error if input field is empty
    private fun validInputData(height: String, weight: String): Boolean {
        if (height.isEmpty()) {
            showHideError(textInputLayoutHeight)
        } else if (weight.isEmpty()) {
            showHideError(textInputLayoutWeight)
        }
        return height.isNotEmpty() && weight.isNotEmpty()
    }

    private fun displayBMIResult(bmi: Double) {
        //formatting string to display bmi with only 2 digits after comma
        val bmiText =
            "${getString(R.string.format_string).format(bmi)}\n${getWeightDefinition(
                bmi
            )}"
        //inside "apply" we don't need to call object itself anymore and can set his properties directly
        textViewResult.apply {
            text = bmiText
            visibility = View.VISIBLE
            clearAnimation()
            startAnimation(AnimationUtils.loadAnimation(context, R.anim.appear_animation))
        }
    }

    private fun showHideError(textInputLayout: TextInputLayout) {
        //Find empty text and display error
        when (textInputLayout) {
            textInputLayoutHeight -> textInputLayout.error = getString(R.string.height_error)
            textInputLayoutWeight -> textInputLayout.error = getString(R.string.weight_error)
        }
        //Hide error in 3 seconds
        Handler().postDelayed({ textInputLayout.error = "" }, 3000)
    }

    //Get weight Definition from BMI
    private fun getWeightDefinition(bmi: Double): String {
        return when (bmi) {
            in 0.0..18.5 -> getString(R.string.underweight)
            in 18.5..24.9 -> getString(R.string.normal_weight)
            in 25.0..29.9 -> getString(R.string.overweight)
            else -> getString(R.string.obesity)
        }
    }
}
