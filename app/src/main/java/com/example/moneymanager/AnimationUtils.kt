package com.example.moneymanager

import android.view.ViewPropertyAnimator
import com.example.moneymanager.databinding.FragmentAddingBinding
import com.example.moneymanager.databinding.FragmentIncomeBinding

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


fun dataViewsAnimationToday (binding: FragmentIncomeBinding) {
    val firstAnimation = binding.constraintLayoutToday.animate().apply {
        duration = 100
        scaleX(1.05f)  // масштабування по осі X
        scaleY(1.05f)  // масштабування по осі Y
    }.withEndAction {
        binding.constraintLayoutToday.animate().apply {
            duration = 100
            scaleX(1.0f)
            scaleY(1.0f)
        }.start()
    }
}

fun dataViewsAnimationYesterday (binding: FragmentIncomeBinding) {
    val firstAnimation = binding.constraintLayoutYesterday.animate().apply {
        duration = 100
        scaleX(1.05f)  // масштабування по осі X
        scaleY(1.05f)  // масштабування по осі Y
    }.withEndAction {
        binding.constraintLayoutYesterday.animate().apply {
            duration = 100
            scaleX(1.0f)
            scaleY(1.0f)
        }.start()
    }
}

fun dataViewsAnimationTwoDaysAgo (binding: FragmentIncomeBinding) {
    val firstAnimation = binding.constraintLayout2daysAgo.animate().apply {
        duration = 100
        scaleX(1.05f)  // масштабування по осі X
        scaleY(1.05f)  // масштабування по осі Y
    }.withEndAction {
        binding.constraintLayout2daysAgo.animate().apply {
            duration = 100
            scaleX(1.0f)
            scaleY(1.0f)
        }.start()
    }
}


