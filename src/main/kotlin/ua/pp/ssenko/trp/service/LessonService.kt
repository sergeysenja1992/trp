package ua.pp.ssenko.trp.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import thenDo
import toSet
import ua.pp.ssenko.trp.domain.Lesson
import ua.pp.ssenko.trp.domain.PeriodType
import ua.pp.ssenko.trp.domain.PeriodType.WEEK
import ua.pp.ssenko.trp.utls.RangeByDate
import ua.pp.ssenko.trp.web.LessonDto
import java.time.LocalDate

@Service
@Transactional
class LessonService (
        val userRepository: UserRepository,
        val lessonRepository: LessonRepository,
        val studentRepository: StudentRepository,
        val groupRepository: GroupRepository
) {
    fun createLesson(lesson: LessonDto, email: String): Lesson {
        val owner = userRepository.getByEmail(email)

        val groups = lesson.groups.isNotEmpty().thenDo {
            groupRepository.findByIdIsIn(lesson.groups)
        }

        val students = lesson.students.isNotEmpty().thenDo {
            studentRepository.findByIdIsIn(lesson.students)
        }

        return lessonRepository.save(Lesson(
                name = lesson.name,
                owner = owner,
                groups = groups.toSet(),
                students = students.toSet(),
                startDate = lesson.startDate,
                endDate = lesson.endDate,
                periodType = lesson.periodType,
                weekDays = lesson.weekDays,
                time = lesson.time
        ))
    }

    fun updateLesson(id: Long, lessonDto: LessonDto, email: String): Lesson {
        val lesson = lessonRepository.getLesson(id)

        val groups = lesson.groups.isNotEmpty().thenDo {
            groupRepository.findByIdIsIn(lessonDto.groups)
        }

        val students = lesson.students.isNotEmpty().thenDo {
            studentRepository.findByIdIsIn(lessonDto.students)
        }

        lesson.apply {
            name = lesson.name
            this.groups.clear()
            this.groups.addAll(groups)
            this.students.clear()
            this.students.addAll(students)
            startDate = lesson.startDate
            endDate = lesson.endDate
            periodType = lesson.periodType
            weekDays = lesson.weekDays
            time = lesson.time
        }

        return lessonRepository.save(lesson)
    }

    fun updateOneLesson(id: Long, lessonDto: LessonDto, email: String) {
        val lesson = lessonRepository.getLesson(id)
        lesson.assertOwner(email)

        for (date in RangeByDate(lesson.startDate, lesson.endDate)) {
            lesson.excludeDays.add(date)
        }
        createLesson(lessonDto, email)
    }

    fun getLessonsByDates(startDate: LocalDate, endDate: LocalDate, email: String): List<Lesson> {
        val owner = userRepository.getByEmail(email)
        var lessons = lessonRepository.findLessonsForUserByRange(startDate, endDate, owner)

        return RangeByDate(startDate, endDate).flatMap {
            lessonsByDate(lessons, it)
        }
    }

    fun lessonsByDate(lessons: List<Lesson>, date: LocalDate): List<Lesson> {
        return lessons.filter { !it.excludeDays.contains(date) }
                .filter {
                    (it.weekDays.contains(date.dayOfWeek) && it.periodType == WEEK)
                            ||
                            it.periodType == PeriodType.DATE
                }.map {
                    val lesson = it
                    lesson.startDate = date
                    lesson.endDate = date
                    lesson
                }
    }

    fun getLessonsByDate(date: LocalDate, email: String): List<Lesson> {
        val owner = userRepository.getByEmail(email)
        return lessonRepository.findLessonsForUserByDate(date, LocalDate.now().dayOfWeek, owner)
    }

    fun retire(id: Long, email: String) {
        val lesson = lessonRepository.getLesson(id)
        lesson.assertOwner(email)
        lessonRepository.save(lesson.apply {
            removed = true
        })
    }

}
