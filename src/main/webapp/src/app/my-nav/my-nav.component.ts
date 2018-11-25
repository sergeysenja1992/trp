import {ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {MediaMatcher} from '@angular/cdk/layout';
import {AccountService} from '../account/account.service';
import {ContextService} from '../context/context.service';
import {Location} from '@angular/common';
import {NavigationEnd, Router} from '@angular/router';

@Component({
  selector: 'my-nav',
  templateUrl: './my-nav.component.html',
  styleUrls: ['./my-nav.component.css']
})
export class MyNavComponent implements OnInit, OnDestroy {

    isAuthentificated = false;
    board = null;
    prevPath = null;

    subscriptions = [];

    constructor(
        private router: Router,
        private accountService: AccountService,
        private contextService: ContextService,
        private location: Location,
        ) {
        router.events.forEach((event) => {
            // console.log(event);
            if (event instanceof NavigationEnd) {
                this.accountService.identify().subscribe(user => {
                    this.redirect(user);
                });
            }
        });
    }

    private redirect(user) {
        if (user === 'UNAUTHORIZED') {
            this.router.navigate(['']);
        } else {
            this.isAuthentificated = user;
        }
    }

    ngOnInit(): void {
        this.isAuthentificated = this.accountService.isAuthentificated();
        this.subscriptions.push(
            this.contextService.subscribe('user', (user: any) => {
                this.redirect(user);
            })
        );
        console.log(this.location.path());
    }

    ngOnDestroy(): void {
        this.subscriptions.forEach(it => it.unsubscribe());
    }

    onBack() {
        this.prevPath = this.location.path(true);
        this.location.back();
    }

}
