package com.example.androidmusicplayer.audio

import android.media.audiofx.AudioEffect
import android.media.audiofx.BassBoost
import android.media.audiofx.Equalizer
import android.media.audiofx.EnvironmentalReverb
import android.media.audiofx.PresetReverb
import android.media.audiofx.Virtualizer
import android.util.Log

class AudioEffectsController(private val audioSessionId: Int) {
    
    private var equalizer: Equalizer? = null
    private var bassBoost: BassBoost? = null
    private var virtualizer: Virtualizer? = null
    private var presetReverb: PresetReverb? = null
    private var environmentalReverb: EnvironmentalReverb? = null
    
    companion object {
        private const val TAG = "AudioEffectsController"
        
        // Equalizer presets
        const val PRESET_CUSTOM = -1
        const val PRESET_POP = 0
        const val PRESET_FOLK = 1
        const val PRESET_ROCK = 2
        const val PRESET_DANCE = 3
        
        // Reverb presets
        const val REVERB_NONE = PresetReverb.PRESET_NONE
        const val REVERB_SMALLROOM = PresetReverb.PRESET_SMALLROOM
        const val REVERB_MEDIUMROOM = PresetReverb.PRESET_MEDIUMROOM
        const val REVERB_LARGEROOM = PresetReverb.PRESET_LARGEROOM
        const val REVERB_MEDIUMHALL = PresetReverb.PRESET_MEDIUMHALL
        const val REVERB_LARGEHALL = PresetReverb.PRESET_LARGEHALL
        const val REVERB_PLATE = PresetReverb.PRESET_PLATE
    }
    
    init {
        initializeEffects()
    }
    
    private fun initializeEffects() {
        try {
            // Initialize Equalizer
            equalizer = Equalizer(0, audioSessionId).apply {
                enabled = false
            }
            
            // Initialize Bass Boost
            bassBoost = BassBoost(0, audioSessionId).apply {
                enabled = false
            }
            
            // Initialize Virtualizer
            virtualizer = Virtualizer(0, audioSessionId).apply {
                enabled = false
            }
            
            // Initialize Preset Reverb
            presetReverb = PresetReverb(0, audioSessionId).apply {
                enabled = false
            }
            
            Log.d(TAG, "Audio effects initialized successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing audio effects", e)
        }
    }
    
    // Equalizer methods
    fun setEqualizerEnabled(enabled: Boolean) {
        equalizer?.enabled = enabled
    }
    
    fun isEqualizerEnabled(): Boolean {
        return equalizer?.enabled ?: false
    }
    
    fun getNumberOfBands(): Short {
        return equalizer?.numberOfBands ?: 0
    }
    
    fun getBandFreqRange(band: Short): IntArray? {
        return equalizer?.getBandFreqRange(band)
    }
    
    fun getBandLevel(band: Short): Short {
        return equalizer?.getBandLevel(band) ?: 0
    }
    
    fun setBandLevel(band: Short, level: Short) {
        equalizer?.setBandLevel(band, level)
    }
    
    fun getBandLevelRange(): ShortArray? {
        return equalizer?.bandLevelRange
    }
    
    fun getCenterFreq(band: Short): Int {
        return equalizer?.getCenterFreq(band) ?: 0
    }
    
    fun applyEqualizerPreset(preset: Int) {
        when (preset) {
            PRESET_POP -> applyPopPreset()
            PRESET_FOLK -> applyFolkPreset()
            PRESET_ROCK -> applyRockPreset()
            PRESET_DANCE -> applyDancePreset()
        }
    }
    
    private fun applyPopPreset() {
        equalizer?.let { eq ->
            val numberOfBands = eq.numberOfBands
            val bandLevelRange = eq.bandLevelRange
            val maxLevel = bandLevelRange[1]
            val minLevel = bandLevelRange[0]
            
            // Pop preset: slight bass boost, enhanced mids, reduced highs
            for (i in 0 until numberOfBands) {
                val level = when (i) {
                    0 -> (maxLevel * 0.3).toInt().toShort() // 60Hz
                    1 -> (maxLevel * 0.2).toInt().toShort() // 230Hz
                    2 -> (maxLevel * 0.4).toInt().toShort() // 910Hz
                    3 -> (maxLevel * 0.1).toInt().toShort() // 3.6KHz
                    4 -> (minLevel * 0.2).toInt().toShort() // 14KHz
                    else -> 0.toShort()
                }
                eq.setBandLevel(i.toShort(), level)
            }
        }
    }
    
