package com.example.simpleengine.candybar

import com.example.simpleengine.experimentation.CandyBarConfig
import com.example.simpleengine.experimentation.Feature
import com.example.simpleengine.experimentation.FeatureFlagRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CandyBarManager(
    private val candyBarRuleEngine: CandyBarRuleEngine,
    private val featureFlagRepo: FeatureFlagRepository,
    private val eventStore: DJTriggerEventStore, // Requirement: requires read + clear permissions

    private val scope: CoroutineScope, // define what thread it runs on, etc.

) {

    private var currentConfig = featureFlagRepo.getFeatureConfiguration(Feature.CandyBar)

    private val _state = MutableStateFlow(CandyBarDecision(show = false))

    // TODO: might not be a simple .asStateFlow, we likely have more complex flow operators
    // that we will need to use as well, things like DEBOUNCE, and DELAY (depending on the candyBar
    // feature configuration)
    val state: Flow<CandyBarDecision> = _state.asStateFlow()
        //.debounce() to ensure we manage things happening that may influence the decision (like a screen changing/modal popping up)?
        .onEach { delay(currentConfig.popUpDelayInMilliseconds) }


    init {

        // observe things that I care about:
        // start:
        // collecting emissions from the ReadableTriggerEventStore
        val eventsFlow: Flow<TriggerEvent> = eventStore.observeEvents()
        val mediaPlaybackFlow: Flow<Boolean> = flow { emit(true) }

        val config: CandyBarConfig = featureFlagRepo.getFeatureConfiguration(Feature.CandyBar)


        // TODO: how do we identify that a modal is currently on-screen (base DJModal implmentation, that would track this information)
        // TODO: audio/media is playing?
        // TODO: Avoid FullScreen, how do we know that something is currently full screen (things like video I believe should already be covered by the audio/media playing point above
        // TODO: Clarify the mechanism by which we identify we are running for a different campaign (aka variant) and must therefore CLEAR/RESET
        // TODO: how do we handle the rules around cool off period
        // TODO: Universal Rules
        //      -

        val combinedFLow = eventsFlow

            .onEach { event ->
                val newConfig = featureFlagRepo.getFeatureConfiguration(Feature.CandyBar)


                if (currentConfig.variationKey != newConfig.variationKey) {
                    // clear the store of any thing that's not relevant
                    // Question: Are there other scenarios in which we must clear?
                    eventStore.clearEvents()
                }

                val decision: CandyBarDecision =
                    candyBarRuleEngine.evaluate(config = newConfig, event = event)

                // when a decision has been taken
                // we need to know that we have shown this to the user
                // we need to track when each day of the cool off period after a user has dismissed it
                _state.emit(decision)

            }.launchIn(scope)


    }

}