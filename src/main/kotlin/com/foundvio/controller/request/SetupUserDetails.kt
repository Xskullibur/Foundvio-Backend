package com.foundvio.controller.request

import com.fasterxml.jackson.annotation.JsonIgnore
import com.foundvio.utils.validation.ValidationField


/**
 * Extra details provided by user
 *
 */
class SetupUserDetails(
    val phone: String,
    val familyName: String,
    val givenName: String,
    val isTracker: Boolean
) {

    @JsonIgnore
    val phoneValidation = ValidationField<String>().apply {
        rule("Phone cannot be blank") { it.isNotBlank() }
    }
    @JsonIgnore
    val familyNameValidation = ValidationField<String>().apply {
        rule("Family Name cannot be blank") { it.isNotBlank() }
    }
    @JsonIgnore
    val givenNameValidation = ValidationField<String>().apply {
        rule("Given Name cannot be blank") { it.isNotBlank() }
    }

    /**
     * Check if all the fields are valid
     */
    fun validate(): Array<String> {
        return phoneValidation.validate(phone) +
            familyNameValidation.validate(familyName) +
            givenNameValidation.validate(givenName)
    }

}