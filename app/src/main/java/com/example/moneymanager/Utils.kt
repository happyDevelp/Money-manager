package com.example.moneymanager

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import android.view.ViewPropertyAnimator
import com.example.moneymanager.databinding.FragmentAddingBinding

/** ANIMATIONS **/
fun coinAnimation(binding: FragmentAddingBinding) {
    val animationsList = mutableListOf<ViewPropertyAnimator>()

    val firstAnimation = binding.coinIcon.animate().apply {
        duration = 700
        scaleX(1.30f)  // масштабування по осі X
        scaleY(1.30f)  // масштабування по осі Y
        rotationYBy(1080f)

    }.withEndAction {
        binding.coinIcon.animate().apply {
            duration = 700
            scaleX(1.0f)
            scaleY(1.0f)
            rotationYBy(720f)
        }.start()
    }

   /* val secondAnimation = binding.coinIcon.animate().apply {
        duration = 700
        scaleX(1.30f)  // масштабування по осі X
        scaleY(1.30f)  // масштабування по осі Y
        rotationXBy(1080f)

    }.withEndAction {
        binding.coinIcon.animate().apply {
            duration = 700
            scaleX(1.0f)
            scaleY(1.0f)
            rotationXBy(720f)
        }.start()
    }*/

    animationsList.add(firstAnimation)
  /*  animationsList.add(secondAnimation)*/
}


// Add these extension functions to an empty kotlin file


