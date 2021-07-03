package com.foundvio.service

import com.huawei.agconnect.server.clouddb.request.CloudDBZoneConfig
import com.huawei.agconnect.server.clouddb.service.AGConnectCloudDB
import com.huawei.agconnect.server.clouddb.service.CloudDBZone
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class CloudDBService{

    @Bean
    fun provideAGCCloudDB(): AGConnectCloudDB = AGConnectCloudDB.getInstance()

    @Bean
    fun provideCloudDBZone(agConnectCloudDB: AGConnectCloudDB): CloudDBZone {
        val cloudDBZoneConfig = CloudDBZoneConfig("Foundvio")
        return agConnectCloudDB.openCloudDBZone(cloudDBZoneConfig)
    }

}