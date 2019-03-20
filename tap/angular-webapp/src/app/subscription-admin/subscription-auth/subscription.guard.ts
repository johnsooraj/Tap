import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, CanLoad, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { CommonLoginService } from '../../common-login/common-login.service';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionGuard implements CanActivate {


  constructor(private commonService: CommonLoginService, private router: Router) {

  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {

    if (this.commonService.isUserLoggedin) {
      return true;
    }
  }

  resetPasswordHandler(): boolean {
    if (this.commonService.isUserLoggedin) {
      if (this.commonService.userData.showResetPassword) {
        return true;
      } else {
        this.router.navigate(['/customer-managament']);
        return false;
      }
    }
  }

}
