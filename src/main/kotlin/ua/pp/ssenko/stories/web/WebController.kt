package ua.pp.ssenko.stories.web

import org.springframework.session.Session
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod.GET
import org.springframework.web.bind.annotation.RequestMethod.POST
import javax.servlet.http.HttpSession

@Controller
class WebController {

    @RequestMapping(value = ["/"], method = [GET])
    fun index() = "index.html"

}