    private fun applyFolkPreset() {
        equalizer?.let { eq ->
            val numberOfBands = eq.numberOfBands
            val bandLevelRange = eq.bandLevelRange
            val maxLevel = bandLevelRange[1]
            
            // Folk preset: warm, natural sound
            for (i in 0 until numberOfBands) {
                val level = when (i) {
                    0 -> (maxLevel * 0.1).toInt().toShort() // 60Hz
                    1 -> (maxLevel * 0.3).toInt().toShort() // 230Hz
                    2 -> (maxLevel * 0.2).toInt().toShort() // 910Hz
                    3 -> (maxLevel * 0.3).toInt().toShort() // 3.6KHz
                    4 -> (maxLevel * 0.2).toInt().toShort() // 14KHz
                    else -> 0.toShort()
                }
                eq.setBandLevel(i.toShort(), level)
            }
        }
    }
    
    private fun applyRockPreset() {
        equalizer?.let { eq ->
            val numberOfBands = eq.numberOfBands
            val bandLevelRange = eq.bandLevelRange
            val maxLevel = bandLevelRange[1]
            
            // Rock preset: enhanced bass and treble
            for (i in 0 until numberOfBands) {
                val level = when (i) {
                    0 -> (maxLevel * 0.5).toInt().toShort() // 60Hz
                    1 -> (maxLevel * 0.2).toInt().toShort() // 230Hz
                    2 -> (maxLevel * 0.1).toInt().toShort() // 910Hz
                    3 -> (maxLevel * 0.3).toInt().toShort() // 3.6KHz
                    4 -> (maxLevel * 0.4).toInt().toShort() // 14KHz
                    else -> 0.toShort()
                }
                eq.setBandLevel(i.toShort(), level)
            }
        }
    }
    
    private fun applyDancePreset() {
        equalizer?.let { eq ->
            val numberOfBands = eq.numberOfBands
            val bandLevelRange = eq.bandLevelRange
            val maxLevel = bandLevelRange[1]
            
            // Dance preset: heavy bass, enhanced highs
            for (i in 0 until numberOfBands) {
                val level = when (i) {
                    0 -> (maxLevel * 0.6).toInt().toShort() // 60Hz
                    1 -> (maxLevel * 0.4).toInt().toShort() // 230Hz
                    2 -> (maxLevel * 0.2).toInt().toShort() // 910Hz
                    3 -> (maxLevel * 0.4).toInt().toShort() // 3.6KHz
                    4 -> (maxLevel * 0.5).toInt().toShort() // 14KHz
                    else -> 0.toShort()
                }
                eq.setBandLevel(i.toShort(), level)
            }
        }
    }
    
    // Bass Boost methods
    fun setBassBoostEnabled(enabled: Boolean) {
        bassBoost?.enabled = enabled
    }
    
    fun isBassBoostEnabled(): Boolean {
        return bassBoost?.enabled ?: false
    }
    
    fun setBassBoostStrength(strength: Short) {
        bassBoost?.setStrength(strength)
    }
    
    fun getBassBoostStrength(): Short {
        return bassBoost?.roundedStrength ?: 0
    }
    
    // Virtualizer methods
    fun setVirtualizerEnabled(enabled: Boolean) {
        virtualizer?.enabled = enabled
    }
    
    fun isVirtualizerEnabled(): Boolean {
        return virtualizer?.enabled ?: false
    }
    
    fun setVirtualizerStrength(strength: Short) {
        virtualizer?.setStrength(strength)
    }
    
    fun getVirtualizerStrength(): Short {
        return virtualizer?.roundedStrength ?: 0
    }
    
    // Reverb methods
    fun setReverbEnabled(enabled: Boolean) {
        presetReverb?.enabled = enabled
    }
    
