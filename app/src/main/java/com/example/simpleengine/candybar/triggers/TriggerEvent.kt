package com.example.simpleengine.candybar.triggers

sealed class TriggerEvent {

    data object AppVisitEvent : TriggerEvent()

    class AppVisitDurationEvent(val durationInMinutes: Int) : TriggerEvent()

    // Example event; "scrolledThroughArticles" will be implemented in future
    class ScrolledThroughArticles(val scrolledThroughArticles: Int) : TriggerEvent()

}
