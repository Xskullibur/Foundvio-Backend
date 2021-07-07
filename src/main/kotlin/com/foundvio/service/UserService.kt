package com.foundvio.service

import com.foundvio.clouddb.model.User
import com.huawei.agconnect.server.clouddb.exception.AGConnectCloudDBException
import com.huawei.agconnect.server.clouddb.request.CloudDBZoneQuery
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
        } catch (e: Exception) {
            when(e){
                is AGConnectCloudDBException, is ExecutionException, is InterruptedException -> {
                    ConsumerAwareRebalanceListener.LOGGER.warn("upsertBookInfo: " + e.message)
                }
            }
            throw e
        }
    }

    fun queryUserById(id: Long): User? {
        return try{
            val result = mCloudDBZone.executeQuery(CloudDBZoneQuery.where(User::class.java)
                .equalTo("id", id))
            result.get().snapshotObjects.firstOrNull()
        } catch (e: Exception) {
            when(e){
                is AGConnectCloudDBException, is ExecutionException, is InterruptedException -> {
                    ConsumerAwareRebalanceListener.LOGGER.warn("upsertBookInfo: " + e.message)
                }
            }
            throw e
        }
    }


}