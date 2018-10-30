import { Component, OnInit, ViewChild } from '@angular/core';
import {MatDialogRef} from '@angular/material'
import {Board} from '../board.model'
import {NgForm} from '@angular/forms';
import {BoardsService} from '../boards.service'

@Component({
  selector: 'app-board-create-dialog',
  templateUrl: './board-create-dialog.component.html',
  styleUrls: ['./board-create-dialog.component.css']
})
export class BoardCreateDialogComponent implements OnInit {

    board = new Board()

    @ViewChild('form') form: NgForm;

    ngOnInit() {
    }

    constructor(
        public dialogRef: MatDialogRef<BoardCreateDialogComponent>,
        private boardsService: BoardsService
    ) {}

    cancel(): void {
        this.dialogRef.close();
    }

    save() {
        if (this.form.form.valid) {
            this.boardsService.saveBoard(this.board).subscribe(it => {
                this.dialogRef.close(true);
            });
        }
    }

}
