package com.example.simpleengine.candybar

import com.example.simpleengine.experimentation.CandyBarConfig

class ConfigTriggerEvaluator {

    fun evalRules(config: CandyBarConfig): List<CandyBarTriggers> {
        val triggers = mutableListOf<CandyBarTriggers>()
        if (config.triggerAppVisits > 0) {
            triggers.add(CandyBarTriggers.VisitCount(config.triggerAppVisits))
        }
        if (config.triggerAppVisitDurationInMinutes > 0) {
            triggers.add(CandyBarTriggers.VisitDuration(config.triggerAppVisitDurationInMinutes))
        }

        return triggers
    }

}

sealed class CandyBarTriggers {

    data class VisitCount(val requiredVisitCount: Int) : CandyBarTriggers()

    data class VisitDuration(val requiredVisitDuration: Int) : CandyBarTriggers()

}
