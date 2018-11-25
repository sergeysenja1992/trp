package ua.pp.ssenko.stories.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ua.pp.ssenko.stories.domain.Kindergarten
import ua.pp.ssenko.stories.web.KindergartenDto

@Service
@Transactional
class KindergartenService(
        val userRepository: UserRepository,
        val kindergartenRepository: KindergartenRepository
) {

    fun createKindergarten(kindergarten: KindergartenDto, email: String): Kindergarten {
        val account = userRepository.getByEmail(email)
        return kindergartenRepository.save(Kindergarten(
                name = kindergarten.name,
                owner = account
        ))
    }

    fun updateKindergarten(id: Long, kindergartenDto: KindergartenDto, email: String): Kindergarten {
        val kindergarten = kindergartenRepository.getKindergarten(id)
        kindergarten.assertOwner(email)
        return kindergartenRepository.save(kindergarten.apply {
            name = kindergartenDto.name
        })
    }

    fun getKindergartensByOwner(email: String): List<Kindergarten> {
        val account = userRepository.getByEmail(email)
        return kindergartenRepository.findByOwnerAndRemovedIsFalse(account)
    }

}
