package com.example.simpleengine.experimentation

import com.example.simpleengine.campaign

@Suppress("UNCHECKED_CAST")
class MockFeatureFlagRepository : FeatureFlagRepository {

    override fun <T : FeatureConfig> getFeatureConfiguration(feature: Feature<T>): T {
        return campaign as T
    }
}