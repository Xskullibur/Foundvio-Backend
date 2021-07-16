package com.foundvio.controller

import com.foundvio.clouddb.model.TrackerTrackee
import com.foundvio.clouddb.model.User
import com.foundvio.controller.session.UserSession
import com.foundvio.service.KafkaProducer
import com.foundvio.service.TrackerTrackeeService
import com.foundvio.service.UserService
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
    val userService: UserService
): Logging {

    private val logger = logger()

    @GetMapping("getTrackeesByTrackerId")
    fun getTrackeeByTrackerId(): Response<Any> {
        return try {

            val user = userSession.user
            if (user != null) {
                // Get all Tracker's Trackees
                val trackerTrackees = trackerTrackeeService.queryTrackeesByTrackerId(user.id)
                val trackees = mutableListOf<User>()

                // Get Trackee User model from TrackeeId
                if (trackerTrackees.isNotEmpty()) {
                    for (result in trackerTrackees) {
                        val trackee = userService.queryUserById(result.trackeeId)
                        if (trackee != null) {
                            trackees.add(trackee)
                        }
                    }
                }

                Response.Success(trackees)
            }
            else {
                Response.Error("Failed to get user session")
            }
        }
        catch (e: Exception){
            Response.Error("Unable to query TrackerTrackee")
        }
    }

    @PostMapping("addTrackerTrackee")
    fun addTrackerTrackee(
        @RequestBody trackeeId: Long
    ): Response<Any> {

        return try {
            val trackerTrackee = TrackerTrackee().apply {
                id = UUID.randomUUID().toString()
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

    @PostMapping("removeTrackerTrackee")
    fun removeTrackerTrackee(
        @RequestBody trackeeId: Long
    ): Response<Any> {

        return try {

            // Get TrackerId
            val user = userSession.user
            if (user != null) {
                // Get TrackerTrackee
                val trackerTrackee = trackerTrackeeService.queryTrackerTrackee(user.id, trackeeId)
                val numberOfDeleted = trackerTrackeeService.deleteTrackerTrackee(trackerTrackee)

                if (numberOfDeleted > 0) {
                    Response.Success()
                }
                else {
                    Response.Error("Failed to delete TrackerTrackee")
                }
            }

            Response.Error("Failed to get user session")
        }
        catch (e: Exception) {
            Response.Error("Failed to delete TrackerTrackee")
        }
    }
}