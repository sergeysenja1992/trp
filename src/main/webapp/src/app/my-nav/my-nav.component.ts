import {ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {MediaMatcher} from '@angular/cdk/layout';
import {AccountService} from '../account/account.service'
import {ContextService} from '../context/context.service'

@Component({
  selector: 'my-nav',
  templateUrl: './my-nav.component.html',
  styleUrls: ['./my-nav.component.css']
})
export class MyNavComponent implements OnInit, OnDestroy {

    isAuthentificated = false;
    board = null;

    subscriptions = [];

    constructor(
        private accountService: AccountService,
        private contextService: ContextService
        ) {
    }

    ngOnInit(): void {
        this.isAuthentificated = this.accountService.isAuthentificated();
        this.subscriptions.push(
            this.contextService.subscribe('user', (user: any) => this.isAuthentificated = user)
        );
        this.subscriptions.push(
            this.contextService.subscribe('board', (board: any) => this.board = board)
        );
    }

    ngOnDestroy(): void {
        this.subscriptions.forEach(it => it.unsubscribe())
    }

}
