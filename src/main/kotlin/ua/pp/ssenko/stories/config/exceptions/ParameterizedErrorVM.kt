package ua.pp.ssenko.stories.config.exceptions

import ua.pp.ssenko.stories.config.exceptionsprivate.ErrorVM
import java.io.Serializable

/**
 * View Model for sending a parametrized error message.
 */
class ParameterizedErrorVM(error: String, error_description: String, val params: Map<String, String>) : ErrorVM(error, error_description), Serializable {
    companion object {
        private const val serialVersionUID = 1L
    }

}
