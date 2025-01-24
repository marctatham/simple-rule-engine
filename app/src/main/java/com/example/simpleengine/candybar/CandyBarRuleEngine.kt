package com.example.simpleengine.candybar

import com.example.simpleengine.experimentation.CandyBarConfig

// must be stateless
// why? because we must be able to unit test this heavily
// a lot of thought will go into exactly the scenarios we care about covering
class CandyBarRuleEngine(
    // no dependencies
) {

//featureConfig: FeatureConfig


    fun evaluate(
        config: CandyBarConfig,
        event: TriggerEvent,
        // TODO: currentScreen: Route?,
        // TODO: daysSinceLastShown: ? 3
        // TODO: daysSinceLastVisit: ? 2
        // TODO: isMediaPlaying?
        // TODO: isModalVisible?
    ): CandyBarDecision {

        // let's do the easy part, short-circuit
        if (!config.isEnabled) {
            return CandyBarDecision(show = false)
        }

        // Prevents showing on specific "no-show" screens
        /*if (currentRoute includes (listOfNoShowScreens)) {
            return CandyBarDecision(show = false)
        }*/

        // now we do the heavy logic-crunching
        // here's where the difficult questions and rules start coming into play

        // for each trigger, evaluate whatever needs evaluating, here's a real world example
        if (config.triggerAppVisits > 0) {
            val userAppVisits = 3 // TODO: Replace with logic to retrieve the actual number of app visits for the user
            if (event is TriggerEvent.AppVisitEvent && userAppVisits == config.triggerAppVisits) {
                // this trigger has been configured
                // the trigger condition has been met and we MUST show the candy bar
                return CandyBarDecision(show = true)
            }
        }

        if (config.triggerAppVisits > 0) {
            val userAppVisits = 3 // TODO: Replace with logic to retrieve the actual number of app visits for the user
            if (event is TriggerEvent.AppVisitEvent && userAppVisits == config.triggerAppVisits) {
                // this trigger has been configured
                // the trigger condition has been met and we MUST show the candy bar
                return CandyBarDecision(show = true)
            }
        }

        return CandyBarDecision(show = false)
    }
}


// TODO: find a better name for this
// define what this looks like
data class CandyBarDecision(val show: Boolean)