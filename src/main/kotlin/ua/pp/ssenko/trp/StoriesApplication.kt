package ua.pp.ssenko.stories

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso
import org.springframework.boot.runApplication

@SpringBootApplication
class StoriesApplication

fun main(args: Array<String>) {
    runApplication<StoriesApplication>(*args)
}
