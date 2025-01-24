package com.example.simpleengine.candybar.modals

class ModalTracker(
    private val store: ModalStateStore
)  {

    fun track(isShown: Boolean) {
        store.trackModalState(isShown)
    }

}