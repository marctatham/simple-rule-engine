package com.example.simpleengine.candybar

import com.example.simpleengine.candybar.triggers.TriggerEvent
import com.example.simpleengine.experimentation.CandyBarConfig

// must be stateless
// why? because we must be able to unit test this heavily
// a lot of thought will go into exactly the scenarios we care about covering

val NO_SHOW_PAGES = listOf("ScreenOne")

class CandyBarRuleEngine {

    fun evaluate(
        config: CandyBarConfig,
        events: Set<TriggerEvent>, // note: can be nullable if no event has taken place yet!
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

        // Rule: if the config contains a triggerAppVisits of greater than 0, it must be evaluated
        val eventVisits = events.find { it is TriggerEvent.AppVisitEvent } as? TriggerEvent.AppVisitEvent
        val triggerVisits = evaluateAppVisitTrigger(config, eventVisits)


        val eventDuration = events.find { it is TriggerEvent.AppVisitTimeEvent } as? TriggerEvent.AppVisitTimeEvent
        val triggerDuration = evaluateDurationTrigger(config, eventDuration)


        val show = triggerVisits && triggerDuration
        return CandyBarDecision(show = show)
    }
}

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
