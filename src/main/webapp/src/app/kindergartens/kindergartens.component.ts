import {Component, OnInit} from '@angular/core';
import {KindergartensService} from './kindergartens.service';
import {MatDialog} from '@angular/material';
import {KindergartenDialogComponent} from './kindergarten-dialog/kindergarten-dialog.component';
import {Kindergarten} from './kindergarten.model';
import {ConfirmDialogComponent} from '../confirm-dialog/confirm-dialog.component';
import {TrpService} from '../ng-tr/trp.service';

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
        private trp: TrpService
    ) {

    }

    ngOnInit() {
        this.load();
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


    private load() {
        this.kindergartenService.findAll().subscribe((next: Kindergarten[]) => {
            console.log(next);
            this.kindergartens = next;
        });
    }

    remove(kindergarten: Kindergarten) {
        const dialogRef = this.dialog.open(ConfirmDialogComponent, {
            data: this.trp.getTr('deleteKinderGarden') + " " + kindergarten.name + "?"
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.kindergartenService.remove(kindergarten.id).subscribe(r => {
                    console.log(r);
                    this.load();
                });
            }
        });
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
