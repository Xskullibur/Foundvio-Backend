/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2020. All rights reserved.
 * Generated by the CloudDB ObjectType compiler.  DO NOT EDIT!
 */
package com.foundvio.clouddb.model;

import com.huawei.agconnect.server.clouddb.request.CloudDBZoneObject;
import com.huawei.agconnect.server.clouddb.annotations.DefaultValue;
import com.huawei.agconnect.server.clouddb.annotations.NotNull;
import com.huawei.agconnect.server.clouddb.annotations.PrimaryKeys;

/**
 * Definition of ObjectType TrackerTrackee.
 *
 * @since 2021-07-02
 */
@PrimaryKeys({"id"})
public final class TrackerTrackee extends CloudDBZoneObject {
    private String id;

    @NotNull
    @DefaultValue(stringValue = "NA")
    private String trackerId;

    @NotNull
    @DefaultValue(stringValue = "NA")
    private String trackeeId;

    public TrackerTrackee() {
        super(TrackerTrackee.class);
        this.trackerId = "NA";
        this.trackeeId = "NA";

    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setTrackerId(String trackerId) {
        this.trackerId = trackerId;
    }

    public String getTrackerId() {
        return trackerId;
    }

    public void setTrackeeId(String trackeeId) {
        this.trackeeId = trackeeId;
    }

    public String getTrackeeId() {
        return trackeeId;
    }

}
