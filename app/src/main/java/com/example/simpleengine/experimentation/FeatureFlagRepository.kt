package com.example.simpleengine.experimentation

interface FeatureFlagRepository {
    /**
     * Check configuration for a given feature. The response object describes:
     *    - whether the feature is enabled or not
     *    - any feature-specific configuration
     */
    fun <T : FeatureConfig> getFeatureConfiguration(feature: Feature<T>): T
}
