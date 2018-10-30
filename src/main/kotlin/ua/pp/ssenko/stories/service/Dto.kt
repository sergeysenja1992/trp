package ua.pp.ssenko.stories.service

import ua.pp.ssenko.stories.domain.Account
import ua.pp.ssenko.stories.domain.Board

data class BoardDto (
        val name: String,
        val description: String = ""
) {
    fun toBoard(account: Account) = Board(name, account, description)
}

data class UserBoards (
        val ownBoards: List<Board>,
        val sharedBoards: List<Board>
)