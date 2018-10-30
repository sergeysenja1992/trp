package ua.pp.ssenko.stories.config

import log
import lombok.extern.slf4j.Slf4j
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.dao.ConcurrencyFailureException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.client.HttpServerErrorException
import ua.pp.ssenko.stories.config.exceptions.*
import ua.pp.ssenko.stories.config.exceptionsprivate.ErrorVM

@Slf4j
@ControllerAdvice
class ExceptionTranslator(
        val messageSource: MessageSource
) {

    @ExceptionHandler(ConcurrencyFailureException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    fun processConcurrencyError(ex: ConcurrencyFailureException): ErrorVM {
        log.debug("Concurrency failure", ex)
        return ErrorVM(ErrorConstants.ERR_CONCURRENCY_FAILURE, translate(ErrorConstants.ERR_CONCURRENCY_FAILURE))
    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun processMissingServletRequestParameterError(ex: MissingServletRequestParameterException): FieldErrorVM {
        val dto = FieldErrorVM(ErrorConstants.ERR_VALIDATION, translate(ErrorConstants.ERR_VALIDATION))
        dto.add(ex.parameterType, ex.parameterName, ex.localizedMessage)
        return dto
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun processValidationError(ex: MethodArgumentNotValidException): ErrorVM {
        val result = ex.bindingResult
        val dto = FieldErrorVM(ErrorConstants.ERR_VALIDATION, translate(ErrorConstants.ERR_VALIDATION))
        for (fieldError in result.fieldErrors) {
            dto.add(fieldError.objectName, fieldError.field, fieldError.code)
        }
        for (globalError in result.globalErrors) {
            dto.add(globalError.objectName, globalError.objectName, globalError.code)
        }
        return dto
    }

    @ExceptionHandler(HttpServerErrorException::class)
    @ResponseBody
    fun processHttpServerError(ex: HttpServerErrorException): ResponseEntity<ErrorVM> {
        val builder: ResponseEntity.BodyBuilder
        val fieldErrorVM: ErrorVM
        val responseStatus: HttpStatus? = ex.statusCode
        if (responseStatus != null) {
            builder = ResponseEntity.status(responseStatus.value())
            fieldErrorVM = ErrorVM(ERROR_PREFIX + responseStatus.value(), translate(ERROR_PREFIX + responseStatus.value()))
        } else {
            builder = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            fieldErrorVM = ErrorVM(ErrorConstants.ERR_INTERNAL_SERVER_ERROR,
                    translate(ErrorConstants.ERR_INTERNAL_SERVER_ERROR))
        }
        return builder.body<ErrorVM>(fieldErrorVM)
    }

    @ExceptionHandler(BusinessException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun processParameterizedValidationError(ex: BusinessException): ParameterizedErrorVM {
        return ParameterizedErrorVM(ex.code, ex.message ?: translate(ex.code), ex.paramMap)
    }

    @ExceptionHandler(EntityNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    fun processNotFoundError(ex: EntityNotFoundException): ErrorVM {
        log.debug("Entity not found", ex)
        return ErrorVM(ErrorConstants.ERR_NOTFOUND, translate(ErrorConstants.ERR_NOTFOUND))
    }

    @ExceptionHandler(AccessDeniedException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    fun processAccessDeniedException(e: AccessDeniedException): ErrorVM {
        log.debug("Access denied", e)
        return ErrorVM(ErrorConstants.ERR_ACCESS_DENIED, translate(ErrorConstants.ERR_ACCESS_DENIED))
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    fun processMethodNotSupportedException(exception: HttpRequestMethodNotSupportedException): ErrorVM {
        log.debug("Method not supported", exception)
        return ErrorVM(ErrorConstants.ERR_METHOD_NOT_SUPPORTED, translate(ErrorConstants.ERR_METHOD_NOT_SUPPORTED))
    }

    @ExceptionHandler(Exception::class)
    fun processException(ex: Exception): ResponseEntity<ErrorVM> {
        log.error("An unexpected error occurred: {}", ex.message, ex)
        val builder: ResponseEntity.BodyBuilder
        val errorVM: ErrorVM
        val responseStatus = AnnotationUtils.findAnnotation(ex.javaClass, ResponseStatus::class.java)
        if (responseStatus != null) {
            builder = ResponseEntity.status(responseStatus.value)
            errorVM = ErrorVM(ERROR_PREFIX + responseStatus.value.value(),
                    translate(ERROR_PREFIX + responseStatus.value.value()))
        } else {
            builder = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            errorVM = ErrorVM(ErrorConstants.ERR_INTERNAL_SERVER_ERROR,
                    translate(ErrorConstants.ERR_INTERNAL_SERVER_ERROR))
        }
        return builder.body<ErrorVM>(errorVM)
    }

    private fun translate(code: String): String {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale())
    }

    companion object {
        private const val ERROR_PREFIX = "error."
    }
}