package ua.pp.ssenko.trp.web

import getEmail
import org.springframework.web.bind.annotation.*
import ua.pp.ssenko.trp.domain.Account
import ua.pp.ssenko.trp.domain.Lesson
import ua.pp.ssenko.trp.domain.PeriodType
import ua.pp.ssenko.trp.service.LessonService
import java.security.Principal
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

@RestController
@RequestMapping("/api")
class LessonController(
        val lessonService: LessonService
) {

    @RequestMapping(value = ["/lessons"], method = [RequestMethod.POST])
    @ResponseBody
    fun createLessons(@RequestBody lesson: LessonDto, user: Principal): Lesson {
        return lessonService.createLesson(lesson, user.getEmail())
    }

    @RequestMapping(value = ["/lessons/{id}"], method = [RequestMethod.PUT])
    @ResponseBody
    fun updateStudent(@PathVariable("id") id: Long,
                      @RequestBody lessonDto: LessonDto,
                      user: Principal): Lesson {
        return lessonService.updateLesson(id, lessonDto, user.getEmail())
    }

    @RequestMapping(value = ["/lessons"], method = [RequestMethod.GET])
    @ResponseBody
    fun findAll(user: Principal,
                @RequestParam("startDate", required = false) startDate: LocalDate,
                @RequestParam("endDate", required = false) endDate: LocalDate): List<Lesson> {
        return lessonService.getLessonsByDates(startDate, endDate, user.getEmail())
    }

    @RequestMapping(value = ["/lessons/{date}"], method = [RequestMethod.GET])
    @ResponseBody
    fun findByDate(user: Principal,
                @PathVariable("date", required = false) date: LocalDate): List<Lesson> {
        return lessonService.getLessonsByDate(date, user.getEmail())
    }

    @RequestMapping(value = ["/lessons/{id}"], method = [RequestMethod.DELETE])
    @ResponseBody
    fun retireGroups(@PathVariable("id") id: Long, user: Principal) {
        lessonService.retire(id, user.getEmail())
    }

}

data class LessonDto (
        var name: String?,
        var owner: Account,
        var groups: MutableSet<Long> = HashSet(),
        val students: MutableSet<Long> = HashSet(),
        var startDate: LocalDate,
        var endDate: LocalDate,
        var periodType: PeriodType,
        var weekDays: MutableSet<DayOfWeek> = HashSet(),
        var time: LocalTime?,
        var removed: Boolean?
)
