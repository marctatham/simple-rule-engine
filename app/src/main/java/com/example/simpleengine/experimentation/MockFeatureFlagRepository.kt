package com.example.simpleengine.experimentation

import com.example.simpleengine.campaignState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


@Suppress("UNCHECKED_CAST")
class MockFeatureFlagRepository : FeatureFlagRepository {

    override fun <T : FeatureConfig> getFeatureConfiguration(feature: Feature<T>): T {
        // just grab the most recent emission from the hacked together campaignstate flow for now
        val campaign = runBlocking { campaignState.first() }

        return campaign as T
    }
}