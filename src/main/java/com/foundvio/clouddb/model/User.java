/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2020. All rights reserved.
 * Generated by the CloudDB ObjectType compiler.  DO NOT EDIT!
 */
package com.foundvio.clouddb.model;

import com.huawei.agconnect.server.clouddb.request.CloudDBZoneObject;
import com.huawei.agconnect.server.clouddb.annotations.DefaultValue;
import com.huawei.agconnect.server.clouddb.request.Text;
import com.huawei.agconnect.server.clouddb.annotations.NotNull;
import com.huawei.agconnect.server.clouddb.annotations.Indexes;
import com.huawei.agconnect.server.clouddb.annotations.PrimaryKeys;
import com.huawei.agconnect.server.clouddb.annotations.EntireEncrypted;

import java.util.Date;

/**
 * Definition of ObjectType User.
 *
 * @since 2021-07-07
 */
@PrimaryKeys({"id"})
public final class User extends CloudDBZoneObject {
    private Long id;

    private String phone;

    @NotNull
    @DefaultValue(stringValue = "NA")
    private String givenName;

    @NotNull
    @DefaultValue(stringValue = "NA")
    private String familyName;

    @NotNull
    @DefaultValue(booleanValue = false)
    private Boolean isTracker;

    public User() {
        super(User.class);
        this.givenName = "NA";
        this.familyName = "NA";
        this.isTracker = false;

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setIsTracker(Boolean isTracker) {
        this.isTracker = isTracker;
    }

    public Boolean getIsTracker() {
        return isTracker;
    }

}
