package com.foundvio.controller

import com.foundvio.clouddb.model.TrackerTrackee
import com.foundvio.service.KafkaProducer
import com.foundvio.service.TrackerTrackeeService
import com.foundvio.utils.Logging
import com.foundvio.utils.Response
import com.foundvio.utils.logger
import com.huawei.agconnect.server.auth.exception.AGCAuthException
import com.huawei.agconnect.server.auth.service.AGCAuth
import org.springframework.web.bind.annotation.*


@RestController
class TrackerTrackeeController (
    val trackerTrackeeService: TrackerTrackeeService,
): Logging {

    private val logger = logger()

    @PostMapping("addTrackerTrackee")
    fun addTrackerTrackee(
        @RequestHeader("access-token") accessToken: String,
        @RequestParam trackee: String
    ): Response<Any> {

        return try {
            val agcAuth = AGCAuth.getInstance()
            val agcAccessToken = agcAuth.verifyAccessToken(accessToken, true)

            val trackerTrackee = TrackerTrackee().apply {
                trackerId = agcAccessToken.phone
                trackerId = accessToken
                trackeeId = trackee
            }

            trackerTrackeeService.upsertTrackerTrackee(trackerTrackee)
            Response.Success()
        }
        catch (e: Exception){
            Response.Error("Unable to upsert TrackerTrackee")
        }
    }
}