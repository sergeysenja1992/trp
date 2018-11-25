package ua.pp.ssenko.trp.web

import getDetails
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import ua.pp.ssenko.trp.service.UserService
import java.security.Principal

@RestController
@RequestMapping("/api")
class UserRestController (
        private val userService: UserService
) {

    @RequestMapping(value = ["/user"], method = [RequestMethod.GET])
    @ResponseBody
    fun user(user: Principal): Map<String, Any> {
        return user.getDetails()
    }



}