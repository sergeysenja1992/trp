import {BrowserModule} from '@angular/platform-browser';
import {ChangeDetectorRef, Injector, NgModule} from '@angular/core';
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
    MatListModule, MatMenuModule,
    MatSidenavModule, MatSortModule,
    MatTableModule,
    MatToolbarModule
} from '@angular/material';

import {WelcomePageComponent} from './welcome-page/welcome-page.component';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from '@angular/common/http';
import {AuthExpiredInterceptor} from './interceptor/auth-expired.interceptor';
import {AccountService} from './account/account.service';
import {ContextService} from './context/context.service';
import {TranslateLoader, TranslateModule, TranslatePipe} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import { NgTrComponent } from './ng-tr/ng-tr.component';
import {AngularDraggableModule} from 'angular2-draggable';
import { KindergartensComponent } from './kindergartens/kindergartens.component';
import { KindergartensService } from './kindergartens/kindergartens.service';
import { KindergartenDialogComponent } from './kindergartens/kindergarten-dialog/kindergarten-dialog.component';
import { GroupDialogComponent } from './kindergartens/group-dialog/group-dialog.component';
import { ConfirmDialogComponent } from './confirm-dialog/confirm-dialog.component';
import {TrpService} from './ng-tr/trp.service';

const appRoutes: Routes = [
    { path: '', component: WelcomePageComponent},
    { path: 'welcome-page', component: WelcomePageComponent},
    { path: 'kindergartens', component: KindergartensComponent}
];

@NgModule({
    declarations: [
        AppComponent,
        MyNavComponent,
        WelcomePageComponent,
        NgTrComponent,
        KindergartensComponent,
        KindergartenDialogComponent,
        GroupDialogComponent,
        ConfirmDialogComponent
    ],
    entryComponents: [
        KindergartenDialogComponent,
        ConfirmDialogComponent
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
        MatMenuModule,
        FormsModule,
        ReactiveFormsModule,
        MatTableModule,
        AngularDraggableModule
    ],
    providers: [
        AccountService,
        ContextService,
        TranslatePipe,
        TrpService,
        KindergartensService,
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
