package com.example.simpleengine.experimentation


sealed class Feature<out T : FeatureConfig>(val flag: String) {
    data object CandyBar : Feature<CandyBarConfig>("candybar")
}


data class CandyBarConfig(
    override val isEnabled: Boolean,
    override val variationKey: String,
    val title: String,
    val description: String,

    // whatever triggers as well
    val triggerAppVisits: Int,
    val triggerAppVisitsTime: Int,

    // rules information
    val coolOffPeriodInDays: Int, // = 3 days

    val popUpDelayInMilliseconds: Long,
) : FeatureConfig
