import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {KindergartensService} from './kindergartens.service';
import {MatDialog} from '@angular/material';
import {KindergartenDialogComponent} from './kindergarten-dialog/kindergarten-dialog.component';
import {Kindergarten} from './kindergarten.model';

@Component({
  selector: 'app-kindergartens',
  templateUrl: './kindergartens.component.html',
  styleUrls: ['./kindergartens.component.css']
})
export class KindergartensComponent implements OnInit {

    kindergartens: Kindergarten[];

    constructor(
        private kindergartenService: KindergartensService,
        private dialog: MatDialog,
        private http: HttpClient
    ) {

    }

    openDialog(): void {
        const dialogRef = this.dialog.open(KindergartenDialogComponent, {
            width: '500px'
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.load();
            }
        });
    }

    ngOnInit() {
        this.load();
    }

    private load() {
        this.kindergartenService.findAll().subscribe((next: Kindergarten[]) => {
            console.log(next);
            this.kindergartens = next;
        });
    }

    remove(kindergarten: Kindergarten) {

    }

    edit(kindergarten: Kindergarten) {
        const dialogRef = this.dialog.open(KindergartenDialogComponent, {
            data: kindergarten,
            width: '500px'
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.load();
            }
        });
    }
}
