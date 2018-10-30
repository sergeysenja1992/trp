import {Injectable} from '@angular/core'
import {HttpClient} from '@angular/common/http'
import {Router} from '@angular/router'
import {Observable, Subject, Subscriber} from 'rxjs/Rx'

@Injectable()
export class ContextService {

    private contextMap: any = {};

    constructor(
    ) {
    }

    subscribe(name, subscription) {
        this.contextMap[name] = this.contextMap[name] || new Subject();
        return this.contextMap[name].subscribe(subscription);
    }

    emit(name, body) {
        if (this.contextMap[name]) {
            this.contextMap[name].next(body);
        }
    }

}