import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { CommonLoginService } from './common-login/common-login.service';

@Injectable({
  providedIn: 'root'
})
export class AppAuthGuard implements CanActivate {

  constructor(private loginService: CommonLoginService, private router: Router) {

  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {

    // console.log('AppAuthGuard() - ' + state.url);

    this.loginService.redirectUrl = state.url;
    if (state.url === '/login') {
      return !this.loginService.isUserLoggedin;
    }

    if (this.loginService.isUserLoggedin) {
      return true;
    } else {
      const userId = localStorage.getItem('memberId');
      if (userId === undefined || userId == null) {
        this.router.navigate(['/login']);
        return false;
      }
      if (+userId > 0) {
        this.loginService.reloginByUserId(+userId).subscribe((data) => {
          // console.log(data);
          this.loginService.setUserDataToCacheAndLocal(data);
          this.router.navigate([`${this.loginService.redirectUrl}`]);
        }, (error) =>{
          console.log(error);
          this.router.navigate(['/xyz']);
        });
      }
      return false;
    }

  }
}
