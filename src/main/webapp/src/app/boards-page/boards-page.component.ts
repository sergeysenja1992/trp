import {Component, OnInit, ViewChild} from '@angular/core';
import {MatDialog, MatSort, MatTableDataSource, Sort} from '@angular/material'
import {Board} from './board.model'
import {BoardsService} from './boards.service'
import {map} from 'rxjs-compat/operator/map'
import {BoardCreateDialogComponent} from './board-create-dialog/board-create-dialog.component'

@Component({
  selector: 'app-boards-page',
  templateUrl: './boards-page.component.html',
  styleUrls: ['./boards-page.component.css']
})
export class BoardsPageComponent implements OnInit {

    displayedColumns: string[] = ['name', 'description'];
    boards: Board[] = [];

    constructor(
        private boardService: BoardsService,
        public dialog: MatDialog
    ) {
        this.load();
    }

    private load() {
        this.boardService.getBoards().subscribe((boards: any) => {
            this.boards = boards.ownBoards;
        });
    }

    openDialog(): void {
        const dialogRef = this.dialog.open(BoardCreateDialogComponent);

        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.load();
            }
        });
    }

    ngOnInit() {

    }

    sortData(sort: Sort) {
        const data = this.boards.slice();
        if (!sort.active || sort.direction === '') {
            this.boards = data;
            return;
        }

        this.boards = data.sort((a, b) => {
            const isAsc = sort.direction === 'asc';
            return this.compare(a[sort.active], b[sort.active], isAsc);
        });
    }

    compare(a, b, isAsc) {
        return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
    }


}
