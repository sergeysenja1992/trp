import {Component, Input, OnInit} from '@angular/core';
import {TrpService} from './trp.service';

@Component({
    selector: 'ng-tr',
    template: '<ng-container>{{key | translate}}</ng-container>'
})
export class NgTrComponent implements OnInit {

  @Input('key') key: string;
  @Input('alias') alias?: string;

  constructor(private trp: TrpService) { }

  ngOnInit() {
      if (this.alias) {
        this.trp.setPropertyAlias(this.alias, this.key);
      }
  }

}
