package com.foundvio.service

import com.foundvio.clouddb.model.TrackerTrackee
import com.foundvio.clouddb.model.User
import com.huawei.agconnect.server.clouddb.exception.AGConnectCloudDBException
import com.huawei.agconnect.server.clouddb.service.CloudDBZone
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
}