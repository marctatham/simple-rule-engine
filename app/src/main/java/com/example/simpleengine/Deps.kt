package com.example.simpleengine

import com.example.simpleengine.candybar.CandyBarManager
import com.example.simpleengine.candybar.CandyBarRuleEngine
import com.example.simpleengine.candybar.media.MediaStateStore
import com.example.simpleengine.candybar.media.MediaTracker
import com.example.simpleengine.candybar.modals.ModalStateStore
import com.example.simpleengine.candybar.modals.ModalTracker
import com.example.simpleengine.candybar.screen.ScreenStateStore
import com.example.simpleengine.candybar.screen.ScreenTracker
import com.example.simpleengine.candybar.triggers.DJTriggerEventStore
import com.example.simpleengine.candybar.triggers.DJTriggerEventTracker
import com.example.simpleengine.experimentation.CandyBarConfig
import com.example.simpleengine.experimentation.MockFeatureFlagRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// cause i haven't bothered to setup dependency injection 🥲
// This project exists purely for illustrative purposes

val scope = CoroutineScope(Dispatchers.Default)

// region Campaign Management

val campaignOne = CandyBarConfig(
    isEnabled = true,
    variationKey = "marketing1",
    title = "campaign1",
    description = "leDescription1",
    triggerAppVisits = 1,
    triggerAppVisitDurationInMinutes = 0,
    coolOffPeriodInDays = 3,
    popUpDelayInMilliseconds = 3000
)

val campaignTwo = CandyBarConfig(
    isEnabled = true,
    variationKey = "marketing2",
    title = "campaign2",
    description = "leDescription2",
    triggerAppVisits = 0,
    triggerAppVisitDurationInMinutes = 2,
    coolOffPeriodInDays = 3,
    popUpDelayInMilliseconds = 3000
)

// endregion Campaign Management

private val _campaignState: MutableStateFlow<CandyBarConfig> = MutableStateFlow(campaignOne)
fun changeCampaign(config:CandyBarConfig):Unit  { scope.launch { _campaignState.emit(config) } }
val campaignState: StateFlow<CandyBarConfig> = _campaignState.asStateFlow()

// region CandyBar Dependencies

val mediaStore: MediaStateStore = MediaStateStore()
val modalStore = ModalStateStore()
val eventStore = DJTriggerEventStore()
val screenStore = ScreenStateStore()

val mediaTracker = MediaTracker(mediaStore)
val modalTracker = ModalTracker(modalStore)
val eventTracker = DJTriggerEventTracker(eventStore)
val screenTracker = ScreenTracker(screenStore)

val ruleEngine = CandyBarRuleEngine()

val featureFlagRepo = MockFeatureFlagRepository()

val candyBarManager = CandyBarManager(
    mediaStore = mediaStore,
    modalStore = modalStore,
    eventStore = eventStore,
    screenStore = screenStore,
    candyBarRuleEngine = ruleEngine,
    featureFlagRepo = featureFlagRepo,
    scope = scope
)

// endregion CandyBar Dependencies