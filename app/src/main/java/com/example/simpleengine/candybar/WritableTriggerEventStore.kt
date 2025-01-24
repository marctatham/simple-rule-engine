package com.dowjones.candybar

import com.example.simpleengine.candybar.TriggerEvent


interface WritableTriggerEventStore {

    fun storeEvent(triggerEvent: TriggerEvent)

}


