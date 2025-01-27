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
        event: TriggerEvent?, // note: can be nullable if no event has taken place yet!
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
        if (config.triggerAppVisits > 0
            && event is TriggerEvent.AppVisitEvent
            && event.visitCount >= config.triggerAppVisits
        ) {
            return CandyBarDecision(show = true)
        }

        // Rule: if the config contains a triggerAppVisits of greater than 0, it must be evaluated
        if (config.triggerAppVisitDurationInMinutes > 0
            && event is TriggerEvent.AppVisitTimeEvent
            && event.durationInMinutes >= config.triggerAppVisitDurationInMinutes
        ) {
            return CandyBarDecision(show = true)
        }

        return declined
    }
}
