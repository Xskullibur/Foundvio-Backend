package com.foundvio.utils.validation



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
 *  checker.validate(email) <- returns an array of error messages
 *
 *  ```
 *
 */
class ValidationField<T>{

    private var validateCheckers: MutableList<ValidationChecker<T>> = mutableListOf()
    private var validateCheckersMessage: MutableList<String> = mutableListOf()

    /**
     * Check if the given [field] is valid based on the rules or predicates
     * @return an array of error messages if there is any error
     */
    fun validate(field: T): Array<String> {
        return validateCheckers.mapIndexed { index, checker ->
            val result = checker.validate(field)
            if(!result) validateCheckersMessage[index]
            else null
        }.filterNotNull().toTypedArray()
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


}

fun interface ValidationChecker<T> {
    fun validate(value: T): Boolean
}