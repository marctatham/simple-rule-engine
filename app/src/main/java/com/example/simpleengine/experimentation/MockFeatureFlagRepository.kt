package com.example.simpleengine.experimentation

@Suppress("UNCHECKED_CAST")
class MockFeatureFlagRepository : FeatureFlagRepository {

    override fun <T : FeatureConfig> getFeatureConfiguration(feature: Feature<T>): T {
        // This is a mock implementation, return any value we want
        return CandyBarConfig(
            isEnabled = true,
            variationKey = "variationKey",
            title = "title",
            description = "description",
            triggerAppVisits = 2,
            triggerAppVisitDurationInMinutes = 3,
            coolOffPeriodInDays = 1,
            popUpDelayInMilliseconds = 3
        ) as T
    }
}