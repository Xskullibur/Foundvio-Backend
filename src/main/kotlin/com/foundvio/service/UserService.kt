package com.foundvio.service

import com.foundvio.clouddb.model.User
import com.huawei.agconnect.server.clouddb.exception.AGConnectCloudDBException
import com.huawei.agconnect.server.clouddb.service.CloudDBZone
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException

@Component
class UserService(
    val mCloudDBZone: CloudDBZone
) {

    fun upsertUser(user: User) {
        try {
            val result: CompletableFuture<Int> = mCloudDBZone.executeUpsert(user)
            println(result.get())
        } catch (e: AGConnectCloudDBException) {
            ConsumerAwareRebalanceListener.LOGGER.warn("upsertBookInfo: " + e.message)
            throw e
        } catch (e: ExecutionException) {
            ConsumerAwareRebalanceListener.LOGGER.warn("upsertBookInfo: " + e.message)
            throw e
        } catch (e: InterruptedException) {
            ConsumerAwareRebalanceListener.LOGGER.warn("upsertBookInfo: " + e.message)
            throw e
        }
    }

}