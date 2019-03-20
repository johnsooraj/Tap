import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgbPaginationModule, NgbAlertModule } from '@ng-bootstrap/ng-bootstrap';
import { NgProgressModule } from '@ngx-progressbar/core';
import { NgxNotificationComponent } from 'ngx-notification';
import { FormWizardModule } from 'angular2-wizard';

import { AppComponent } from './app.component';
import { CommonLoginComponent } from './common-login/common-login.component';
import { AppRoutingModule } from './app-routing.module';
import { PageNotfoundComponent } from './page-notfound/page-notfound.component';
import { CommonLoginService } from './common-login/common-login.service';
import { ResetPasswordComponent } from './reset-password/reset-password.component';

@NgModule({
  declarations: [
    AppComponent,
    CommonLoginComponent,
    PageNotfoundComponent,
    ResetPasswordComponent,
    NgxNotificationComponent
  ],
  imports: [
    NgbModule,
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    NgProgressModule.forRoot(),
    NgbPaginationModule,
    NgbAlertModule,
    FormWizardModule
  ],
  providers: [CommonLoginService],
  bootstrap: [AppComponent]
})
export class AppModule { }
