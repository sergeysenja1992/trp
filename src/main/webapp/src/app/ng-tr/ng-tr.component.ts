import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';

@Component({
    selector: 'ng-tr',
    template: '<ng-container>{{key | translate}}</ng-container>'
})
export class NgTrComponent implements OnInit {

  @Input('key') key: String;

  constructor() { }

  ngOnInit() {

  }

}
