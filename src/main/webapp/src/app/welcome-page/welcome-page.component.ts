import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router'
import {AccountService} from '../account/account.service'

@Component({
  selector: 'app-welcome-page',
  templateUrl: './welcome-page.component.html',
  styleUrls: ['./welcome-page.component.css']
})
export class WelcomePageComponent implements OnInit {

  isAuthenticated = true;

  constructor(
      private router: Router,
      private accountService: AccountService
  ) { }

  ngOnInit() {
    this.accountService.identify().subscribe(user => {
        this.isAuthenticated = user;
        if (this.isAuthenticated) {
            console.log('User authenticated');
            this.router.navigate(['/boards-page'])
        }
        console.log(this.isAuthenticated);
    });
  }

  singIn() {
     window.location.href = '/google/login';
  }

}
