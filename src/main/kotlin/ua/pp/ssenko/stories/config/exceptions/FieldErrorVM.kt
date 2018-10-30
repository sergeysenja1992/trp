package ua.pp.ssenko.stories.config.exceptions

import ua.pp.ssenko.stories.config.exceptionsprivate.ErrorVM
import java.io.Serializable
import java.util.*

/**
 * View Model for transferring error message with a list of field errors.
 */
class FieldErrorVM : ErrorVM {

    private var fieldErrors: MutableList<FieldError> = ArrayList()

    constructor(error: String) : super(error) {}

    constructor(error: String, error_description: String) : super(error, error_description) {}

    fun add(objectName: String, field: String, message: String?) {
        fieldErrors.add(FieldErrorVM.FieldError(objectName, field, message))
    }

    private class FieldError(
            val objectName: String,
            val field: String,
            val message: String?
    ) : Serializable {
        companion object {
            private const val serialVersionUID = 1L
        }
    }
}
