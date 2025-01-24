package com.example.simpleengine

import android.provider.MediaStore
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
import com.example.simpleengine.experimentation.MockFeatureFlagRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

// cause i don't have dependency injection 🥲
// this will do for this hack project

val scope = CoroutineScope(Dispatchers.Default)

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
