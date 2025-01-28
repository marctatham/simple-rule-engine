package com.example.simpleengine.candybar.triggers

import com.example.simpleengine.candybar.CandyBarTriggers


data class ResolvedTrigger(
    val rule: CandyBarTriggers,
    val trigger: TriggerEvent?,
    val isConditionMet: Boolean,
)
