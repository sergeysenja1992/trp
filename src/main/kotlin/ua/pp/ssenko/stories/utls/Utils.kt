import org.slf4j.LoggerFactory
import org.springframework.security.oauth2.provider.OAuth2Authentication
import java.security.Principal
import java.time.LocalDate

val Any.log
        get() = LoggerFactory.getLogger(this.javaClass)

fun Principal.toOAuth2() = this as OAuth2Authentication

fun Principal.getEmail() : String {
        val details: Map<String, Any> = getDetails()
        return details["email"] as String
}

fun Principal.getDetails() = toOAuth2().userAuthentication.details as Map<String, Any>

fun <T> Boolean.thenDo(fetcher: () -> List<T>): List<T> {
        if (this) {
                return fetcher.invoke()
        } else {
                return listOf<T>()
        }
}

fun <T> List<T>.toSet() = HashSet<T>(this)

