import {Injectable} from '@angular/core'
import {HttpClient} from '@angular/common/http'
import {BehaviorSubject} from 'rxjs/Rx'
import {ContextService} from '../context/context.service'

@Injectable()
export class AccountService {

    private user: any = null;
    private userSubject = new BehaviorSubject(null);

    constructor(
        private http: HttpClient,
        private contextService: ContextService
    ) {
        this.identify().subscribe(user => this.user = user);
        this.loadUser();
    }

    isAuthentificated() {
        return this.user != null;
    }

    identify() {
        return this.userSubject;
    }

    loadUser() {
        this.http.request('GET', '/api/user').subscribe((res: any) => {
            this.userSubject.next(res);
            this.contextService.emit('user', res);
        }, (error) => {
            console.error(error);
        });
    }

    logout() {
        this.resetUser()
        this.http.post('/logout', {});
    }

    resetUser() {
        this.userSubject.next("UNAUTHORIZED");
        this.user = null;
        this.contextService.emit('user', null);
    }
}
