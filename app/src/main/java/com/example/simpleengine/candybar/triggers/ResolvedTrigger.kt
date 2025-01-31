package com.example.simpleengine.candybar.triggers

import com.example.simpleengine.candybar.CandyBarTriggerConditions


data class ResolvedTrigger(
    val rule: CandyBarTriggerConditions,
    val trigger: TriggerEvent?,
    val isConditionMet: Boolean,
)
