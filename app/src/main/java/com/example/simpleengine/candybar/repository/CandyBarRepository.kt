package com.example.simpleengine.candybar.repository

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface CandyBarRepository {
    val appVisits: Flow<Int>
    val appVisitTime: Flow<Long>

    fun updateAppVisitTime(timestamp: Long): Job
    fun updateAppVisits(visits: Int): Job

    fun clear(): Job
}

class CandyBarRepositoryImpl(
    // private val djDataStoreAsync: DataStoreAsync,
    // private val scope: CoroutineScope
) : CandyBarRepository {

    override val appVisitTime: Flow<Long>
        get() = flowOf(10) // Get value djDataStoreAsync

    override val appVisits: Flow<Int>
        get() = flowOf(10) // Get value djDataStoreAsync

    override fun updateAppVisitTime(timestamp: Long): Job {
        return Job() // Save using djDataStoreAsync
    }

    override fun updateAppVisits(visits: Int): Job {
        return Job() // Save using djDataStoreAsync
    }

    override fun clear(): Job {
        return Job() // Clear using djDataStoreAsync.purge()
    }


}