package ua.pp.ssenko.stories.web

import getEmail
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.*
import ua.pp.ssenko.stories.domain.Student
import ua.pp.ssenko.stories.service.StudentService
import java.security.Principal
import java.time.LocalDate

@RestController
@RequestMapping("/api")
class StudentController(
        private val studentService: StudentService
) {

    @RequestMapping(value = ["/students"], method = [POST])
    @ResponseBody
    fun createStudent(@RequestBody student: StudentDto, user: Principal): Student {
        return studentService.createStudent(student, user.getEmail())
    }

    @RequestMapping(value = ["/students/{id}"], method = [PUT])
    @ResponseBody
    fun updateStudent(@PathVariable("id") id: Long,
                    @RequestBody student: StudentDto,
                    user: Principal): Student {
        return studentService.updateStudent(id, student, user.getEmail())
    }

    @RequestMapping(value = ["/students"], method = [GET])
    @ResponseBody
    fun findAll(user: Principal,
                @RequestParam("groupId") groupId: Long,
                @RequestParam("endDate", required = false) endDate: LocalDate?): List<Student> {
        return studentService.getStudentsByGroup(groupId, endDate, user.getEmail())
    }

    @RequestMapping(value = ["/students/{id}"], method = [RequestMethod.DELETE])
    @ResponseBody
    fun retireGroups(@PathVariable("id") id: Long, user: Principal) {
        studentService.retire(id, user.getEmail())
    }

}

data class StudentDto (
        var name: String,
        var startDate: LocalDate,
        var endDate: LocalDate?,
        var groupId: Long,
        var removed: Boolean = false
)

