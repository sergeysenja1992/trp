import {ContextService} from '../context/context.service';
import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Kindergarten} from './kindergarten.model';
import {Observable} from 'rxjs/Rx';

@Injectable()
export class KindergartensService {

    constructor(private http: HttpClient,
                private contextService: ContextService) {
    }

    findAll(): Observable<Kindergarten[]> {
        return this.http.get<Kindergarten[]>('/api/kindergartens');
    }

    save(kindergarten: Kindergarten) {
        if (kindergarten.id) {
            return this.http.put('/api/kindergartens/' + kindergarten.id, kindergarten);
        } else {
            return this.http.post('/api/kindergartens', kindergarten);
        }
    }
}
