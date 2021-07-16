package com.foundvio.service

import com.foundvio.clouddb.model.TrackerTrackee
import com.foundvio.clouddb.model.User
import com.huawei.agconnect.server.clouddb.exception.AGConnectCloudDBException
import com.huawei.agconnect.server.clouddb.request.CloudDBZoneQuery
import com.huawei.agconnect.server.clouddb.service.CloudDBZone
import com.sun.demo.jvmti.hprof.Tracker
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException

@Component
class TrackerTrackeeService(
    val mCloudDBZone: CloudDBZone
) {

    fun upsertTrackerTrackee(trackerTrackee: TrackerTrackee) {
        try {
            val result: CompletableFuture<Int> = mCloudDBZone.executeUpsert(trackerTrackee)
            println(result.get())
        }
        catch (e: Exception) {
            when(e){
                is AGConnectCloudDBException, is ExecutionException, is InterruptedException -> {
                    ConsumerAwareRebalanceListener.LOGGER.warn("upsertTrackerTrackee: " + e.message)
                }
            }
            throw e
        }
    }

    fun queryTrackeesByTrackerId(id: Long): List<TrackerTrackee> {
        return try {
            val result = mCloudDBZone.executeQuery(CloudDBZoneQuery.where(TrackerTrackee::class.java)
                    .equalTo("trackerId", id))
            result.get().snapshotObjects
        }
        catch (e: Exception) {
            when(e) {
                is AGConnectCloudDBException, is ExecutionException, is InterruptedException -> {
                    ConsumerAwareRebalanceListener.LOGGER.warn("queryTrackeesByTrackerId: " + e.message)
                }
            }
            throw e
        }
    }

    fun queryTrackerTrackee(trackerId: Long, trackeeId: Long): TrackerTrackee {

        return try {
            val result = mCloudDBZone.executeQuery(CloudDBZoneQuery.where(TrackerTrackee::class.java)
                    .equalTo("trackerId", trackeeId)
                    .equalTo("trackeeId", trackeeId)
            )

            result.get().snapshotObjects[0]
        }
        catch (e: Exception) {
            when(e) {
                is AGConnectCloudDBException, is ExecutionException, is InterruptedException -> {
                    ConsumerAwareRebalanceListener.LOGGER.warn("queryTrackerTrackee: " + e.message)
                }
            }
            throw e
        }
    }

    fun deleteTrackerTrackee(trackerTrackee: TrackerTrackee): Int {

        return try {
            val result = mCloudDBZone.executeDelete(trackerTrackee)
            result.get()
        }
        catch (e: Exception) {
            when(e) {
                is AGConnectCloudDBException, is ExecutionException, is InterruptedException -> {
                    ConsumerAwareRebalanceListener.LOGGER.warn("deleteTrackerTrackee: " + e.message)
                }
            }
            throw e
        }
    }
}