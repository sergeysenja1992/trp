package ua.pp.ssenko.trp.service

import log
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ua.pp.ssenko.trp.domain.Account

@Service
@Transactional
class UserService(
        private val userRepository: UserRepository
) {

    fun createUserIfNotExists(email: String) {
        val user = userRepository.findByEmail(email)
        if (user == null) {
            log.info("Create user with email {}", email);
            userRepository.save(Account(email = email))
        }
    }

}

interface UserRepository: CrudRepository<Account, Long> {
    fun findByEmail(email: String): Account?
    fun getByEmail(email: String): Account
}
