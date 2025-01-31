package com.example.simpleengine.candybar

import com.example.simpleengine.experimentation.CandyBarConfig

class CandyBarTriggerConditionEvaluator {

    /**
     * evaluate the TriggerConditions that cause the CandyBar to be displayed.
     */
    fun evaluateTriggerConditions(config: CandyBarConfig): List<CandyBarTriggerConditions> {
        val triggers = mutableListOf<CandyBarTriggerConditions>()
        if (config.triggerAppVisits > 0) {
            triggers.add(CandyBarTriggerConditions.VisitCount(config.triggerAppVisits))
        }
        if (config.triggerAppVisitDurationInMinutes > 0) {
            triggers.add(CandyBarTriggerConditions.VisitDuration(config.triggerAppVisitDurationInMinutes))
        }

        return triggers
    }

}

/**
 * Describes the trigger conditions available.
  */

sealed class CandyBarTriggerConditions {

    data class VisitCount(val requiredVisitCount: Int) : CandyBarTriggerConditions()

    data class VisitDuration(val requiredVisitDuration: Int) : CandyBarTriggerConditions()

}
