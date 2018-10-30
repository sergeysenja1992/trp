import org.slf4j.LoggerFactory
import org.springframework.security.oauth2.provider.OAuth2Authentication
import java.security.Principal

val Any.log
        get() = LoggerFactory.getLogger(this.javaClass)

fun Principal.toOAuth2() = this as OAuth2Authentication

fun Principal.getEmail() : String {
        val details: Map<String, Any> = getDetails()
        return details["email"] as String
}

fun Principal.getDetails() = toOAuth2().userAuthentication.details as Map<String, Any>
