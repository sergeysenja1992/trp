package ua.pp.ssenko.stories.web

import getEmail
import org.springframework.web.bind.annotation.*
import ua.pp.ssenko.stories.service.BoardDto
import ua.pp.ssenko.stories.service.BoardService
import java.security.Principal

@RestController
@RequestMapping("/api")
class BoardRestController (
        private val boardService: BoardService
) {
    @PostMapping("/boards")
    fun createBoard(@RequestBody board: BoardDto, principal: Principal) {
        boardService.addBoard(principal.getEmail(), board)
    }

    @GetMapping("/boards")
    fun createBoard(principal: Principal) = boardService.getAccountBoards(principal.getEmail())

}
