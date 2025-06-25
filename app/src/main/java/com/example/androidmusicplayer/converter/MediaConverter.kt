package com.example.androidmusicplayer.converter

import android.content.Context
import android.net.Uri
import android.util.Log
import com.arthenica.mobileffmpeg.Config
import com.arthenica.mobileffmpeg.FFmpeg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class MediaConverter(private val context: Context) {
    
    companion object {
        private const val TAG = "MediaConverter"
        
        // Audio formats
        const val FORMAT_MP3 = "mp3"
        const val FORMAT_AAC = "aac"
        const val FORMAT_FLAC = "flac"
        const val FORMAT_WAV = "wav"
        const val FORMAT_OGG = "ogg"
        const val FORMAT_M4A = "m4a"
        
        // Video formats (for extraction)
        const val FORMAT_MP4 = "mp4"
        const val FORMAT_AVI = "avi"
        const val FORMAT_MKV = "mkv"
        const val FORMAT_MOV = "mov"
        const val FORMAT_WMV = "wmv"
        const val FORMAT_FLV = "flv"
        const val FORMAT_WEBM = "webm"
        
        // Quality presets
        const val QUALITY_LOW = "low"
        const val QUALITY_MEDIUM = "medium"
        const val QUALITY_HIGH = "high"
        const val QUALITY_LOSSLESS = "lossless"
    }
    
    interface ConversionCallback {
        fun onProgress(progress: Int)
        fun onSuccess(outputPath: String)
        fun onError(error: String)
    }
    
    suspend fun convertAudioToAudio(
        inputPath: String,
        outputPath: String,
        outputFormat: String,
        quality: String = QUALITY_MEDIUM,
        callback: ConversionCallback
    ): Boolean = withContext(Dispatchers.IO) {
        try {
            val command = buildAudioToAudioCommand(inputPath, outputPath, outputFormat, quality)
            return@withContext executeFFmpegCommand(command, callback)
        } catch (e: Exception) {
            Log.e(TAG, "Error converting audio to audio", e)
            callback.onError("Conversion failed: ${e.message}")
            false
        }
    }
    
    suspend fun convertVideoToAudio(
        inputPath: String,
        outputPath: String,
        outputFormat: String,
        quality: String = QUALITY_MEDIUM,
        callback: ConversionCallback
    ): Boolean = withContext(Dispatchers.IO) {
        try {
            val command = buildVideoToAudioCommand(inputPath, outputPath, outputFormat, quality)
            return@withContext executeFFmpegCommand(command, callback)
        } catch (e: Exception) {
            Log.e(TAG, "Error converting video to audio", e)
            callback.onError("Conversion failed: ${e.message}")
            false
        }
    }
    
    suspend fun extractAudioFromVideo(
        inputPath: String,
        outputPath: String,
        startTime: String? = null,
        duration: String? = null,
        callback: ConversionCallback
    ): Boolean = withContext(Dispatchers.IO) {
        try {
            val command = buildExtractAudioCommand(inputPath, outputPath, startTime, duration)
            return@withContext executeFFmpegCommand(command, callback)
        } catch (e: Exception) {
            Log.e(TAG, "Error extracting audio from video", e)
            callback.onError("Extraction failed: ${e.message}")
            false
        }
    }
    
    suspend fun changeAudioBitrate(
        inputPath: String,
        outputPath: String,
        bitrate: String,
        callback: ConversionCallback
    ): Boolean = withContext(Dispatchers.IO) {
        try {
            val command = buildBitrateChangeCommand(inputPath, outputPath, bitrate)
            return@withContext executeFFmpegCommand(command, callback)
        } catch (e: Exception) {
            Log.e(TAG, "Error changing audio bitrate", e)
            callback.onError("Bitrate change failed: ${e.message}")
            false
        }
    }
    
    suspend fun trimAudio(
        inputPath: String,
        outputPath: String,
        startTime: String,
        duration: String,
        callback: ConversionCallback
    ): Boolean = withContext(Dispatchers.IO) {
        try {
            val command = buildTrimCommand(inputPath, outputPath, startTime, duration)
            return@withContext executeFFmpegCommand(command, callback)
        } catch (e: Exception) {
            Log.e(TAG, "Error trimming audio", e)
            callback.onError("Trim failed: ${e.message}")
            false
        }
    }
    
    suspend fun mergeAudioFiles(
        inputPaths: List<String>,
        outputPath: String,
        callback: ConversionCallback
    ): Boolean = withContext(Dispatchers.IO) {
        try {
            val command = buildMergeCommand(inputPaths, outputPath)
            return@withContext executeFFmpegCommand(command, callback)
        } catch (e: Exception) {
            Log.e(TAG, "Error merging audio files", e)
            callback.onError("Merge failed: ${e.message}")
            false
        }
    }
    
    private fun buildAudioToAudioCommand(
        inputPath: String,
        outputPath: String,
        outputFormat: String,
        quality: String
    ): String {
        val qualityParams = getQualityParameters(outputFormat, quality)
        return "-i \"$inputPath\" $qualityParams \"$outputPath\""
    }
    
    private fun buildVideoToAudioCommand(
        inputPath: String,
        outputPath: String,
        outputFormat: String,
        quality: String
    ): String {
        val qualityParams = getQualityParameters(outputFormat, quality)
        return "-i \"$inputPath\" -vn $qualityParams \"$outputPath\""
    }
    
    private fun buildExtractAudioCommand(
        inputPath: String,
        outputPath: String,
        startTime: String?,
        duration: String?
    ): String {
        var command = "-i \"$inputPath\""
        
        startTime?.let { command += " -ss $it" }
        duration?.let { command += " -t $it" }
        
        command += " -vn -acodec copy \"$outputPath\""
        return command
    }
    
    private fun buildBitrateChangeCommand(
        inputPath: String,
        outputPath: String,
        bitrate: String
    ): String {
        return "-i \"$inputPath\" -b:a $bitrate \"$outputPath\""
    }
    
    private fun buildTrimCommand(
        inputPath: String,
        outputPath: String,
        startTime: String,
        duration: String
    ): String {
        return "-i \"$inputPath\" -ss $startTime -t $duration -c copy \"$outputPath\""
    }
    
    private fun buildMergeCommand(
        inputPaths: List<String>,
        outputPath: String
    ): String {
        val inputs = inputPaths.joinToString(" ") { "-i \"$it\"" }
        val filterComplex = "concat=n=${inputPaths.size}:v=0:a=1[out]"
        return "$inputs -filter_complex \"$filterComplex\" -map \"[out]\" \"$outputPath\""
    }
    
    private fun getQualityParameters(format: String, quality: String): String {
        return when (format.lowercase()) {
            FORMAT_MP3 -> when (quality) {
                QUALITY_LOW -> "-codec:a libmp3lame -b:a 128k"
                QUALITY_MEDIUM -> "-codec:a libmp3lame -b:a 192k"
                QUALITY_HIGH -> "-codec:a libmp3lame -b:a 320k"
                QUALITY_LOSSLESS -> "-codec:a libmp3lame -b:a 320k"
                else -> "-codec:a libmp3lame -b:a 192k"
            }
            FORMAT_AAC -> when (quality) {
                QUALITY_LOW -> "-codec:a aac -b:a 128k"
                QUALITY_MEDIUM -> "-codec:a aac -b:a 192k"
                QUALITY_HIGH -> "-codec:a aac -b:a 256k"
                QUALITY_LOSSLESS -> "-codec:a aac -b:a 320k"
                else -> "-codec:a aac -b:a 192k"
            }
            FORMAT_FLAC -> "-codec:a flac"
            FORMAT_WAV -> "-codec:a pcm_s16le"
            FORMAT_OGG -> when (quality) {
                QUALITY_LOW -> "-codec:a libvorbis -q:a 3"
                QUALITY_MEDIUM -> "-codec:a libvorbis -q:a 5"
                QUALITY_HIGH -> "-codec:a libvorbis -q:a 7"
                QUALITY_LOSSLESS -> "-codec:a libvorbis -q:a 10"
                else -> "-codec:a libvorbis -q:a 5"
            }
            FORMAT_M4A -> when (quality) {
                QUALITY_LOW -> "-codec:a aac -b:a 128k"
                QUALITY_MEDIUM -> "-codec:a aac -b:a 192k"
                QUALITY_HIGH -> "-codec:a aac -b:a 256k"
                QUALITY_LOSSLESS -> "-codec:a aac -b:a 320k"
                else -> "-codec:a aac -b:a 192k"
            }
            else -> "-codec:a libmp3lame -b:a 192k"
        }
    }
    
    private suspend fun executeFFmpegCommand(
        command: String,
        callback: ConversionCallback
    ): Boolean = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "Executing FFmpeg command: $command")
            
            // Set up progress callback
            Config.enableStatisticsCallback { statistics ->
                val progress = (statistics.time * 100 / statistics.size).toInt()
                callback.onProgress(progress.coerceIn(0, 100))
            }
            
            val rc = FFmpeg.execute(command)
            
            if (rc == Config.RETURN_CODE_SUCCESS) {
                Log.d(TAG, "FFmpeg command completed successfully")
                callback.onProgress(100)
                true
            } else {
                val error = "FFmpeg command failed with return code: $rc"
                Log.e(TAG, error)
                callback.onError(error)
                false
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception during FFmpeg execution", e)
            callback.onError("Execution failed: ${e.message}")
            false
        }
    }
    
    fun getSupportedAudioFormats(): List<String> {
        return listOf(FORMAT_MP3, FORMAT_AAC, FORMAT_FLAC, FORMAT_WAV, FORMAT_OGG, FORMAT_M4A)
    }
    
    fun getSupportedVideoFormats(): List<String> {
        return listOf(FORMAT_MP4, FORMAT_AVI, FORMAT_MKV, FORMAT_MOV, FORMAT_WMV, FORMAT_FLV, FORMAT_WEBM)
    }
    
    fun getQualityOptions(): List<String> {
        return listOf(QUALITY_LOW, QUALITY_MEDIUM, QUALITY_HIGH, QUALITY_LOSSLESS)
    }
    
    fun isFormatSupported(format: String): Boolean {
        return getSupportedAudioFormats().contains(format.lowercase()) ||
                getSupportedVideoFormats().contains(format.lowercase())
    }
    
    fun getFileExtension(path: String): String {
        return File(path).extension.lowercase()
    }
    
    fun isAudioFile(path: String): Boolean {
        return getSupportedAudioFormats().contains(getFileExtension(path))
    }
    
    fun isVideoFile(path: String): Boolean {
        return getSupportedVideoFormats().contains(getFileExtension(path))
    }
    
    fun generateOutputPath(inputPath: String, outputFormat: String, suffix: String = ""): String {
        val inputFile = File(inputPath)
        val nameWithoutExtension = inputFile.nameWithoutExtension
        val outputDir = inputFile.parent ?: context.getExternalFilesDir(null)?.absolutePath ?: ""
        return "$outputDir/${nameWithoutExtension}$suffix.$outputFormat"
    }
    
    fun cancelConversion() {
        FFmpeg.cancel()
    }
    
    fun getFFmpegVersion(): String {
        return Config.getFFmpegVersion()
    }
}

