package ua.pp.ssenko.trp.domain

import ua.pp.ssenko.trp.web.error.ForbiddenException
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import javax.persistence.*
import javax.persistence.GenerationType.IDENTITY

@Entity
data class Account (
        @Id
        @GeneratedValue(strategy = IDENTITY)
        val id: Long = 0,
        @Column(unique = true)
        var email: String
)

@Entity
data class Kindergarten (
        @Id
        @GeneratedValue(strategy = IDENTITY)
        val id: Long = 0,
        var name: String,
        @ManyToOne
        var owner: Account,
        var removed: Boolean = false
) {
    fun assertOwner(email: String) {
        if (!owner.email.equals(email)) {
            throw ForbiddenException("user.not.owner")
        }
    }
}

@Entity
@Table(name = "lesson_group")
data class Group (
        @Id
        @GeneratedValue(strategy = IDENTITY)
        val id: Long = 0,
        var name: String,
        @ManyToOne
        val kindergarten: Kindergarten,
        @ManyToOne
        var owner: Account,
        var endDate: LocalDate? = null,
        var removed: Boolean = false
) {
    fun assertOwner(email: String) {
        if (!owner.email.equals(email)) {
            throw ForbiddenException("user.not.owner")
        }
    }
}

@Entity
data class Student (
        @Id
        @GeneratedValue(strategy = IDENTITY)
        val id: Long = 0,
        var name: String,
        @ManyToOne
        var group: Group,
        var startDate: LocalDate = LocalDate.now(),
        var endDate: LocalDate? = null,
        var removed: Boolean = false

)


@Entity
data class Lesson (
        @Id
        @GeneratedValue(strategy = IDENTITY)
        val id: Long = 0,
        var name: String?,
        @ManyToOne
        val owner: Account,
        @OneToMany
        val groups: MutableSet<Group> = HashSet(),
        @OneToMany
        val students: MutableSet<Student> = HashSet(),

        var startDate: LocalDate,
        var endDate: LocalDate,
        @Enumerated(EnumType.STRING)
        var periodType: PeriodType,
        @ElementCollection
        var weekDays: MutableSet<DayOfWeek> = HashSet(),
        @ElementCollection
        var excludeDays: MutableSet<LocalDate> = HashSet(),
        var time: LocalTime?,

        var removed: Boolean = false
) {

    fun assertOwner(email: String) {
        if (!owner.email.equals(email)) {
            throw ForbiddenException("user.not.owner")
        }
    }

    val color
        get() = String.format("#%06x", id.hashCode() % 0xffffff)
}

enum class PeriodType {
    DATE, WEEK
}

@Entity
data class StudentCheck (
        @Id
        @GeneratedValue(strategy = IDENTITY)
        val id: Long = 0,
        @ManyToOne
        val student: Student,
        @ManyToOne
        val lesson: Lesson,
        val date: Instant,
        val visited: Boolean
)
