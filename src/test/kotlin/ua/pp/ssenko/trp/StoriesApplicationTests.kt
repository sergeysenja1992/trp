package ua.pp.ssenko.trp

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import ua.pp.ssenko.trp.domain.Account
import ua.pp.ssenko.trp.service.LessonService
import ua.pp.ssenko.trp.service.UserRepository
import java.time.LocalDate

@RunWith(SpringRunner::class)
@SpringBootTest
class StoriesApplicationTests {

	@Autowired
	lateinit var lessonService: LessonService

	@Autowired
	lateinit var userRepository: UserRepository

	@Test
	fun contextLoads() {
		userRepository.save(Account(email = "testemail"))
		lessonService.getLessonsByDate(LocalDate.now(), "testemail")
	}

}
