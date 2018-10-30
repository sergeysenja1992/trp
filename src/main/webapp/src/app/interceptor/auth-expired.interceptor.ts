import {
    HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest,
    HttpResponse
} from '@angular/common/http';
import {Injector} from '@angular/core';
import {Observable} from 'rxjs';
import {AccountService} from '../account/account.service'
import {catchError, tap} from 'rxjs/internal/operators'
import {Router} from '@angular/router'


export class AuthExpiredInterceptor implements HttpInterceptor {

    constructor(private injector: Injector) {
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(
            tap(event => {
                if (event instanceof HttpResponse) {
                }
            }, error => {
                // http response status code
                console.error("status code:");
                console.error(error.status);
                console.error(error.message);

                if (error.status == 401) {
                    const accountService: AccountService = this.injector.get(AccountService);
                    accountService.resetUser();
                    this.injector.get(Router).navigate(['/welcome-page'])
                }

            })
        );
    }
}
