package ua.pp.ssenko.trp.service

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ua.pp.ssenko.trp.domain.*
import ua.pp.ssenko.trp.web.error.NotFoundException
import java.time.DayOfWeek
import java.time.LocalDate

interface KindergartenRepository: JpaRepository<Kindergarten, Long> {
    fun findByOwnerAndRemovedIsFalse(owner: Account): List<Kindergarten>
    fun findByIdIsIn(groups: MutableSet<Long>): List<Kindergarten>
}

fun KindergartenRepository.getKindergarten(id: Long): Kindergarten {
    return findById(id).orElseThrow {
        throw NotFoundException("error.kindergarten.not.found")
    }
}

interface GroupRepository: JpaRepository<Group, Long> {
    fun findByOwnerAndRemovedIsFalse(owner: Account): List<Group>
    fun findByIdIsIn(groups: MutableSet<Long>): List<Group>
}

fun GroupRepository.getGroup(id: Long): Group {
    return findById(id).orElseThrow {
        throw NotFoundException("error.group.not.found")
    }
}

interface StudentRepository: JpaRepository<Student, Long> {
    fun findStudentsByGroupId(groupId: Long): List<Student>
    fun findStudentsByGroupIdAndEndDateBefore(groupId: Long, endDate: LocalDate): List<Student>

    fun findByIdIsIn(student: MutableSet<Long>): List<Student>
}

fun StudentRepository.getStudent(id: Long): Student {
    return findById(id).orElseThrow {
        throw NotFoundException("error.student.not.found")
    }
}

fun LessonRepository.getLesson(id: Long): Lesson {
    return findById(id).orElseThrow {
        throw NotFoundException("error.lesson.not.found")
    }
}

interface LessonRepository: JpaRepository<Lesson, Long> {


    @Query("""
        SELECT lesson FROM Lesson lesson WHERE
        lesson.startDate >= :startDate AND lesson.endDate <= :endDate
        AND lesson.owner = :owner
    """)
    fun findLessonsForUserByRange(@Param("startDate") startDate: LocalDate,
                                  @Param("endDate") endDate: LocalDate,
                                  @Param("owner") owner: Account): List<Lesson>

    @Query("""
        SELECT lesson FROM Lesson lesson WHERE
        lesson.startDate >= :date AND lesson.endDate <= :date
        AND lesson.owner = :owner
        AND
        (
            lesson.periodType = 'DATE'
            OR
            (
                lesson.periodType = 'WEEK'
                AND
                :weekDay member of lesson.weekDays
                AND
                :date not member of lesson.excludeDays
            )
        )
    """)
    fun findLessonsForUserByDate(@Param("date") startDate: LocalDate,
                                 @Param("weekDay") weekDay: DayOfWeek,
                                 @Param("owner") owner: Account): List<Lesson>

}

interface StudentCheckRepository: JpaRepository<StudentCheck, Long> {

}
