import { Injectable } from '@angular/core';

@Injectable()
export class TrpService {

    private translations: any = {};

    constructor() { }

    setPropertyAlias(alias: string, value: string) {
        this.translations[alias] = value;
    }

    getTr(alias: string) {
        return this.translations[alias];
    }

}
