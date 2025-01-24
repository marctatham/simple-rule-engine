package com.example.simpleengine.candybar

import android.util.Log
import com.example.simpleengine.candybar.media.MediaStateStore
import com.example.simpleengine.candybar.modals.ModalStateStore
import com.example.simpleengine.candybar.screen.ScreenStateStore
import com.example.simpleengine.candybar.triggers.DJTriggerEventStore
import com.example.simpleengine.candybar.triggers.TriggerEvent
import com.example.simpleengine.experimentation.Feature
import com.example.simpleengine.experimentation.FeatureFlagRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalCoroutinesApi::class)
class CandyBarManager(
    private val candyBarRuleEngine: CandyBarRuleEngine,
    private val featureFlagRepo: FeatureFlagRepository,
    private val screenStore: ScreenStateStore,
    private val mediaStore: MediaStateStore,
    private val modalStore: ModalStateStore,
    private val eventStore: DJTriggerEventStore,
    private val scope: CoroutineScope, // define what thread it runs on, etc.
) {

    private var currentConfig = featureFlagRepo.getFeatureConfiguration(Feature.CandyBar)

    private val _state = MutableStateFlow(CandyBarDecision(show = false, config = null))
    val state: Flow<CandyBarDecision> = _state.asStateFlow().onEach {
        Log.i("CandyBarManager", "CandyBarDecision: ${it.show}")
    }

    init {
        // observe things that I care about:
        // start:
        // collecting emissions from the ReadableTriggerEventStore
        val screenFlow: Flow<String> = screenStore.observeEvents()
        val mediaFlow: Flow<Boolean> = mediaStore.observeEvents()
        val modalFlow: Flow<Boolean> = modalStore.observeEvents()
        val eventsFlow: Flow<TriggerEvent?> = eventStore.observeEvents()

        combine(
            screenFlow,
            mediaFlow,
            modalFlow,
            eventsFlow
        ) { screen, media, modal, event ->
            candyBarRuleEngine.evaluate(
                config = currentConfig,
                event = event,
                currentScreen = screen,
                isMediaPlaying = media,
                isModalVisible = modal
            )
        }.flatMapLatest { decision -> // flatMapLatest: Cancel the previous flow and start a new one
            if (decision.show) {
                flow {
                    Log.w(
                        "CandyBarManager",
                        "Delaying for ${currentConfig.popUpDelayInMilliseconds} ms"
                    )
                    delay(currentConfig.popUpDelayInMilliseconds)
                    emit(decision)
                }
            } else {
                flowOf(decision)
            }
        }.onEach { decision ->
            if (decision.show) {
                // Save state for Cool Off period or after dismiss the CandyBar¿?
            }
            Log.i("CandyBarManager", "Decision: $decision")
            _state.emit(decision)
        }.launchIn(scope)
    }
}