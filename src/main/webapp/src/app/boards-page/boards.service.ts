import {HttpClient} from '@angular/common/http'
import {Injectable} from '@angular/core'
import {Board} from './board.model'

@Injectable()
export class BoardsService {

    constructor(private http: HttpClient) {
    }

    getBoards() {
        return this.http.get('/api/boards');
    }

    saveBoard(board: Board) {
        return this.http.post('/api/boards', board);
    }

}