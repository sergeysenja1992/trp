import {Component, OnInit, Inject, ViewChild} from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import {KindergartensService} from '../kindergartens.service';
import {NgForm} from '@angular/forms';
import {Kindergarten} from '../kindergarten.model';

@Component({
  selector: 'app-kindergarten-dialog',
  templateUrl: './kindergarten-dialog.component.html',
  styleUrls: ['./kindergarten-dialog.component.css']
})
export class KindergartenDialogComponent implements OnInit {

    @ViewChild('form') form: NgForm;

    kindergarten: Kindergarten = new Kindergarten();

    constructor(
        private kindergartensService: KindergartensService,
        public dialogRef: MatDialogRef<KindergartenDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: Kindergarten
    ) {
        if (data) {
            this.kindergarten = data;
        }
    }

    ngOnInit() {
    }

    cancel(): void {
        this.dialogRef.close();
    }

    save() {
        if (this.form.form.valid) {
            this.kindergartensService.save(this.kindergarten).subscribe(k => {
                this.dialogRef.close(k);
            });
        } else {
            const controls = this.form.form.controls;
            for (const controlName in controls) {
                controls[controlName].markAsDirty();
                controls[controlName].markAsTouched();
            }
        }
        console.log(this.form.form);
    }


}
