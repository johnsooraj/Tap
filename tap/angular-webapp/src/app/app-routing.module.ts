import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { CommonLoginComponent } from './common-login/common-login.component';
import { PageNotfoundComponent } from './page-notfound/page-notfound.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { AppAuthGuard } from './app-auth.guard';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: CommonLoginComponent,
    canActivate: [AppAuthGuard]
  },
  {
    path: 'subscription',
    loadChildren: './subscription-admin/subscription-admin.module#SubscriptionAdminModule',
    canActivate: [AppAuthGuard]
  },
  {
    path: 'superadmin',
    loadChildren: './super-admin/super-admin.module#SuperAdminModule',
    canActivate: [AppAuthGuard]
  },
  {
    path: 'resetpassword',
    component: ResetPasswordComponent,
    canActivate: [AppAuthGuard]
  },
  {
    path: 'logout',
    redirectTo: '/login',
  },
  {
    path: '**',
    component: PageNotfoundComponent
  }
]

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(routes,
      {
        useHash: true,
      })
  ],
  declarations: [],
  exports: [RouterModule]
})
export class AppRoutingModule { }
