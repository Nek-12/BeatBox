package com.nek.beatbox

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivityViewModel(val beatBox: BeatBox): ViewModel() {

    override fun onCleared() {
        beatBox.release()
    }
}
