import { Injectable } from '@angular/core';
import {TranslatePipe, TranslateService} from '@ngx-translate/core';

@Injectable()
export class TrpService {

    private translations: any = {};

    constructor(private trPipe: TranslateService) { }

    setPropertyAlias(alias: string, value: string) {
        this.translations[alias] = this.trPipe.instant(value);
    }

    getTr(alias: string) {
        return this.translations[alias];
    }

}
