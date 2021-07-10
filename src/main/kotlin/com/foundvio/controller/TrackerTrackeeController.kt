package com.foundvio.controller

import com.foundvio.clouddb.model.TrackerTrackee
import com.foundvio.controller.session.UserSession
import com.foundvio.service.KafkaProducer
import com.foundvio.service.TrackerTrackeeService
import com.foundvio.utils.Logging
import com.foundvio.utils.Response
import com.foundvio.utils.logger
import com.huawei.agconnect.server.auth.exception.AGCAuthException
import com.huawei.agconnect.server.auth.service.AGCAuth
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
class TrackerTrackeeController (
    val userSession: UserSession,
    val trackerTrackeeService: TrackerTrackeeService,
): Logging {

    private val logger = logger()

    @PostMapping("addTrackerTrackee")
    fun addTrackerTrackee(
        @RequestHeader("access-token") accessToken: String,
        @RequestParam trackeeId: Long
    ): Response<Any> {

        return try {
            val trackerTrackee = TrackerTrackee().apply {
                id = UUID.randomUUID().toString()
                //TODO: Change this to Long instead of String
                this.trackeeId = trackeeId
                this.trackerId = userSession.user?.id
            }

            trackerTrackeeService.upsertTrackerTrackee(trackerTrackee)
            Response.Success()
        }
        catch (e: Exception){
            Response.Error("Unable to upsert TrackerTrackee")
        }
    }
}