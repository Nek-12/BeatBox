package com.nek.beatbox

import java.util.*

private const val WAV_EXTENSION = ".wav"
class Sound(val assetPath: String, var soundId: Int? = null) {
    val name = assetPath.split("/").last()
        .removeSuffix(WAV_EXTENSION)
        .substringAfter("_")
        .toUpperCase(Locale.ROOT)
}
