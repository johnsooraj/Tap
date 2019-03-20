import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { PageNotfoundComponent } from './page-notfound/page-notfound.component';
import { CustomerManagementComponent } from './customer-management/customer-management.component';
import { ResultsDownloadComponent } from './results-download/results-download.component';
import { SubscriptionGuard } from './subscription.guard';
import { OrganizationProfileComponent } from './organization-profile/organization-profile.component';
import { OrganizationAdminsComponent } from './organization-admins/organization-admins.component';
import { ReportManagementComponent } from './report-management/report-management.component';
import { SubscriptionToolHomeComponent } from './subscription-tool-home/subscription-tool-home.component';
import { FeedbackToolComponent } from './subscription-tool-home/feedback-tool/feedback-tool.component';
import { PollToolComponent } from './subscription-tool-home/poll-tool/poll-tool.component';
import { NoticeToolComponent } from './subscription-tool-home/notice-tool/notice-tool.component';
import { FeedbackEditorComponent } from './subscription-tool-home/feedback-tool/feedback-editor/feedback-editor.component';
import { SubscriptionCustomersComponent } from './subscription-tool-home/customers/subscription-customers/subscription-customers.component';
import { NoticeEditorComponent } from './subscription-tool-home/notice-tool/notice-editor/notice-editor.component';
import { PollEditorComponent } from './subscription-tool-home/poll-tool/poll-editor/poll-editor.component';
import { PermissionDeniedComponent } from './permission-denied/permission-denied.component';

const routes: Routes = [
  {
    path: '',
    component: AdminHomeComponent,
    children: [
      {
        path: '',
        redirectTo: 'customer-managament',
        pathMatch: 'full'
      },
      {
        path: 'customer-managament',
        component: CustomerManagementComponent,
        canActivate: [SubscriptionGuard]
      },
      {
        path: 'subscription-tools',
        component: SubscriptionToolHomeComponent,
        canActivate: [SubscriptionGuard],
      },
      {
        path: 'subscription-tools/feedback',
        component: FeedbackToolComponent,
        canActivate: [SubscriptionGuard],
      },
      {
        path: 'subscription-tools/feedback/:id',
        component: FeedbackEditorComponent,
        canActivate: [SubscriptionGuard],
      },
      {
        path: 'subscription-tools/poll',
        component: PollToolComponent,
        canActivate: [SubscriptionGuard],
      },
      {
        path: 'subscription-tools/poll/:id',
        component: PollEditorComponent,
        canActivate: [SubscriptionGuard],
      },
      {
        path: 'subscription-tools/notice',
        component: NoticeToolComponent,
        canActivate: [SubscriptionGuard],
      },
      {
        path: 'subscription-tools/notice/:id',
        component: NoticeEditorComponent,
        canActivate: [SubscriptionGuard],
      },
      {
        path: 'subscription-tools/forward',
        component: SubscriptionCustomersComponent,
        canActivate: [SubscriptionGuard],
      },
      {
        path: 'download-results',
        component: ResultsDownloadComponent,
        canActivate: [SubscriptionGuard]
      },
      {
        path: 'edit-profile',
        component: OrganizationProfileComponent,
        canActivate: [SubscriptionGuard]
      },
      {
        path: 'admin',
        component: OrganizationAdminsComponent,
        canActivate: [SubscriptionGuard]
      },
      {
        path: 'report-management',
        component: ReportManagementComponent,
        canActivate: [SubscriptionGuard]
      },
      {
        path: 'access-denied',
        component: PermissionDeniedComponent,
      },
      {
        path: '**',
        component: PageNotfoundComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SubscriptionAdminRoutingModule { }
