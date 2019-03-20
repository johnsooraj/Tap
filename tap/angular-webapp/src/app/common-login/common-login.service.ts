import { Injectable, OnInit } from '@angular/core';
import { LoginModel } from '../common-models/login-model';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { User } from '../common-models/user';
import { UserTypes } from '../common-enums/uesr-types';
import { Router } from '@angular/router';
import { UserPermission } from '../common-enums/user-permissions';

const ROOT_URL_ADMIN = '/tap/api/admin';
const ROOT_URL_ORGANIZATION = '/tap/api/admin/organization';
const ROOT_URL_ORGANIZATION_MEMBER = '/tap/api/admin/organization/member';

const header = new HttpHeaders({
    'token': localStorage.getItem('userToken'),
});

@Injectable({
    providedIn: 'root'
})
export class CommonLoginService implements OnInit {

    userData: User;
    memberId: number;
    isUserLoggedin = false;
    redirectUrl: string;
    loginUserName: string;
    loginPassword: string;
    showTapNotification: boolean = false;

    constructor(
        private http: HttpClient,
        private router: Router,
    ) { }


    ngOnInit() { }

    userLogin(data: LoginModel): Observable<any> {
        return this.http.post<any>(ROOT_URL_ADMIN + '/login', data).pipe(
            catchError(error => {
                return this.errorHandler(error);
            })
        );
    }

    // used to fetch user data after refresh window
    reloginByUserId(userId: number): Observable<User> {
        return this.http.get<User>(ROOT_URL_ORGANIZATION_MEMBER + '/' + userId);
    }

    resetPassword(data: any): Observable<any> {
        return this.http.post(ROOT_URL_ADMIN + '/reset', data);
    }

    userLogout(data: LoginModel) {
        this.http.post<User>(ROOT_URL_ADMIN + '/logout', data).subscribe(() => {
            this.removeUserDataFromCacheAndLocal();
        });
    }

    commonLogout() {
        this.removeUserDataFromCacheAndLocal();
    }

    setUserDataToCacheAndLocal(user: User) {
        this.userData = user;   // very important task
        this.memberId = user.memberId;
        this.isUserLoggedin = true;
        localStorage.setItem('memberId', user.memberId.toString());
    }

    removeUserDataFromCacheAndLocal() {
        this.userData = null;
        this.memberId = null;
        this.isUserLoggedin = false;
        localStorage.removeItem('memberId');
        this.router.navigate(['/login']);
    }

    switchTheUrl(user: User) {
        switch (user.memberType) {
            case UserTypes.SuperAdministrator:
                this.router.navigate(['/superadmin']);
                break;
            case UserTypes.SubAdminstrator:
            case UserTypes.Administrator:
                if (user.showResetPassword) {
                    this.router.navigate(['/resetpassword']);
                } else {
                    this.router.navigate(['/subscription']);
                }
                break;
            default:
                break;
        }
    }

    checkUserPermission(permission: UserPermission): boolean {
        if (this.userData) {
            return this.userData.userPermissions.some(vendor => vendor['securityPermissionId'] === permission);
        }
        return false;
    }

    checkUserPermissionByUser(user: User, permission: UserPermission): boolean {
        if (user) {
            return user.userPermissions.some(vendor => vendor['securityPermissionId'] === permission);
        }
        return false;
    }

    errorHandler(error: HttpErrorResponse) {
        return Observable.throw(error.message);
    }

    createCustomerAccess(): boolean {
        if (this.checkUserPermission(UserPermission.ADD_CUSTOMER)) {
            return true;
        }
        return false;
    }

    createFeedbackAccess(): boolean {
        if (this.userData.memberType != UserTypes.Administrator) {
            if (this.checkUserPermission(UserPermission.ADD_FEEDBACK)) {
                return true;
            }
            return false;
        }
        return false;
    }

    createPollAccess(): boolean {
        if (this.userData.memberType != UserTypes.Administrator) {
            if (this.checkUserPermission(UserPermission.ADD_POLL)) {
                return true;
            }
            return false;
        }
        return false;
    }

    createNoticeAccess(): boolean {
        if (this.userData.memberType != UserTypes.Administrator) {
            if (this.checkUserPermission(UserPermission.ADD_NOTICE
            )) {
                return true;
            }
            return false;
        }
        return false;
    }

    deleteCustomerAccess(): boolean {
        if (this.checkUserPermission(UserPermission.ADD_CUSTOMER)) {
            return true;
        }
        return false;
    }

    deleteFeedbackAccess(): boolean {
        if (this.checkUserPermission(UserPermission.DELETE_FEEDBACK)) {
            return true;
        }
        return false;
    }

    deleteNoticeAccess(): boolean {
        if (this.checkUserPermission(UserPermission.DELETE_NOTICE)) {
            return true;
        }
        return false;
    }

    deletePollAccess(): boolean {
        if (this.checkUserPermission(UserPermission.DELETE_POLL)) {
            return true;
        }
        return false;
    }

    addCustomerAccess(): boolean {
        if (this.checkUserPermission(UserPermission.ADD_CUSTOMER) || UserTypes.Administrator) {
            return true;
        }
        return false;
    }

    removeCustomerAccess(): boolean {
        if (this.checkUserPermission(UserPermission.DELETE_CUSTOMER) || UserTypes.Administrator) {
            return true;
        }
        return false;
    }


}
