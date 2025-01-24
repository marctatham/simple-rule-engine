package com.example.simpleengine.candybar.triggers

sealed class TriggerEvent(val key: String) {

    class AppVisitEvent(val visitCount:Int) : TriggerEvent("app_visit")

    // Example event; "scrolledThroughArticles" will be implemented in future
    class ScrolledThroughArticles(val scrolledThroughArticles: Int) : TriggerEvent("article_scroll_through")

    class AppVisitTimeEvent(val durationInMinutes:Int) : TriggerEvent("app_visit_time") // might not make

}
