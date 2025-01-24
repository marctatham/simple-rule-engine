package com.example.simpleengine.candybar

import com.example.simpleengine.experimentation.CandyBarConfig

data class CandyBarDecision(
    val show: Boolean,
    val config: CandyBarConfig?
)