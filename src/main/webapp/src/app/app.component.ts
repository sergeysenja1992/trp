import {Component, OnInit} from '@angular/core';
import {Event, Router, RouterEvent} from '@angular/router'
import {AccountService} from './account/account.service'
import {TranslateService} from '@ngx-translate/core'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

    title = 'app';

    constructor(
        private router: Router,
        private accountService: AccountService,
        private translate: TranslateService
    ) {
        translate.addLangs(['en']);
        translate.setDefaultLang('en');
        const browserLang = translate.getBrowserLang();
        translate.use(browserLang.match(/en/) ? browserLang : 'en');
    }

    ngOnInit(): void {

    }
}
