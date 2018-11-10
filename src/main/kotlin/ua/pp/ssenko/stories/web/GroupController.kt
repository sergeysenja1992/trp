package ua.pp.ssenko.stories.web

import getEmail
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.*
import ua.pp.ssenko.stories.domain.Group
import ua.pp.ssenko.stories.service.GroupService
import java.security.Principal
import java.time.Instant
import java.time.LocalDate

@RestController
@RequestMapping("/api")
class GroupController (
        private val groupService: GroupService
) {

    @RequestMapping(value = ["/groups"], method = [POST])
    @ResponseBody
    fun createGroup(@RequestBody group: GroupDto, user: Principal): Group {
        return groupService.createGroup(group, user.getEmail())
    }

    @RequestMapping(value = ["/groups/{id}"], method = [PUT])
    @ResponseBody
    fun updateGroup(@PathVariable("id") id: Long,
                    @RequestBody group: GroupDto,
                    user: Principal): Group {
        return groupService.updateGroup(id, group, user.getEmail())
    }

    @RequestMapping(value = ["/groups"], method = [GET])
    @ResponseBody
    fun findAll(user: Principal): List<Group> {
        return groupService.getGroupsByOwner(user.getEmail())
    }

}

data class GroupDto (
        var name: String,
        var endDate: LocalDate?,
        var removed: Boolean = false
)
