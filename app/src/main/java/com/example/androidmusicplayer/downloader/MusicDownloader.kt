package com.example.androidmusicplayer.downloader

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.webkit.URLUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class MusicDownloader(private val context: Context) {
    
    companion object {
        private const val TAG = "MusicDownloader"
        private const val DOWNLOAD_DIRECTORY = "MusicPlayer/Downloads"
    }
    
    private val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    private val activeDownloads = mutableMapOf<Long, DownloadInfo>()
    
    interface DownloadCallback {
        fun onProgress(downloadId: Long, progress: Int)
        fun onSuccess(downloadId: Long, filePath: String)
        fun onError(downloadId: Long, error: String)
        fun onPaused(downloadId: Long)
        fun onCancelled(downloadId: Long)
    }
    
    data class DownloadInfo(
        val id: Long,
        val url: String,
        val fileName: String,
        val title: String,
        val artist: String? = null,
        val callback: DownloadCallback?
    )
    
    private val downloadReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val downloadId = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1) ?: -1
            if (downloadId != -1L && activeDownloads.containsKey(downloadId)) {
                handleDownloadComplete(downloadId)
            }
        }
    }
    
    init {
        // Register broadcast receiver for download completion
        val filter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        context.registerReceiver(downloadReceiver, filter)
    }
    
    fun downloadFromUrl(
        url: String,
        fileName: String,
        title: String,
        artist: String? = null,
        callback: DownloadCallback? = null
    ): Long {
        try {
            if (!URLUtil.isValidUrl(url)) {
                callback?.onError(-1, "Invalid URL")
                return -1
            }
            
            val request = DownloadManager.Request(Uri.parse(url)).apply {
                setTitle(title)
                setDescription("Downloading ${artist?.let { "$it - " } ?: ""}$title")
                setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC, "$DOWNLOAD_DIRECTORY/$fileName")
                setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                setAllowedOverRoaming(false)
            }
            
            val downloadId = downloadManager.enqueue(request)
            
            val downloadInfo = DownloadInfo(
                id = downloadId,
                url = url,
                fileName = fileName,
                title = title,
                artist = artist,
                callback = callback
            )
            
            activeDownloads[downloadId] = downloadInfo
            
            Log.d(TAG, "Download started: $title (ID: $downloadId)")
            return downloadId
            
        } catch (e: Exception) {
            Log.e(TAG, "Error starting download", e)
            callback?.onError(-1, "Failed to start download: ${e.message}")
            return -1
        }
    }
    
    suspend fun downloadCurrentlyPlaying(
        currentSong: String,
        artist: String? = null,
        callback: DownloadCallback? = null
    ): Long = withContext(Dispatchers.IO) {
        try {
            // This is a placeholder implementation
            // In a real app, you would need to:
            // 1. Get the current playing song's metadata
            // 2. Search for download sources (YouTube, SoundCloud, etc.)
            // 3. Extract download URLs using appropriate APIs
            
            // For demonstration, we'll simulate a download
            val fileName = "${currentSong.replace(" ", "_")}.mp3"
            val simulatedUrl = "https://example.com/music/${fileName}"
            
            // In reality, you would use services like:
            // - YouTube Data API + youtube-dl equivalent
            // - SoundCloud API
            // - Other music streaming APIs
            
            callback?.onError(-1, "Download feature requires integration with music streaming APIs")
            return@withContext -1L
            
        } catch (e: Exception) {
            Log.e(TAG, "Error downloading currently playing song", e)
            callback?.onError(-1, "Failed to download: ${e.message}")
            return@withContext -1L
        }
    }
    
    suspend fun downloadFromStreamingService(
        serviceUrl: String,
        callback: DownloadCallback? = null
    ): Long = withContext(Dispatchers.IO) {
        try {
            // Extract metadata and download URL from streaming service
            val metadata = extractMetadataFromUrl(serviceUrl)
            val downloadUrl = extractDownloadUrl(serviceUrl)
            
            if (downloadUrl != null && metadata != null) {
                val fileName = "${metadata.title.replace(" ", "_")}.${metadata.format}"
                return@withContext downloadFromUrl(
                    url = downloadUrl,
                    fileName = fileName,
                    title = metadata.title,
                    artist = metadata.artist,
                    callback = callback
                )
            } else {
                callback?.onError(-1, "Could not extract download information")
                return@withContext -1L
            }
            
        } catch (e: Exception) {
            Log.e(TAG, "Error downloading from streaming service", e)
            callback?.onError(-1, "Failed to download: ${e.message}")
            return@withContext -1L
        }
    }
    
    private suspend fun extractMetadataFromUrl(url: String): MediaMetadata? = withContext(Dispatchers.IO) {
        try {
            // This would use libraries like youtube-dl, yt-dlp, or specific APIs
            // to extract metadata from streaming services
            
            when {
                url.contains("youtube.com") || url.contains("youtu.be") -> {
                    // Extract YouTube metadata
                    extractYouTubeMetadata(url)
                }
                url.contains("soundcloud.com") -> {
                    // Extract SoundCloud metadata
                    extractSoundCloudMetadata(url)
                }
                else -> {
                    // Generic metadata extraction
                    extractGenericMetadata(url)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error extracting metadata", e)
            null
        }
    }
    
    private suspend fun extractDownloadUrl(url: String): String? = withContext(Dispatchers.IO) {
        try {
            // This would use appropriate libraries or APIs to extract actual download URLs
            // For security and legal reasons, this is a placeholder implementation
            
            when {
                url.contains("youtube.com") || url.contains("youtu.be") -> {
                    // Use YouTube Data API + youtube-dl equivalent
                    extractYouTubeDownloadUrl(url)
                }
                url.contains("soundcloud.com") -> {
                    // Use SoundCloud API
                    extractSoundCloudDownloadUrl(url)
                }
                else -> {
                    // Direct URL or other services
                    if (URLUtil.isValidUrl(url)) url else null
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error extracting download URL", e)
            null
        }
    }
    
    private fun extractYouTubeMetadata(url: String): MediaMetadata? {
        // Placeholder - would use YouTube Data API
        return null
    }
    
    private fun extractSoundCloudMetadata(url: String): MediaMetadata? {
        // Placeholder - would use SoundCloud API
        return null
    }
    
    private fun extractGenericMetadata(url: String): MediaMetadata? {
        // Placeholder - would parse HTML or use other methods
        return null
    }
    
    private fun extractYouTubeDownloadUrl(url: String): String? {
        // Placeholder - would use youtube-dl equivalent library
        return null
    }
    
    private fun extractSoundCloudDownloadUrl(url: String): String? {
        // Placeholder - would use SoundCloud API
        return null
    }
    
    fun pauseDownload(downloadId: Long) {
        // DownloadManager doesn't support pause/resume directly
        // You would need to implement custom download logic for this
        activeDownloads[downloadId]?.callback?.onPaused(downloadId)
    }
    
    fun resumeDownload(downloadId: Long) {
        // Custom implementation needed for pause/resume
    }
    
    fun cancelDownload(downloadId: Long) {
        try {
            downloadManager.remove(downloadId)
            activeDownloads[downloadId]?.callback?.onCancelled(downloadId)
            activeDownloads.remove(downloadId)
            Log.d(TAG, "Download cancelled: $downloadId")
        } catch (e: Exception) {
            Log.e(TAG, "Error cancelling download", e)
        }
    }
    
    fun getDownloadProgress(downloadId: Long): Int {
        try {
            val query = DownloadManager.Query().setFilterById(downloadId)
            val cursor: Cursor = downloadManager.query(query)
            
            if (cursor.moveToFirst()) {
                val bytesDownloaded = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                val bytesTotal = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                
                cursor.close()
                
                return if (bytesTotal > 0) {
                    ((bytesDownloaded * 100) / bytesTotal).toInt()
                } else {
                    0
                }
            }
            cursor.close()
        } catch (e: Exception) {
            Log.e(TAG, "Error getting download progress", e)
        }
        return 0
    }
    
    fun getDownloadStatus(downloadId: Long): Int {
        try {
            val query = DownloadManager.Query().setFilterById(downloadId)
            val cursor: Cursor = downloadManager.query(query)
            
            if (cursor.moveToFirst()) {
                val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                cursor.close()
                return status
            }
            cursor.close()
        } catch (e: Exception) {
            Log.e(TAG, "Error getting download status", e)
        }
        return DownloadManager.STATUS_FAILED
    }
    
    private fun handleDownloadComplete(downloadId: Long) {
        val downloadInfo = activeDownloads[downloadId] ?: return
        
        try {
            val query = DownloadManager.Query().setFilterById(downloadId)
            val cursor: Cursor = downloadManager.query(query)
            
            if (cursor.moveToFirst()) {
                val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                val localUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI))
                
                when (status) {
                    DownloadManager.STATUS_SUCCESSFUL -> {
                        Log.d(TAG, "Download completed successfully: ${downloadInfo.title}")
                        downloadInfo.callback?.onSuccess(downloadId, localUri ?: "")
                    }
                    DownloadManager.STATUS_FAILED -> {
                        val reason = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_REASON))
                        Log.e(TAG, "Download failed: ${downloadInfo.title}, reason: $reason")
                        downloadInfo.callback?.onError(downloadId, "Download failed (reason: $reason)")
                    }
                }
            }
            cursor.close()
        } catch (e: Exception) {
            Log.e(TAG, "Error handling download completion", e)
            downloadInfo.callback?.onError(downloadId, "Error processing completed download")
        } finally {
            activeDownloads.remove(downloadId)
        }
    }
    
    fun getAllDownloads(): List<DownloadInfo> {
        return activeDownloads.values.toList()
    }
    
    fun getDownloadDirectory(): File {
        return File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), DOWNLOAD_DIRECTORY)
    }
    
    fun cleanup() {
        try {
            context.unregisterReceiver(downloadReceiver)
        } catch (e: Exception) {
            Log.e(TAG, "Error unregistering receiver", e)
        }
    }
    
    data class MediaMetadata(
        val title: String,
        val artist: String?,
        val duration: Long?,
        val format: String = "mp3"
    )
}

