package ua.pp.ssenko.stories.web

import getEmail
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.*
import ua.pp.ssenko.stories.domain.Kindergarten
import ua.pp.ssenko.stories.service.KindergartenService
import java.security.Principal

@RestController
@RequestMapping("/api")
class KindergartenController (
        private val kindergartenService: KindergartenService
) {

    @RequestMapping(value = ["/kindergartens"], method = [POST])
    @ResponseBody
    fun createKindergarten(@RequestBody kindergarten: KindergartenDto, user: Principal): Kindergarten {
        return kindergartenService.createKindergarten(kindergarten, user.getEmail())
    }

    @RequestMapping(value = ["/kindergartens/{id}"], method = [PUT])
    @ResponseBody
    fun updateKindergarten(@PathVariable("id") id: Long,
                    @RequestBody kindergarten: KindergartenDto,
                    user: Principal): Kindergarten {
        return kindergartenService.updateKindergarten(id, kindergarten, user.getEmail())
    }

    @RequestMapping(value = ["/kindergartens/{id}"], method = [DELETE])
    @ResponseBody
    fun retireKindergarten(@PathVariable("id") id: Long, user: Principal) {
        kindergartenService.retire(id, user.getEmail())
    }

    @RequestMapping(value = ["/kindergartens"], method = [GET])
    @ResponseBody
    fun findAll(user: Principal): List<Kindergarten> {
        return kindergartenService.getKindergartensByOwner(user.getEmail())
    }

}

data class KindergartenDto (
        var name: String
) {

}
