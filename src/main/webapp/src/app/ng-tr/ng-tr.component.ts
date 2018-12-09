import {Component, Input, OnInit} from '@angular/core';
import {TranslatePipe} from '@ngx-translate/core';
import {TrpService} from './trp.service';

@Component({
    selector: 'ng-tr',
    template: '<ng-container>{{key | translate}}</ng-container>'
})
export class NgTrComponent implements OnInit {

  @Input('key') key: string;
  @Input('alias') alias: string;

  constructor(private translatePipe: TranslatePipe,
              private trp: TrpService) { }

  ngOnInit() {
      this.trp.setPropertyAlias(this.alias, this.translatePipe.transform(this.key));
  }

}
