package ua.pp.ssenko.trp.domain

import ua.pp.ssenko.trp.web.error.ForbiddenException
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import javax.persistence.*
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.InheritanceType.SINGLE_TABLE

@MappedSuperclass
open class ObjectWithOwner (
    @ManyToOne
    var owner: Account,
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long = 0
) {
    fun assertOwner(email: String) {
        if (!owner.email.equals(email)) {
            throw ForbiddenException("user.not.owner")
        }
    }
}

@Entity
@Inheritance
class Account (
        @Column(unique = true)
        var email: String,
        owner: Account
) : ObjectWithOwner(owner)

@Entity
@Inheritance
class Kindergarten (
        var name: String,
        var removed: Boolean = false,
        owner: Account
) : ObjectWithOwner(owner)

@Entity
@Inheritance
@Table(name = "lesson_group")
class Group (
        var name: String,
        @ManyToOne
        val kindergarten: Kindergarten,
        var endDate: LocalDate? = null,
        var removed: Boolean = false,
        owner: Account
) : ObjectWithOwner(owner)

@Entity
@Inheritance
class Student (
        var name: String,
        var startDate: LocalDate = LocalDate.now(),
        var endDate: LocalDate? = null,
        var removed: Boolean = false,
        @ManyToOne
        var group: Group?,
        owner: Account
) : ObjectWithOwner(owner)


@Entity
@Inheritance
class Lesson (
        var name: String?,
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
        var removed: Boolean = false,
        owner: Account
) : ObjectWithOwner(owner) {

    val color
        get() = String.format("#%06x", id.hashCode() % 0xffffff)
}

enum class PeriodType {
    DATE, WEEK
}

@Entity
@Inheritance
class StudentCheck (
        @ManyToOne
        val student: Student,
        @ManyToOne
        val lesson: Lesson,
        val date: Instant,
        val visited: Boolean,
        owner: Account
) : ObjectWithOwner(owner)
