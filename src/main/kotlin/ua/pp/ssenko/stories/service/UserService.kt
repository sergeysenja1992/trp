package ua.pp.ssenko.stories.service

import getEmail
import log
import lombok.extern.slf4j.Slf4j
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ua.pp.ssenko.stories.domain.Account
import java.security.Principal

@Service
@Transactional
class UserService(
        private val userRepository: UserRepository
) {

    fun createUserIfNotExists(principal: Principal) {
        val user = userRepository.findByEmail(principal.getEmail())
        if (user == null) {
            log.info("Create user with email {}", principal.getEmail());
            userRepository.save(Account(principal.getEmail()))
        }
    }

}

interface UserRepository: CrudRepository<Account, Long> {
    fun findByEmail(email: String): Account?
    fun getByEmail(email: String): Account
}
