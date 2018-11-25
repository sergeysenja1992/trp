package ua.pp.ssenko.trp.config.exceptionsprivate

import java.io.Serializable

/**
 * The root view model for all error responses from rest API.
 * Created by medved on 20.06.17.
 */
open class ErrorVM @JvmOverloads constructor(val error: String, val error_description: String? = null) : Serializable {
    companion object {

        private const val serialVersionUID = 1L
    }

}
