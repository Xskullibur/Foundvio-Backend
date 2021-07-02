package com.foundvio.service

import com.huawei.agconnect.server.clouddb.request.CloudDBZoneConfig
import com.huawei.agconnect.server.clouddb.service.AGConnectCloudDB
import com.huawei.agconnect.server.clouddb.service.CloudDBZone
import com.huawei.agconnect.server.commons.AGCClient
import com.huawei.agconnect.server.commons.AGCParameter
import com.huawei.agconnect.server.commons.credential.CredentialParser
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component

@Component
class CloudDBService(
    resourceLoader: ResourceLoader
) {

    lateinit var mCloudDBZone: CloudDBZone
    lateinit var agConnectCloudDB: AGConnectCloudDB


    init{
        val credentialResource = resourceLoader
            .getResource("classpath:agc-apiclient-662148821073020224-6980207906738382842.json")

        AGCClient.initialize(
            AGCParameter.builder()
                .setCredential(CredentialParser.toCredential(credentialResource.file))
                .build())

        agConnectCloudDB = AGConnectCloudDB.getInstance()

        val cloudDBZoneConfig = CloudDBZoneConfig("Foundvio")
        mCloudDBZone = agConnectCloudDB.openCloudDBZone(cloudDBZoneConfig)
    }

}