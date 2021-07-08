package com.foundvio.utils.validation

import java.util.function.Consumer

abstract class Validators {

    private val validators = mutableSetOf<ValidationField<*>>()

    fun<T> addValidator(validator: ValidationField<T>){
        validators += validator
    }

    fun onError(onError: Consumer<String?>){
        validators.onEach { it.onError(onError) }
    }


}