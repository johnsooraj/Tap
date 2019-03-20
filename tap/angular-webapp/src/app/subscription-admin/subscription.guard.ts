import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { UserTypes } from '../common-enums/uesr-types';
import { CommonLoginService } from '../common-login/common-login.service';
import { SubscriptionService } from './subscription.service';
import { UserPermission } from '../common-enums/user-permissions';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionGuard implements CanActivate {

  constructor(
    private commonService: CommonLoginService,
    private localService: SubscriptionService,
    private router: Router
  ) {
    // console.log('router');
  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    const url = state.url;
    if (this.commonService.isUserLoggedin) {
      switch (state.url) {
        case '/subscription/customer-managament':
          return this.customerManagementHandler(state.url);
          break;
        case '/subscription/subscription-tools':
          return this.subscriptionToolHandler(state.url);
          break;
        case '/subscription/download-results':
          return this.downloadResultsHandler(state.url);
          break;
        case '/subscription/edit-profile':
          return this.editProfileHandler(state.url);
          break;
        case '/subscription/admin':
          return this.adminHandler(state.url);
          break;
        default:
          return true;
          break;
      }
      return false;
    } else {
      return false;
    }
  }

  customerManagementHandler(nexturl) {
    if (this.commonService.userData.memberType === UserTypes.Administrator) {
      return true;
    }
    if (this.commonService.userData.memberType === UserTypes.SubAdminstrator) {
      if (this.commonService.checkUserPermission(UserPermission.CUSTOMER_MANAGEMENT) || this.commonService.checkUserPermission(UserPermission.VIEW_CUSTOMER)) {
        return true;
      } else {
        this.router.navigate(['/subscription/access-denied']);
      }
    }
  }

  subscriptionToolHandler(nexturl) {
    if (this.commonService.userData.memberType === UserTypes.Administrator) {
      return true;
    }
    if (this.commonService.userData.memberType === UserTypes.SubAdminstrator) {
      if (this.commonService.checkUserPermission(UserPermission.SUBSCRIPTION_TOOL)) {
        return true;
      }
      else if (this.commonService.checkUserPermission(UserPermission.VIEW_FEEDBACK)) {
        return true;
      }
      else if (this.commonService.checkUserPermission(UserPermission.VIEW_POLL)) {
        return true;
      }
      else if (this.commonService.checkUserPermission(UserPermission.VIEW_NOTICE)) {
        return true;
      }
      else {
        this.router.navigate(['/subscription/access-denied']);
      }
    }
  }

  downloadResultsHandler(nexturl) {
    if (this.commonService.userData.memberType === UserTypes.Administrator) {
      return true;
    }
    if (this.commonService.userData.memberType === UserTypes.SubAdminstrator) {
      if (this.commonService.checkUserPermission(UserPermission.VIEW_DOWNLOAD_RESULT) ||
        this.commonService.checkUserPermission(UserPermission.VIEW_RESULT) ||
        this.commonService.checkUserPermission(UserPermission.DOWNLOAD_RESULT)) {
        return true;
      }
      else {
        this.router.navigate(['/subscription/access-denied']);
      }
    }
  }

  editProfileHandler(nexturl) {
    if (this.commonService.userData.memberType === UserTypes.Administrator) {
      return true;
    }
    if (this.commonService.userData.memberType === UserTypes.SubAdminstrator) {
      if (this.commonService.checkUserPermission(UserPermission.VIEW_EDIT_PROFILE)) {
        return true;
      } else {
        this.router.navigate(['/subscription/access-denied']);
      }
    }
  }

  adminHandler(nexturl) {
    if (this.commonService.userData.memberType === UserTypes.Administrator) {
      return true;
    }
  }

}
