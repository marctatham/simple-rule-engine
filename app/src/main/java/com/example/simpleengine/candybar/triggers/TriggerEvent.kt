package com.example.simpleengine.candybar.triggers

sealed class TriggerEvent(val key: String) {

    class AppVisitEvent(val visitCount: Int) : TriggerEvent("app_visit")

    // Example event; "scrolledThroughArticles" will be implemented in future
    class ScrolledThroughArticles(val scrolledThroughArticles: Int) :
        TriggerEvent("article_scroll_through")

    class AppVisitTimeEvent(val durationInMinutes: Int) :
        TriggerEvent("app_visit_time") // might not make

}

sealed class CandyBarRule {
    abstract val key: String
    abstract var conditionsMeet: Boolean
    abstract fun evaluate()

    data class AppVisitRule(
        val condition: Int,
        val value: Int,
        override val key: String = "app_visit",
    ) :
        CandyBarRule() {
        override var conditionsMeet: Boolean = false

        override fun evaluate() {
            conditionsMeet = if (condition > 0) {
                value >= condition
            } else {
                true
            }
        }
    }

    data class AppVisitTimeRule(
        val condition: Int, val value: Int,
        override val key: String = "app_visit_time",
    ) :
        CandyBarRule() {
        override var conditionsMeet: Boolean = false

        override fun evaluate() {
            conditionsMeet = if (condition > 0) {
                value >= condition
            } else {
                true
            }
        }
    }
}