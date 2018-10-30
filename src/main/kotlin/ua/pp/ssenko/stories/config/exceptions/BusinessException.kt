package ua.pp.ssenko.stories.config.exceptions

import lombok.Getter

import java.util.HashMap

/**
 * Custom, parametrized exception, which can be translated on the client side.
 * For example:
 *
 *
 * <pre>
 * throw new BusinessException(&quot;myCustomError&quot;, &quot;hello&quot;, &quot;world&quot;);
</pre> *
 *
 *
 * Can be translated with:
 *
 *
 * <pre>
 * "error.myCustomError" :  "The server says {{param0}} to {{param1}}"
</pre> *
 */
class BusinessException : RuntimeException {

    val code: String
    val paramMap = HashMap<String, String>()

    constructor(message: String) : this(ErrorConstants.ERR_BUSINESS, message) {}

    constructor(code: String, message: String) : super(message) {
        this.code = code
    }

    constructor(code: String, message: String, paramMap: Map<String, String>) : super(message) {
        this.code = code
        this.paramMap.putAll(paramMap)
    }

    constructor(message: String, paramMap: Map<String, String>) : this(message) {
        this.paramMap.putAll(paramMap)
    }

    fun withParams(vararg params: String): BusinessException {
        if (params.size > 0) {
            for (i in params.indices) {
                paramMap[PARAM + i] = params[i]
            }
        }
        return this
    }

    override fun toString(): String {
        return "{code=" + code + ", message=" + message + (if (paramMap.isEmpty()) "" else ", paramMap=$paramMap") + "}"
    }

    companion object {

        private val serialVersionUID = 1L

        private val PARAM = "param"
    }
}