    fun isReverbEnabled(): Boolean {
        return presetReverb?.enabled ?: false
    }
    
    fun setReverbPreset(preset: Short) {
        presetReverb?.preset = preset
    }
    
    fun getReverbPreset(): Short {
        return presetReverb?.preset ?: REVERB_NONE.toShort()
    }
    
    // 3D Audio Effects (simulated)
    fun enable3DRotate(enabled: Boolean) {
        // Simulate 3D rotate by adjusting virtualizer and reverb
        if (enabled) {
            setVirtualizerEnabled(true)
            setVirtualizerStrength(800)
            setReverbEnabled(true)
            setReverbPreset(REVERB_MEDIUMHALL.toShort())
        }
    }
    
    fun enableToneLow(enabled: Boolean) {
        // Enhance low frequencies
        if (enabled) {
            setBassBoostEnabled(true)
            setBassBoostStrength(700)
        }
    }
    
    fun enableSurroundSound(enabled: Boolean) {
        // Simulate surround sound
        if (enabled) {
            setVirtualizerEnabled(true)
            setVirtualizerStrength(900)
            setReverbEnabled(true)
            setReverbPreset(REVERB_LARGEHALL.toShort())
        }
    }
    
    fun enableMagicSound(enabled: Boolean) {
        // Apply a combination of effects for "magic" sound
        if (enabled) {
            setEqualizerEnabled(true)
            applyEqualizerPreset(PRESET_DANCE)
            setBassBoostEnabled(true)
            setBassBoostStrength(600)
            setVirtualizerEnabled(true)
            setVirtualizerStrength(700)
        }
    }
    
    fun enableLiveTreble(enabled: Boolean) {
        // Enhance treble frequencies for live sound
        if (enabled) {
            setEqualizerEnabled(true)
            equalizer?.let { eq ->
                val numberOfBands = eq.numberOfBands
                val bandLevelRange = eq.bandLevelRange
                val maxLevel = bandLevelRange[1]
                
                // Boost higher frequencies
                for (i in (numberOfBands / 2) until numberOfBands) {
                    eq.setBandLevel(i.toShort(), (maxLevel * 0.4).toInt().toShort())
                }
            }
        }
    }
    
    // Cleanup
    fun release() {
        try {
            equalizer?.release()
            bassBoost?.release()
            virtualizer?.release()
            presetReverb?.release()
            environmentalReverb?.release()
            
            equalizer = null
            bassBoost = null
            virtualizer = null
            presetReverb = null
            environmentalReverb = null
            
            Log.d(TAG, "Audio effects released")
        } catch (e: Exception) {
            Log.e(TAG, "Error releasing audio effects", e)
        }
    }
    
    // Get all current settings
    fun getCurrentSettings(): AudioEffectSettings {
        return AudioEffectSettings(
            equalizerEnabled = isEqualizerEnabled(),
            bassBoostEnabled = isBassBoostEnabled(),
            bassBoostStrength = getBassBoostStrength(),
            virtualizerEnabled = isVirtualizerEnabled(),
            virtualizerStrength = getVirtualizerStrength(),
            reverbEnabled = isReverbEnabled(),
            reverbPreset = getReverbPreset()
        )
    }
    
    // Apply settings
    fun applySettings(settings: AudioEffectSettings) {
        setEqualizerEnabled(settings.equalizerEnabled)
        setBassBoostEnabled(settings.bassBoostEnabled)
        setBassBoostStrength(settings.bassBoostStrength)
        setVirtualizerEnabled(settings.virtualizerEnabled)
        setVirtualizerStrength(settings.virtualizerStrength)
        setReverbEnabled(settings.reverbEnabled)
        setReverbPreset(settings.reverbPreset)
    }
}

data class AudioEffectSettings(
    val equalizerEnabled: Boolean = false,
    val bassBoostEnabled: Boolean = false,
    val bassBoostStrength: Short = 0,
    val virtualizerEnabled: Boolean = false,
    val virtualizerStrength: Short = 0,
    val reverbEnabled: Boolean = false,
    val reverbPreset: Short = 0
)

