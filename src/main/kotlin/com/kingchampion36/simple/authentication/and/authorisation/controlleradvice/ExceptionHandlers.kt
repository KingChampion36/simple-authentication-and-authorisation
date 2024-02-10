package com.kingchampion36.simple.authentication.and.authorisation.controlleradvice

import com.kingchampion36.simple.authentication.and.authorisation.exceptions.ResourceConflictException
import com.kingchampion36.simple.authentication.and.authorisation.exceptions.ResourceNotFoundException
import com.kingchampion36.simple.authentication.and.authorisation.response.GenericErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandlers {
  @ExceptionHandler(ResourceNotFoundException::class)
  fun resourceNotFoundException(ex: ResourceNotFoundException) = ResponseEntity
    .status(HttpStatus.NOT_FOUND)
    .body(GenericErrorResponse(ex.localizedMessage))

  @ExceptionHandler(ResourceConflictException::class)
  fun resourceConflictException(ex: ResourceConflictException) = ResponseEntity
    .status(HttpStatus.CONFLICT)
    .body(GenericErrorResponse(ex.localizedMessage))
}
