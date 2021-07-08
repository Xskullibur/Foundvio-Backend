package com.foundvio.controller

import com.fasterxml.jackson.annotation.JsonIgnore
import com.foundvio.utils.validation.ValidationField
import com.foundvio.utils.validation.Validators


/**
 * Extra details provided by user
 *
 */
class SetupUserDetails(
    val phone: String,
    val familyName: String,
    val givenName: String,
): Validators() {

    @JsonIgnore
    val phoneValidation = ValidationField<String>().apply {
        rule("Phone cannot be blank") { it.isNotBlank() }
        addValidator(this)
    }
    @JsonIgnore
    val familyNameValidation = ValidationField<String>().apply {
        rule("Family Name cannot be blank") { it.isNotBlank() }
        addValidator(this)
    }
    @JsonIgnore
    val givenNameValidation = ValidationField<String>().apply {
        rule("Given Name cannot be blank") { it.isNotBlank() }
        addValidator(this)
    }

    /**
     * Check if all the fields are valid
     */
    fun validate(): Boolean {
        return phoneValidation.validate(phone) and
                familyNameValidation.validate(familyName) and
                givenNameValidation.validate(givenName)
    }

}