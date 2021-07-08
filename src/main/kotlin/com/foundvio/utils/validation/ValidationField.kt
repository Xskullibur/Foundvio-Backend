package com.foundvio.utils.validation

import java.util.function.Consumer


/**
 * Provides validation check on field
 *
 * Example:
 *
 *  Creating a [ValidationField] for validating email
 *  ```
 *  val checker = ValidationField<String>().apply {
 *      //List of predicates or rules for validating the field
 *      rule("Email cannot be blank") { it.isNotBlank() }
 *      rule("Email is invalid") { it.contains("@") }
 *  }
 *  ```
 *
 *  For checking if email is valid:
 *  ```
 *
 *  val email = "address@domain.com"
 *  checker.validate(email) <- returns true
 *
 *  checker.onError { validationMessage ->
 *      println(validationMessage)
 *  }
 *
 *  ```
 *
 */
class ValidationField<T>{

    private var validateCheckers: MutableList<ValidationChecker<T>> = mutableListOf()
    private var validateCheckersMessage: MutableList<String> = mutableListOf()

    private var onError: Consumer<String?>? = null

    /**
     * Check if the given [field] is valid based on the rules or predicates
     * @return if the [field] is valid
     */
    fun validate(field: T): Boolean {
        return validateCheckers.withIndex().any { (index, checker) ->
            val result = checker.validate(field)
            if(!result){ onError?.accept(validateCheckersMessage[index])}
            result
        }.also { valid ->
            if(valid) onError?.accept(null)
        }
    }

    /**
     * Add a new rule or predicate for validating field when calling [validate]
     * @param errorMessage error message if the rule or predicate were to fail
     * @param validationField rule or predicate for validating field
     */
    fun rule(errorMessage: String, validationField: ValidationChecker<T>) {
        validateCheckers += validationField
        validateCheckersMessage += errorMessage
    }

    /**
     * Consume any new error message
     */
    fun onError(onError: Consumer<String?>){
        this.onError = onError
    }


}

fun interface ValidationChecker<T> {
    fun validate(value: T): Boolean
}