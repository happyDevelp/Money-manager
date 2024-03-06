package com.example.moneymanager.Utils

import com.example.moneymanager.databinding.FragmentAddingBinding
import com.example.moneymanager.databinding.FragmentIncomeBinding

/** ANIMATIONS **/
fun coinAnimation1(binding: FragmentAddingBinding) {

    binding.coinIcon.animate().apply {
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
    }
}

fun coinAnimation2(binding: FragmentAddingBinding) {
    binding.coinIcon.animate().apply {
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
}

fun coinAnimation3(binding: FragmentAddingBinding) {
    binding.apply {

        coinIcon.animate().apply {
            duration = 600
            translationYBy(-25f)
            rotationYBy(1080f)
            scaleX(1.25f)
            scaleY(1.25f)

        }.withEndAction {
            coinIcon.animate().apply {
                duration = 900
                translationYBy(25f)
                rotationYBy(-1080f)
                scaleX(1.00f)
                scaleY(1.00f)

            }.withEndAction {
                coinIcon.animate().apply {
                    duration = 150
                    scaleX(1.20f)
                    scaleY(1.20f)

                }.withEndAction {
                    coinIcon.animate().apply {
                        duration = 200
                        scaleX(1.00f)
                        scaleY(1.00f)
                    }
                }.start()
            }
        }
    }
}

fun coinAnimationUp(binding: FragmentAddingBinding) {
    val firstAnimation = binding.coinIcon.animate().apply {
        duration = 150
        scaleX(1.25f)  // x-axis scaling
        scaleY(1.25f)  // y-axis scaling
    }.start()
}

fun coinAnimationDown(binding: FragmentAddingBinding) {
    val firstAnimation = binding.coinIcon.animate().apply {
        duration = 150
        scaleX(1.0f)  // x-axis scaling
        scaleY(1.0f)  // y-axis scaling

    }.start()


}

fun dataViewsAnimationToday(binding: FragmentIncomeBinding) {
    val firstAnimation = binding.todayContainer.animate().apply {
        duration = 100
        scaleX(1.05f)  // масштабування по осі X
        scaleY(1.05f)  // масштабування по осі Y
    }.withEndAction {
        binding.todayContainer.animate().apply {
            duration = 100
            scaleX(1.0f)
            scaleY(1.0f)
        }.start()
    }
}

fun dataViewsAnimationYesterday(binding: FragmentIncomeBinding) {
    val firstAnimation = binding.yesterdayContainer.animate().apply {
        duration = 100
        scaleX(1.05f)  // масштабування по осі X
        scaleY(1.05f)  // масштабування по осі Y
    }.withEndAction {
        binding.yesterdayContainer.animate().apply {
            duration = 100
            scaleX(1.0f)
            scaleY(1.0f)
        }.start()
    }
}

fun dataViewsAnimationTwoDaysAgo(binding: FragmentIncomeBinding) {
    val firstAnimation = binding.twoDaysAgoContainer.animate().apply {
        duration = 100
        scaleX(1.05f)  // масштабування по осі X
        scaleY(1.05f)  // масштабування по осі Y
    }.withEndAction {
        binding.twoDaysAgoContainer.animate().apply {
            duration = 100
            scaleX(1.0f)
            scaleY(1.0f)
        }.start()
    }
}


