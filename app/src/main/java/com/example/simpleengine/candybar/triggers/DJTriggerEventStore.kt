package com.example.simpleengine.candybar.triggers

import com.example.simpleengine.experimentation.Feature
import com.example.simpleengine.experimentation.FeatureFlagRepository
import com.example.simpleengine.scope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// singleton implementation that implements each of the interfaces
class DJTriggerEventStore(
    private val featureFlagRepo: FeatureFlagRepository,
) {
    private var currentConfig = featureFlagRepo.getFeatureConfiguration(Feature.CandyBar)

    private val _state: MutableStateFlow<List<CandyBarRule>> = MutableStateFlow(emptyList())
    private val state = _state.asStateFlow()

    fun storeEvent(event: TriggerEvent) {
        scope.launch {
            val rule = createTriggerAndEvaluate(event)
            _state.update { it.filterNot { it::class == rule::class } + rule }
        }
    }

    fun observeEvents(): StateFlow<List<CandyBarRule>> = state

    fun clearEvents() {
        scope.launch { _state.emit(emptyList()) }
    }

    private fun createTriggerAndEvaluate(triggerEvent: TriggerEvent): CandyBarRule {
        val rule = when (triggerEvent) {
            is TriggerEvent.AppVisitEvent -> createAppVisitRule(triggerEvent)
            is TriggerEvent.AppVisitTimeEvent -> createAppVisitTimeRule(triggerEvent)
            else -> throw IllegalArgumentException("Unknown trigger type: ${triggerEvent.key}")
        }

        rule.evaluate()
        return rule
    }

    private fun createAppVisitRule(triggerEvent: TriggerEvent.AppVisitEvent): CandyBarRule.AppVisitRule {
        val condition = currentConfig.triggerAppVisits
        val value = triggerEvent.visitCount

        return CandyBarRule.AppVisitRule(condition, value)
    }

    private fun createAppVisitTimeRule(triggerEvent: TriggerEvent.AppVisitTimeEvent): CandyBarRule.AppVisitTimeRule {
        val condition = currentConfig.triggerAppVisitDurationInMinutes
        val value = triggerEvent.durationInMinutes

        return CandyBarRule.AppVisitTimeRule(condition, value)
    }
}