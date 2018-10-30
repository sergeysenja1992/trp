package ua.pp.ssenko.stories.service

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ua.pp.ssenko.stories.domain.Board
import ua.pp.ssenko.stories.domain.Account
import ua.pp.ssenko.stories.domain.BoardShare

@Service
@Transactional
class BoardService(
        private val boardRepository: BoardRepository,
        private val boardShareRepository: BoardShareRepository,
        private val userRepository: UserRepository
) {


    fun addBoard(email: String, boardDto: BoardDto): Board {
        val account = userRepository.getByEmail(email)
        val board = boardDto.toBoard(account)
        return boardRepository.save(board)
    }

    @Transactional(readOnly = true)
    fun getAccountBoards(email: String): UserBoards {
        return UserBoards(
                boardRepository.findByOwnerEmail(email),
                boardShareRepository.findByAccountEmail(email).map { it.board }
        )
    }

}


interface BoardShareRepository: CrudRepository<BoardShare, Long> {
    fun findByAccount(account: Account): List<BoardShare>
    fun findByAccountEmail(email: String): List<BoardShare>
    fun findByBoard(board: Board): List<BoardShare>
}

interface BoardRepository: CrudRepository<Board, Long> {
    fun findByOwner(owner: Account): List<Board>
    fun findByOwnerEmail(email: String): List<Board>
}