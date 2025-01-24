package com.example.simpleengine.experimentation

/**
 * Describes whether or not the feature is enabled
 */
interface FeatureConfig {
    val isEnabled: Boolean
    val variationKey: String
}
