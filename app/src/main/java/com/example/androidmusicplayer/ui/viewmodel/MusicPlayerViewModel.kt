package com.example.androidmusicplayer.ui.viewmodel

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.IBinder
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidmusicplayer.repository.MusicRepository
import com.example.androidmusicplayer.repository.Song
import com.example.androidmusicplayer.service.MusicPlayerService
import kotlinx.coroutines.launch

class MusicPlayerViewModel(application: Application) : AndroidViewModel(application) {
    
    private val musicRepository = MusicRepository(application)
    private var musicService: MusicPlayerService? = null
    private var isBound = false
    
    private val _currentSong = MutableLiveData<Song?>()
    val currentSong: LiveData<Song?> = _currentSong
    
    private val _isPlaying = MutableLiveData<Boolean>()
    val isPlaying: LiveData<Boolean> = _isPlaying
    
    private val _currentPosition = MutableLiveData<Long>()
    val currentPosition: LiveData<Long> = _currentPosition
    
    private val _duration = MutableLiveData<Long>()
    val duration: LiveData<Long> = _duration
    
    private val _songs = MutableLiveData<List<Song>>()
    val songs: LiveData<List<Song>> = _songs
    
    private val _albums = MutableLiveData<List<com.example.androidmusicplayer.repository.Album>>()
    val albums: LiveData<List<com.example.androidmusicplayer.repository.Album>> = _albums
    
    private val _artists = MutableLiveData<List<com.example.androidmusicplayer.repository.Artist>>()
    val artists: LiveData<List<com.example.androidmusicplayer.repository.Artist>> = _artists
    
    private val _currentPlaylist = MutableLiveData<List<Song>>()
    val currentPlaylist: LiveData<List<Song>> = _currentPlaylist
    
    private val _currentIndex = MutableLiveData<Int>()
    val currentIndex: LiveData<Int> = _currentIndex
    
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MusicPlayerService.MusicPlayerBinder
            musicService = binder.getService()
            isBound = true
            updatePlaybackState()
        }
        
        override fun onServiceDisconnected(name: ComponentName?) {
            musicService = null
            isBound = false
        }
    }
    
    init {
        bindToService()
        loadMusic()
    }
    
    private fun bindToService() {
        val intent = Intent(getApplication(), MusicPlayerService::class.java)
        getApplication<Application>().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }
    
    private fun loadMusic() {
        viewModelScope.launch {
            try {
                val songList = musicRepository.getAllSongs()
                _songs.value = songList
                
                val albumList = musicRepository.getAllAlbums()
                _albums.value = albumList
                
                val artistList = musicRepository.getAllArtists()
                _artists.value = artistList
            } catch (e: Exception) {
                // Handle error - could emit to an error LiveData
                e.printStackTrace()
            }
        }
    }
    
    fun playSong(song: Song, playlist: List<Song> = emptyList()) {
        _currentSong.value = song
        _currentPlaylist.value = if (playlist.isNotEmpty()) playlist else listOf(song)
        _currentIndex.value = _currentPlaylist.value?.indexOf(song) ?: 0
        
        val uri = musicRepository.getSongUri(song.id)
        musicService?.playMusic(uri)
        updatePlaybackState()
    }
    
    fun playPause() {
        if (musicService?.isPlaying() == true) {
            musicService?.pauseMusic()
        } else {
            musicService?.playMusic()
        }
        updatePlaybackState()
    }
    
    fun nextTrack() {
        val playlist = _currentPlaylist.value ?: return
        val currentIdx = _currentIndex.value ?: return
        
        if (currentIdx < playlist.size - 1) {
            val nextSong = playlist[currentIdx + 1]
            playSong(nextSong, playlist)
        }
    }
    
    fun previousTrack() {
        val playlist = _currentPlaylist.value ?: return
        val currentIdx = _currentIndex.value ?: return
        
        if (currentIdx > 0) {
            val previousSong = playlist[currentIdx - 1]
            playSong(previousSong, playlist)
        }
    }
    
    fun seekTo(position: Long) {
        musicService?.seekTo(position)
        _currentPosition.value = position
    }
    
    fun stopMusic() {
        musicService?.stopMusic()
        _currentSong.value = null
        _isPlaying.value = false
        _currentPosition.value = 0L
        _duration.value = 0L
    }
    
    private fun updatePlaybackState() {
        musicService?.let { service ->
            _isPlaying.value = service.isPlaying()
            _currentPosition.value = service.getCurrentPosition()
            _duration.value = service.getDuration()
        }
    }
    
    fun shufflePlaylist() {
        val playlist = _currentPlaylist.value?.toMutableList() ?: return
        val currentSong = _currentSong.value ?: return
        
        // Remove current song, shuffle the rest, then add current song at the beginning
        playlist.remove(currentSong)
        playlist.shuffle()
        playlist.add(0, currentSong)
        
        _currentPlaylist.value = playlist
        _currentIndex.value = 0
    }
    
    fun loadSongsByAlbum(albumId: Long) {
        viewModelScope.launch {
            try {
                val albumSongs = musicRepository.getSongsByAlbum(albumId)
                _currentPlaylist.value = albumSongs
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    fun loadSongsByArtist(artistId: Long) {
        viewModelScope.launch {
            try {
                val artistSongs = musicRepository.getSongsByArtist(artistId)
                _currentPlaylist.value = artistSongs
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    fun searchSongs(query: String): List<Song> {
        val allSongs = _songs.value ?: return emptyList()
        return allSongs.filter { song ->
            song.title.contains(query, ignoreCase = true) ||
            song.artist.contains(query, ignoreCase = true) ||
            song.album.contains(query, ignoreCase = true)
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        if (isBound) {
            getApplication<Application>().unbindService(serviceConnection)
            isBound = false
        }
    }
}

