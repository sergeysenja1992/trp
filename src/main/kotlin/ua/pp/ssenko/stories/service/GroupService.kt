package ua.pp.ssenko.stories.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ua.pp.ssenko.stories.domain.Group
import ua.pp.ssenko.stories.web.GroupDto

@Service
@Transactional
class GroupService(
        private val groupRepository: GroupRepository,
        private val userRepository: UserRepository
) {

    fun createGroup(group: GroupDto, email: String): Group {
        val account = userRepository.getByEmail(email)
        return groupRepository.save(Group(
                name = group.name,
                owner = account
        ))
    }

    fun updateGroup(id: Long, groupDto: GroupDto, email: String): Group {
        val group = groupRepository.getGroup(id)
        group.assertOwner(email)
        group.apply {
            name = groupDto.name
            endDate = groupDto.endDate
            removed = groupDto.removed
        }
        return group
    }

    fun getGroupsByOwner(email: String): List<Group> {
        val account = userRepository.getByEmail(email)
        return groupRepository.findByOwnerAndRemovedIsFalse(account)
    }

}
