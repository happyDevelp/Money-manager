package com.example.moneymanager.Utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.moneymanager.databinding.FragmentAddingBinding

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
    binding.coinIcon.animate().apply {
        duration = 150
        scaleX(1.25f)  // x-axis scaling
        scaleY(1.25f)  // y-axis scaling
    }.start()
}

fun coinAnimationDown(binding: FragmentAddingBinding) {
    binding.coinIcon.animate().apply {
        duration = 150
        scaleX(1.0f)  // x-axis scaling
        scaleY(1.0f)  // y-axis scaling

    }.start()


}

fun dataViewsAnimationToday(binding: FragmentAddingBinding) {
    binding.todayContainer.animate()?.apply {
        duration = 100
        scaleX(1.05f)  // масштабування по осі X
        scaleY(1.05f)  // масштабування по осі Y
    }?.withEndAction {
        binding.todayContainer.animate().apply {
            duration = 100
            scaleX(1.0f)
            scaleY(1.0f)
        }.start()
    }
}

fun dataViewsAnimationYesterday(binding: FragmentAddingBinding) {
    binding.yesterdayContainer.animate()?.apply {
        duration = 100
        scaleX(1.05f)  // масштабування по осі X
        scaleY(1.05f)  // масштабування по осі Y
    }?.withEndAction {
        binding.yesterdayContainer.animate().apply {
            duration = 100
            scaleX(1.0f)
            scaleY(1.0f)
        }.start()
    }
}

fun dataViewsAnimationTwoDaysAgo(binding: FragmentAddingBinding) {
    binding.twoDaysAgoContainer.animate()?.apply {
        duration = 100
        scaleX(1.05f)  // масштабування по осі X
        scaleY(1.05f)  // масштабування по осі Y
    }?.withEndAction {
        binding.twoDaysAgoContainer.animate().apply {
            duration = 100
            scaleX(1.0f)
            scaleY(1.0f)
        }.start()
    }
}

/**
 * Transforms a [LiveData] into [MutableLiveData]
 *
 * @param T type
 * @return [MutableLiveData] emitting the same values
 */
fun <T> LiveData<T>.toMutableLiveData(): MutableLiveData<T> {
    val mediatorLiveData = MediatorLiveData<T>()
    mediatorLiveData.addSource(this) {
        mediatorLiveData.value = it
    }
    return mediatorLiveData
}
