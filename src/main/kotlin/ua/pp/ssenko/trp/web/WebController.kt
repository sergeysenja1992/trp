package ua.pp.ssenko.trp.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod.GET

@Controller
class WebController {

    @RequestMapping(value = ["/"], method = [GET])
    fun index() = "index.html"

}