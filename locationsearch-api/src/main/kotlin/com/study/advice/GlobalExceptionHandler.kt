package com.study.advice

import com.wantedlab.exception.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.reactive.function.client.WebClientResponseException
import org.springframework.web.server.MissingRequestValueException

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(MissingRequestValueException::class)
    fun handleMissingRequestValueException(ex: MissingRequestValueException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(code = "BAD_REQUEST", message = "Invalid request body")
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(WebClientResponseException::class)
    fun handleWebClientResponseException(ex: WebClientResponseException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(code = "INTERNAL_SERVER_ERROR", message = "Internal api call error")
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

}


