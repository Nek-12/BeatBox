package com.nek.beatbox

private const val WAV_EXTENSION = ".wav"
class Sound(val assetPath: String) {
    val name = assetPath.split("/").last().removeSuffix(WAV_EXTENSION)
}
