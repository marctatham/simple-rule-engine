package com.example.simpleengine.candybar

import com.example.simpleengine.candybar.triggers.CandyBarRule
import com.example.simpleengine.experimentation.CandyBarConfig

// must be stateless
// why? because we must be able to unit test this heavily
// a lot of thought will go into exactly the scenarios we care about covering

val NO_SHOW_PAGES = listOf("ScreenOne")

class CandyBarRuleEngine {

    fun evaluate(
        config: CandyBarConfig,
        rules: List<String>,
        events: List<CandyBarRule>,
        currentScreen: String,
        isMediaPlaying: Boolean,
        isModalVisible: Boolean,
        // TODO: daysSinceLastShown: ? 3 let's come back to this
        // TODO: daysSinceLastVisit: ? 2
    ): CandyBarDecision {

        // let's do the easy part, short-circuit for any deal-breaker scenarios
        val declined = CandyBarDecision(show = false)
        if (!config.isEnabled
            || NO_SHOW_PAGES.contains(currentScreen)
            || isMediaPlaying
            || isModalVisible
        ) {
            return declined
        }

        // How to check if all conditions are meet with full List?
        val show = events.all { it.conditionsMeet } && rules.all { it -> it in events.map { it.key } }

        return CandyBarDecision(show = show)
    }
}

/*
private fun evaluateAppVisitTrigger(
    config: CandyBarConfig,
    event: TriggerEvent.AppVisitEvent?
): Boolean {
    if (config.triggerAppVisits > 0) {
        val visitCount = event?.visitCount ?: 0
        val condition = visitCount >= config.triggerAppVisits
        return condition
    }

    return false
}


private fun evaluateDurationTrigger(
    config: CandyBarConfig,
    event: TriggerEvent.AppVisitTimeEvent?
): Boolean {
    if (config.triggerAppVisits > 0) {
        val duration = event?.durationInMinutes ?: 0
        val condition = duration >= config.triggerAppVisitDurationInMinutes
        return condition
    }

    return false
}
*/
