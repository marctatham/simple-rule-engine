package com.example.simpleengine.candybar

import com.example.simpleengine.candybar.triggers.ResolvedTrigger
import com.example.simpleengine.candybar.triggers.TriggerEvent

class TriggerEvaluator(
    private val appVisitStore: AppVisitsStore = MockAppVisitStore() // mocked, for illustrative purposes
) {

    fun evaluate(rules: List<CandyBarTriggers>, events: List<TriggerEvent>): List<ResolvedTrigger> {
        val resolvedTriggers = rules.map {
            when (it) {
                is CandyBarTriggers.VisitCount -> evaluateAppVisitTrigger(it, events)
                is CandyBarTriggers.VisitDuration -> evaluateAppDurationTrigger(it, events)
            }
        }

        return resolvedTriggers
    }

    private fun evaluateAppVisitTrigger(rule: CandyBarTriggers.VisitCount, events: List<TriggerEvent>): ResolvedTrigger {
        val event: TriggerEvent.AppVisitEvent? = events.firstOrNull { it is TriggerEvent.AppVisitEvent } as? TriggerEvent.AppVisitEvent
        // TODO: might be a good idea to showcase behaviour between Trigger & AppVisit Store
        // like how maybe all we get is a triggerEvent.NewVisit with no additional data, and
        // that results in an increment in the store and returning out the current visit count since
        return event?.let {
            ResolvedTrigger(
                rule = rule,
                trigger = event,
                isConditionMet = appVisitStore.visitCount >= rule.requiredVisitCount,
            )
        } ?: ResolvedTrigger(
            rule = rule,
            trigger = null,
            isConditionMet = false,
        )
    }

    private fun evaluateAppDurationTrigger(rule: CandyBarTriggers.VisitDuration, events: List<TriggerEvent>): ResolvedTrigger {
        val event = events.firstOrNull { it is TriggerEvent.AppVisitDurationEvent } as? TriggerEvent.AppVisitDurationEvent
        return event?.let {
            ResolvedTrigger(
                rule = rule,
                trigger = event,
                isConditionMet = event.durationInMinutes >= rule.requiredVisitDuration,
            )
        } ?: ResolvedTrigger(
            rule = rule,
            trigger = null,
            isConditionMet = false,
        )
    }
}


interface AppVisitsStore {
    val visitCount: Int
}

class MockAppVisitStore: AppVisitsStore {
    override val visitCount: Int = 3
}

