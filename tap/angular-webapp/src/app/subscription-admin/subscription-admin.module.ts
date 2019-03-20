import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormWizardModule } from 'angular2-wizard';

import { SubscriptionAdminRoutingModule } from './subscription-admin-routing.module';
import { SharedModule } from '../shared/shared.module';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { WrapperComponent } from './wrapper/wrapper.component';
import { PageNotfoundComponent } from './page-notfound/page-notfound.component';
import { AdminHomeContentComponent } from './admin-home-content/admin-home-content.component';
import { CustomerManagementComponent } from './customer-management/customer-management.component';
import { ResultsDownloadComponent } from './results-download/results-download.component';
import { SubscriptionService } from './subscription.service';
import { OrganizationProfileComponent } from './organization-profile/organization-profile.component';
import { OrganizationAdminsComponent } from './organization-admins/organization-admins.component';
import { AddAdministratorComponent } from './organization-admins/add-administrator/add-administrator.component';
import { ViewAdministratorComponent } from './organization-admins/view-administrator/view-administrator.component';
import { SubscriptionToolHomeComponent } from './subscription-tool-home/subscription-tool-home.component';
import { FeedbackToolComponent } from './subscription-tool-home/feedback-tool/feedback-tool.component';
import { PollToolComponent } from './subscription-tool-home/poll-tool/poll-tool.component';
import { NoticeToolComponent } from './subscription-tool-home/notice-tool/notice-tool.component';
import { FeedbackEditorComponent } from './subscription-tool-home/feedback-tool/feedback-editor/feedback-editor.component';
import { NoticeEditorComponent } from './subscription-tool-home/notice-tool/notice-editor/notice-editor.component';
import { PollEditorComponent } from './subscription-tool-home/poll-tool/poll-editor/poll-editor.component';
import { SubscriptionCustomersComponent } from './subscription-tool-home/customers/subscription-customers/subscription-customers.component';
import { PermissionDeniedComponent } from './permission-denied/permission-denied.component';
import { ResultsDetailsComponent } from './results-download/results-details/results-details.component';
import { ReportManagementComponent } from './report-management/report-management.component';

@NgModule({
  imports: [
    CommonModule,
    SubscriptionAdminRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    SharedModule,
    FormWizardModule
  ],
  declarations: [
    AdminHomeComponent,
    SidebarComponent,
    WrapperComponent,
    PageNotfoundComponent,
    AdminHomeContentComponent,
    CustomerManagementComponent,
    ResultsDownloadComponent,
    OrganizationProfileComponent,
    OrganizationAdminsComponent,
    AddAdministratorComponent,
    ViewAdministratorComponent,
    SubscriptionToolHomeComponent,
    FeedbackToolComponent,
    PollToolComponent,
    NoticeToolComponent,
    FeedbackEditorComponent,
    NoticeEditorComponent,
    PollEditorComponent,
    SubscriptionCustomersComponent,
    PermissionDeniedComponent,
    ResultsDetailsComponent,
    ReportManagementComponent
  ],
  providers: [
    SubscriptionService
  ]
})
export class SubscriptionAdminModule { }
