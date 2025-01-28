package com.example.simpleengine.candybar

import com.example.simpleengine.candybar.triggers.ResolvedTrigger
import com.example.simpleengine.experimentation.CandyBarConfig

// must be stateless
// why? because we must be able to unit test this heavily
// a lot of thought will go into exactly the scenarios we care about covering

val NO_SHOW_PAGES = listOf("ScreenOne")

class CandyBarRuleEngine {

    fun evaluate(
        config: CandyBarConfig,
        resolvedTriggers: List<ResolvedTrigger>,
        currentScreen: String,
        isMediaPlaying: Boolean,
        isModalVisible: Boolean,
        // TODO: daysSinceLastShown: ? 3 let's come back to this
        // TODO: daysSinceLastVisit: ? 2
    ): CandyBarDecision {

        // let's do the easy part, short-circuit for any deal-breaker scenarios
        if (!config.isEnabled
            || NO_SHOW_PAGES.contains(currentScreen)
            || isMediaPlaying
            || isModalVisible
        ) {
            return CandyBarDecision(show = false)
        }

        // How to check if all conditions are meet with full List?
        val areTriggerConditionsMet = resolvedTriggers.all { it.isConditionMet }
        return CandyBarDecision(show = areTriggerConditionsMet)
    }
}
