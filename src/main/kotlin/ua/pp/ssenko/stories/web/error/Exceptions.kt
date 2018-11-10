package ua.pp.ssenko.stories.web.error

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(BAD_REQUEST)
class BusinessException(val errorCode: String): RuntimeException()

@ResponseStatus(NOT_FOUND)
class NotFoundException(val errorCode: String): RuntimeException()

@ResponseStatus(FORBIDDEN)
class ForbiddenException(val errorCode: String): RuntimeException()
