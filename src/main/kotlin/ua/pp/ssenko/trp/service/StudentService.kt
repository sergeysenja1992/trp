package ua.pp.ssenko.trp.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ua.pp.ssenko.trp.domain.Student
import ua.pp.ssenko.trp.web.StudentDto
import java.time.LocalDate


@Service
@Transactional
class StudentService(
        private val studentRepository: StudentRepository,
        private val groupRepository: GroupRepository
) {

    fun createStudent(studentDto: StudentDto, email: String): Student {
        val group = groupRepository.getGroup(studentDto.groupId)
        group.assertOwner(email)
        return studentRepository.save(Student(name = studentDto.name, group = group))
    }

    fun updateStudent(id: Long, studentDto: StudentDto, email: String): Student {
        val student = studentRepository.getStudent(id)
        student.group.assertOwner(email)
        student.apply {
            name = studentDto.name
            endDate = studentDto.endDate
            startDate = studentDto.startDate
            removed = studentDto.removed
        }
        return studentRepository.save(student)
    }

    fun getStudentsByGroup(groupId: Long, endDate: LocalDate?, email: String): List<Student> {
        val group = groupRepository.getGroup(groupId)
        group.assertOwner(email)
        if (endDate == null) {
            return studentRepository.findStudentsByGroupId(groupId);
        } else {
            return studentRepository.findStudentsByGroupIdAndEndDateBefore(groupId, endDate);
        }
    }

    fun retire(id: Long, email: String) {
        val student = studentRepository.getStudent(id)
        student.group.assertOwner(email)
        studentRepository.save(student.apply {
            removed = true
        })
    }

}