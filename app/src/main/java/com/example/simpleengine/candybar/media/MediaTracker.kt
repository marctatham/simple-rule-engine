package com.example.simpleengine.candybar.media

class MediaTracker(
    private val store: MediaStateStore
)  {

    fun track(isShown: Boolean) {
        store.trackMediaState(isShown)
    }

}