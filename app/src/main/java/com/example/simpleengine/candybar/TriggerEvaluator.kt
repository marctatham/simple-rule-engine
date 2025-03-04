package com.example.simpleengine.candybar

import com.example.simpleengine.candybar.triggers.ResolvedTrigger
import com.example.simpleengine.candybar.triggers.TriggerEvent

class TriggerEvaluator(
    private val appVisitStore: AppVisitsStore // mocked, for illustrative purposes
) {

    fun evaluate(rules: List<CandyBarTriggerConditions>, events: List<TriggerEvent>): List<ResolvedTrigger> {
        val resolvedTriggers = rules.map {
            when (it) {
                is CandyBarTriggerConditions.VisitCount -> evaluateAppVisitTrigger(it, events)
                is CandyBarTriggerConditions.VisitDuration -> evaluateAppDurationTrigger(it, events)
            }
        }

        return resolvedTriggers
    }

    private fun evaluateAppVisitTrigger(
        rule: CandyBarTriggerConditions.VisitCount,
        events: List<TriggerEvent>
    ): ResolvedTrigger {
        val appVisitEvent = events.firstOrNull { it is TriggerEvent.AppVisitEvent } as? TriggerEvent.AppVisitEvent
        return appVisitEvent?.let {
            ResolvedTrigger(
                rule = rule,
                trigger = appVisitEvent,
                isConditionMet = appVisitStore.visitCount >= rule.requiredVisitCount,
            )
        } ?: ResolvedTrigger(
            rule = rule,
            trigger = null,
            isConditionMet = false,
        )
    }

    private fun evaluateAppDurationTrigger(
        rule: CandyBarTriggerConditions.VisitDuration,
        events: List<TriggerEvent>
    ): ResolvedTrigger {
        val event =
            events.firstOrNull { it is TriggerEvent.AppVisitDurationEvent } as? TriggerEvent.AppVisitDurationEvent
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

    fun incrementVisitCount()

    fun clearStore()
}

class MockAppVisitStore : AppVisitsStore {

    private var _visitCount = 0

    override val visitCount: Int
        get() = _visitCount

    override fun incrementVisitCount() {
        _visitCount += 1
    }

    override fun clearStore() {
        _visitCount = 0
    }
}

