import {BrowserModule} from '@angular/platform-browser';
import {Injector, NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MyNavComponent} from './my-nav/my-nav.component';
import {LayoutModule} from '@angular/cdk/layout';
import {
    MatButtonModule,
    MatCardModule, MatDialogModule, MatFormFieldModule,
    MatGridListModule, MatHint,
    MatIconModule, MatInputModule,
    MatListModule,
    MatSidenavModule, MatSortModule,
    MatTableModule,
    MatToolbarModule
} from '@angular/material';

import {WelcomePageComponent} from './welcome-page/welcome-page.component';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from '@angular/common/http';
import {AuthExpiredInterceptor} from './interceptor/auth-expired.interceptor';
import {AccountService} from './account/account.service';
import {ContextService} from './context/context.service';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import { NgTrComponent } from './ng-tr/ng-tr.component';
import {AngularDraggableModule} from 'angular2-draggable';

const appRoutes: Routes = [
    { path: '', component: WelcomePageComponent},
    { path: 'welcome-page', component: WelcomePageComponent}
];

@NgModule({
    declarations: [
        AppComponent,
        MyNavComponent,
        WelcomePageComponent,
        NgTrComponent
    ],
    entryComponents: [

    ],
    imports: [
        BrowserModule,
        TranslateModule.forRoot({
            loader: {
                provide: TranslateLoader,
                useFactory: HttpLoaderFactory,
                deps: [HttpClient]
            }
        }),
        BrowserAnimationsModule,
        RouterModule.forRoot(appRoutes, {useHash: true}),
        LayoutModule,
        MatToolbarModule,
        MatButtonModule,
        MatSidenavModule,
        MatIconModule,
        MatListModule,
        MatCardModule,
        MatGridListModule,
        HttpClientModule,
        MatSortModule,
        MatDialogModule,
        MatInputModule,
        MatFormFieldModule,
        FormsModule,
        ReactiveFormsModule,
        MatTableModule,
        AngularDraggableModule
    ],
    providers: [
        AccountService,
        ContextService,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true,
            deps: [
                Injector
            ]
        }
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }

// required for AOT compilation
export function HttpLoaderFactory(http: HttpClient) {
    return new TranslateHttpLoader(http);
}
