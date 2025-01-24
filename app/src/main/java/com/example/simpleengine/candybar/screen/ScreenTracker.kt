package com.example.simpleengine.candybar.screen

class ScreenTracker(
    private val store: ScreenStateStore
)  {

    fun track(screen: String) {
        store.trackModalState(screen)
    }

}