(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["main"],{

/***/ "./src/$$_lazy_route_resource lazy recursive":
/*!**********************************************************!*\
  !*** ./src/$$_lazy_route_resource lazy namespace object ***!
  \**********************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var map = {
	"./subscription-admin/subscription-admin.module": [
		"./src/app/subscription-admin/subscription-admin.module.ts",
		"subscription-admin-subscription-admin-module"
	],
	"./super-admin/super-admin.module": [
		"./src/app/super-admin/super-admin.module.ts",
		"super-admin-super-admin-module"
	]
};
function webpackAsyncContext(req) {
	var ids = map[req];
	if(!ids) {
		return Promise.resolve().then(function() {
			var e = new Error("Cannot find module '" + req + "'");
			e.code = 'MODULE_NOT_FOUND';
			throw e;
		});
	}
	return __webpack_require__.e(ids[1]).then(function() {
		var id = ids[0];
		return __webpack_require__(id);
	});
}
webpackAsyncContext.keys = function webpackAsyncContextKeys() {
	return Object.keys(map);
};
webpackAsyncContext.id = "./src/$$_lazy_route_resource lazy recursive";
module.exports = webpackAsyncContext;

/***/ }),

/***/ "./src/app/app-auth.guard.ts":
/*!***********************************!*\
  !*** ./src/app/app-auth.guard.ts ***!
  \***********************************/
/*! exports provided: AppAuthGuard */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppAuthGuard", function() { return AppAuthGuard; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _common_login_common_login_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./common-login/common-login.service */ "./src/app/common-login/common-login.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var AppAuthGuard = /** @class */ (function () {
    function AppAuthGuard(loginService, router) {
        this.loginService = loginService;
        this.router = router;
    }
    AppAuthGuard.prototype.canActivate = function (next, state) {
        // console.log('AppAuthGuard() - ' + state.url);
        var _this = this;
        this.loginService.redirectUrl = state.url;
        if (state.url === '/login') {
            return !this.loginService.isUserLoggedin;
        }
        if (this.loginService.isUserLoggedin) {
            return true;
        }
        else {
            var userId = localStorage.getItem('memberId');
            if (userId === undefined || userId == null) {
                this.router.navigate(['/login']);
                return false;
            }
            if (+userId > 0) {
                this.loginService.reloginByUserId(+userId).subscribe(function (data) {
                    // console.log(data);
                    _this.loginService.setUserDataToCacheAndLocal(data);
                    _this.router.navigate(["" + _this.loginService.redirectUrl]);
                }, function (error) {
                    console.log(error);
                    _this.router.navigate(['/xyz']);
                });
            }
            return false;
        }
    };
    AppAuthGuard = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Injectable"])({
            providedIn: 'root'
        }),
        __metadata("design:paramtypes", [_common_login_common_login_service__WEBPACK_IMPORTED_MODULE_2__["CommonLoginService"], _angular_router__WEBPACK_IMPORTED_MODULE_1__["Router"]])
    ], AppAuthGuard);
    return AppAuthGuard;
}());



/***/ }),

/***/ "./src/app/app-routing.module.ts":
/*!***************************************!*\
  !*** ./src/app/app-routing.module.ts ***!
  \***************************************/
/*! exports provided: AppRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppRoutingModule", function() { return AppRoutingModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common */ "./node_modules/@angular/common/fesm5/common.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _common_login_common_login_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./common-login/common-login.component */ "./src/app/common-login/common-login.component.ts");
/* harmony import */ var _page_notfound_page_notfound_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./page-notfound/page-notfound.component */ "./src/app/page-notfound/page-notfound.component.ts");
/* harmony import */ var _reset_password_reset_password_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./reset-password/reset-password.component */ "./src/app/reset-password/reset-password.component.ts");
/* harmony import */ var _app_auth_guard__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./app-auth.guard */ "./src/app/app-auth.guard.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};







var routes = [
    {
        path: '',
        redirectTo: '/login',
        pathMatch: 'full'
    },
    {
        path: 'login',
        component: _common_login_common_login_component__WEBPACK_IMPORTED_MODULE_3__["CommonLoginComponent"],
        canActivate: [_app_auth_guard__WEBPACK_IMPORTED_MODULE_6__["AppAuthGuard"]]
    },
    {
        path: 'subscription',
        loadChildren: './subscription-admin/subscription-admin.module#SubscriptionAdminModule',
        canActivate: [_app_auth_guard__WEBPACK_IMPORTED_MODULE_6__["AppAuthGuard"]]
    },
    {
        path: 'superadmin',
        loadChildren: './super-admin/super-admin.module#SuperAdminModule',
        canActivate: [_app_auth_guard__WEBPACK_IMPORTED_MODULE_6__["AppAuthGuard"]]
    },
    {
        path: 'resetpassword',
        component: _reset_password_reset_password_component__WEBPACK_IMPORTED_MODULE_5__["ResetPasswordComponent"],
        canActivate: [_app_auth_guard__WEBPACK_IMPORTED_MODULE_6__["AppAuthGuard"]]
    },
    {
        path: 'logout',
        redirectTo: '/login',
    },
    {
        path: '**',
        component: _page_notfound_page_notfound_component__WEBPACK_IMPORTED_MODULE_4__["PageNotfoundComponent"]
    }
];
var AppRoutingModule = /** @class */ (function () {
    function AppRoutingModule() {
    }
    AppRoutingModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [
                _angular_common__WEBPACK_IMPORTED_MODULE_1__["CommonModule"],
                _angular_router__WEBPACK_IMPORTED_MODULE_2__["RouterModule"].forRoot(routes, {
                    useHash: true,
                })
            ],
            declarations: [],
            exports: [_angular_router__WEBPACK_IMPORTED_MODULE_2__["RouterModule"]]
        })
    ], AppRoutingModule);
    return AppRoutingModule;
}());



/***/ }),

/***/ "./src/app/app.component.css":
/*!***********************************!*\
  !*** ./src/app/app.component.css ***!
  \***********************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/app.component.html":
/*!************************************!*\
  !*** ./src/app/app.component.html ***!
  \************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<ng-progress [direction]=\"'ltr+'\" [speed]=\"500\" [trickleSpeed]=\"100\" [spinner]=\"true\"\r\n    [spinnerPosition]=\"'right'\" [thick]=\"true\" [meteor]=\"true\" [color]=\"'#00ff40'\" [ease]=\"'linear'\"></ng-progress>\r\n<router-outlet></router-outlet>\r\n<lib-ngx-notification></lib-ngx-notification>"

/***/ }),

/***/ "./src/app/app.component.ts":
/*!**********************************!*\
  !*** ./src/app/app.component.ts ***!
  \**********************************/
/*! exports provided: AppComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppComponent", function() { return AppComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _common_login_common_login_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./common-login/common-login.service */ "./src/app/common-login/common-login.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var AppComponent = /** @class */ (function () {
    function AppComponent(commonService) {
        this.commonService = commonService;
    }
    AppComponent.prototype.ngOnInit = function () {
    };
    AppComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-root',
            template: __webpack_require__(/*! ./app.component.html */ "./src/app/app.component.html"),
            styles: [__webpack_require__(/*! ./app.component.css */ "./src/app/app.component.css")]
        }),
        __metadata("design:paramtypes", [_common_login_common_login_service__WEBPACK_IMPORTED_MODULE_1__["CommonLoginService"]])
    ], AppComponent);
    return AppComponent;
}());



/***/ }),

/***/ "./src/app/app.module.ts":
/*!*******************************!*\
  !*** ./src/app/app.module.ts ***!
  \*******************************/
/*! exports provided: AppModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppModule", function() { return AppModule; });
/* harmony import */ var _angular_platform_browser__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/platform-browser */ "./node_modules/@angular/platform-browser/fesm5/platform-browser.js");
/* harmony import */ var _angular_platform_browser_animations__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/platform-browser/animations */ "./node_modules/@angular/platform-browser/fesm5/animations.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @ng-bootstrap/ng-bootstrap */ "./node_modules/@ng-bootstrap/ng-bootstrap/fesm5/ng-bootstrap.js");
/* harmony import */ var _ngx_progressbar_core__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @ngx-progressbar/core */ "./node_modules/@ngx-progressbar/core/fesm5/ngx-progressbar-core.js");
/* harmony import */ var ngx_notification__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ngx-notification */ "./node_modules/ngx-notification/fesm5/ngx-notification.js");
/* harmony import */ var angular2_wizard__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! angular2-wizard */ "./node_modules/angular2-wizard/dist/index.js");
/* harmony import */ var angular2_wizard__WEBPACK_IMPORTED_MODULE_8___default = /*#__PURE__*/__webpack_require__.n(angular2_wizard__WEBPACK_IMPORTED_MODULE_8__);
/* harmony import */ var _app_component__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! ./app.component */ "./src/app/app.component.ts");
/* harmony import */ var _common_login_common_login_component__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! ./common-login/common-login.component */ "./src/app/common-login/common-login.component.ts");
/* harmony import */ var _app_routing_module__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! ./app-routing.module */ "./src/app/app-routing.module.ts");
/* harmony import */ var _page_notfound_page_notfound_component__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! ./page-notfound/page-notfound.component */ "./src/app/page-notfound/page-notfound.component.ts");
/* harmony import */ var _common_login_common_login_service__WEBPACK_IMPORTED_MODULE_13__ = __webpack_require__(/*! ./common-login/common-login.service */ "./src/app/common-login/common-login.service.ts");
/* harmony import */ var _reset_password_reset_password_component__WEBPACK_IMPORTED_MODULE_14__ = __webpack_require__(/*! ./reset-password/reset-password.component */ "./src/app/reset-password/reset-password.component.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
















var AppModule = /** @class */ (function () {
    function AppModule() {
    }
    AppModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_2__["NgModule"])({
            declarations: [
                _app_component__WEBPACK_IMPORTED_MODULE_9__["AppComponent"],
                _common_login_common_login_component__WEBPACK_IMPORTED_MODULE_10__["CommonLoginComponent"],
                _page_notfound_page_notfound_component__WEBPACK_IMPORTED_MODULE_12__["PageNotfoundComponent"],
                _reset_password_reset_password_component__WEBPACK_IMPORTED_MODULE_14__["ResetPasswordComponent"],
                ngx_notification__WEBPACK_IMPORTED_MODULE_7__["NgxNotificationComponent"]
            ],
            imports: [
                _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_5__["NgbModule"],
                _angular_platform_browser__WEBPACK_IMPORTED_MODULE_0__["BrowserModule"],
                _angular_platform_browser_animations__WEBPACK_IMPORTED_MODULE_1__["BrowserAnimationsModule"],
                _app_routing_module__WEBPACK_IMPORTED_MODULE_11__["AppRoutingModule"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_3__["ReactiveFormsModule"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_3__["FormsModule"],
                _angular_common_http__WEBPACK_IMPORTED_MODULE_4__["HttpClientModule"],
                _ngx_progressbar_core__WEBPACK_IMPORTED_MODULE_6__["NgProgressModule"].forRoot(),
                _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_5__["NgbPaginationModule"],
                _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_5__["NgbAlertModule"],
                angular2_wizard__WEBPACK_IMPORTED_MODULE_8__["FormWizardModule"]
            ],
            providers: [_common_login_common_login_service__WEBPACK_IMPORTED_MODULE_13__["CommonLoginService"]],
            bootstrap: [_app_component__WEBPACK_IMPORTED_MODULE_9__["AppComponent"]]
        })
    ], AppModule);
    return AppModule;
}());



/***/ }),

/***/ "./src/app/common-enums/uesr-types.ts":
/*!********************************************!*\
  !*** ./src/app/common-enums/uesr-types.ts ***!
  \********************************************/
/*! exports provided: UserTypes */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UserTypes", function() { return UserTypes; });
var UserTypes;
(function (UserTypes) {
    UserTypes[UserTypes["SuperAdministrator"] = 100] = "SuperAdministrator";
    UserTypes[UserTypes["Administrator"] = 101] = "Administrator";
    UserTypes[UserTypes["SubAdminstrator"] = 102] = "SubAdminstrator";
    UserTypes[UserTypes["GuestUser"] = 103] = "GuestUser";
    UserTypes[UserTypes["RegistredUser"] = 104] = "RegistredUser";
})(UserTypes || (UserTypes = {}));


/***/ }),

/***/ "./src/app/common-enums/user-permissions.ts":
/*!**************************************************!*\
  !*** ./src/app/common-enums/user-permissions.ts ***!
  \**************************************************/
/*! exports provided: UserPermission */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UserPermission", function() { return UserPermission; });
var UserPermission;
(function (UserPermission) {
    UserPermission[UserPermission["FULL_CUSTOMER"] = 100] = "FULL_CUSTOMER";
    UserPermission[UserPermission["VIEW_CUSTOMER"] = 101] = "VIEW_CUSTOMER";
    UserPermission[UserPermission["ADD_CUSTOMER"] = 102] = "ADD_CUSTOMER";
    UserPermission[UserPermission["DELETE_CUSTOMER"] = 103] = "DELETE_CUSTOMER";
    UserPermission[UserPermission["EDIT_CUSTOMER"] = 104] = "EDIT_CUSTOMER";
    UserPermission[UserPermission["VIEW_RESULT"] = 111] = "VIEW_RESULT";
    UserPermission[UserPermission["DOWNLOAD_RESULT"] = 112] = "DOWNLOAD_RESULT";
    UserPermission[UserPermission["FULL_FEEDBACK"] = 120] = "FULL_FEEDBACK";
    UserPermission[UserPermission["VIEW_FEEDBACK"] = 121] = "VIEW_FEEDBACK";
    UserPermission[UserPermission["EDIT_FEEDBACK"] = 122] = "EDIT_FEEDBACK";
    UserPermission[UserPermission["DELETE_FEEDBACK"] = 123] = "DELETE_FEEDBACK";
    UserPermission[UserPermission["ADD_FEEDBACK"] = 124] = "ADD_FEEDBACK";
    UserPermission[UserPermission["FULL_POLL"] = 130] = "FULL_POLL";
    UserPermission[UserPermission["VIEW_POLL"] = 131] = "VIEW_POLL";
    UserPermission[UserPermission["EDIT_POLL"] = 132] = "EDIT_POLL";
    UserPermission[UserPermission["DELETE_POLL"] = 133] = "DELETE_POLL";
    UserPermission[UserPermission["ADD_POLL"] = 134] = "ADD_POLL";
    UserPermission[UserPermission["FULL_NOTICE"] = 140] = "FULL_NOTICE";
    UserPermission[UserPermission["VIEW_NOTICE"] = 141] = "VIEW_NOTICE";
    UserPermission[UserPermission["EDIT_NOTICE"] = 142] = "EDIT_NOTICE";
    UserPermission[UserPermission["DELETE_NOTICE"] = 143] = "DELETE_NOTICE";
    UserPermission[UserPermission["ADD_NOTICE"] = 144] = "ADD_NOTICE";
    UserPermission[UserPermission["FULL_PROFILE"] = 150] = "FULL_PROFILE";
    UserPermission[UserPermission["VIEW_PROFILE"] = 151] = "VIEW_PROFILE";
    UserPermission[UserPermission["EDIT_PROFILE"] = 152] = "EDIT_PROFILE";
    UserPermission[UserPermission["DELETE_PROFILE"] = 153] = "DELETE_PROFILE";
    UserPermission[UserPermission["ADD_PROFILE"] = 154] = "ADD_PROFILE";
    UserPermission[UserPermission["CUSTOMER_MANAGEMENT"] = 160] = "CUSTOMER_MANAGEMENT";
    UserPermission[UserPermission["SUBSCRIPTION_TOOL"] = 161] = "SUBSCRIPTION_TOOL";
    UserPermission[UserPermission["VIEW_DOWNLOAD_RESULT"] = 162] = "VIEW_DOWNLOAD_RESULT";
    UserPermission[UserPermission["VIEW_EDIT_PROFILE"] = 163] = "VIEW_EDIT_PROFILE";
    UserPermission[UserPermission["ADMINISTRATOR"] = 164] = "ADMINISTRATOR";
    UserPermission[UserPermission["FULL_ADMIN"] = 170] = "FULL_ADMIN";
    UserPermission[UserPermission["VIEW_ADMIN"] = 171] = "VIEW_ADMIN";
    UserPermission[UserPermission["EDIT_ADMIN"] = 172] = "EDIT_ADMIN";
    UserPermission[UserPermission["DELETE_ADMIN"] = 173] = "DELETE_ADMIN";
    UserPermission[UserPermission["ADD_ADMIN"] = 174] = "ADD_ADMIN";
})(UserPermission || (UserPermission = {}));


/***/ }),

/***/ "./src/app/common-login/common-login.component.css":
/*!*********************************************************!*\
  !*** ./src/app/common-login/common-login.component.css ***!
  \*********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".login-page-wrapper {\r\n  background-image: url('login-bg-1.jpg');\r\n  background-repeat: no-repeat;\r\n  background-size: cover;\r\n  background-position: center;\r\n  position: absolute;\r\n  left: 0;\r\n  right: 0;\r\n  top: 0;\r\n  bottom: 0;\r\n}\r\n\r\n.login-logo {\r\n  margin-top: 30px;\r\n}\r\n\r\n.login-form-wrapper {\r\n  text-align: center;\r\n}\r\n\r\n.login-form-wrapper form {\r\n  padding: 30px 20px;\r\n  background-color: #fff;\r\n  border-radius: 10px;\r\n  display: inline-block;\r\n  opacity: 0.8;\r\n  margin-top: 20px;\r\n}\r\n\r\n.login-form-wrapper .input-icons {\r\n  display: inline-block;\r\n  margin-right: 5px;\r\n}\r\n\r\n.login-form-wrapper .form-control {\r\n  display: inline-block;\r\n  width: 250px;\r\n  border: 0;\r\n  border-bottom: 1px solid #ccc;\r\n  box-shadow: none;\r\n  border-radius: 0;\r\n  padding-left: 0;\r\n}\r\n\r\n.login-form-wrapper .forgot-password {\r\n  float: right;\r\n  text-decoration: none;\r\n  font-size: smaller;\r\n  opacity: 0.9;\r\n}\r\n\r\n.login-form-wrapper .login-btn {\r\n  color: #fff;\r\n  background-color: #0085f0;\r\n  display: block;\r\n  text-align: center;\r\n  border-radius: 10px;\r\n  padding: 4px;\r\n  margin-top: 10px;\r\n  outline: none !important;\r\n}\r\n\r\n.login-form-wrapper .login-btn:hover,\r\n.login-form-wrapper .login-btn:focus {\r\n  opacity: 0.4;\r\n  outline: none !important;\r\n}\r\n\r\n.login-logo span {\r\n  font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;\r\n  font-weight: bold;\r\n  letter-spacing: 1px;\r\n}\r\n\r\n.login-form-submit {\r\n  margin: none;\r\n  width: 100%;\r\n  border: 0;\r\n}\r\n\r\n@media (min-width: 700px) {\r\n}\r\n"

/***/ }),

/***/ "./src/app/common-login/common-login.component.html":
/*!**********************************************************!*\
  !*** ./src/app/common-login/common-login.component.html ***!
  \**********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"login-page-wrapper\">\n  <div class=\"container\">\n    <div class=\"login-logo\">\n      <img src=\"./assets/images/logo.png\" width=\"88\" alt=\"logo\">\n    </div>\n    <div class=\"row\">\n      <div class=\"col-md-6\">\n      </div>\n      <div class=\"col-md-6\">\n        <div class=\"login-form-wrapper\">\n          <form [formGroup]=\"loginForm\" (ngSubmit)=\"loginFormSubmit()\" novalidate>\n            <div class=\"form-group\">\n              <span class=\"input-icons\"><i class=\"fa fa-user\"></i></span>\n              <input type=\"text\" formControlName=\"username\" class=\"form-control\" placeholder=\"Username\">\n            </div>\n            <div class=\"form-group\">\n              <span class=\"input-icons\"><i class=\"fa fa-lock\"></i></span>\n              <input type=\"password\" formControlName=\"password\" class=\"form-control\" placeholder=\"Password\">\n            </div>\n            <div class=\"form-group\">\n            </div>\n            <div class=\"forgot-password-wrap\">\n              <div class=\"clearfix\"></div>\n            </div>\n            <div class=\"login-btn-wrap\">\n              <input type=\"submit\" value=\"Login\" class=\"login-btn login-form-submit\">\n              <div class=\"clearfix\"></div>\n            </div>\n          </form>\n        </div>\n      </div>\n    </div>\n  </div>\n</div>\n"

/***/ }),

/***/ "./src/app/common-login/common-login.component.ts":
/*!********************************************************!*\
  !*** ./src/app/common-login/common-login.component.ts ***!
  \********************************************************/
/*! exports provided: CommonLoginComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "CommonLoginComponent", function() { return CommonLoginComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _common_login_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./common-login.service */ "./src/app/common-login/common-login.service.ts");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _common_models_login_model__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../common-models/login-model */ "./src/app/common-models/login-model.ts");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var ngx_notification__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ngx-notification */ "./node_modules/ngx-notification/fesm5/ngx-notification.js");
/* harmony import */ var _ngx_progressbar_core__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @ngx-progressbar/core */ "./node_modules/@ngx-progressbar/core/fesm5/ngx-progressbar-core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};







var CommonLoginComponent = /** @class */ (function () {
    function CommonLoginComponent(loginService, router, progressBar, ngxNotificationService) {
        this.loginService = loginService;
        this.router = router;
        this.progressBar = progressBar;
        this.ngxNotificationService = ngxNotificationService;
    }
    CommonLoginComponent.prototype.ngOnInit = function () {
        this.createFormControl();
        this.createFormGroup();
    };
    CommonLoginComponent.prototype.sendNotification = function () {
        this.ngxNotificationService.sendMessage('This is my message to you!', 'danger', 'top-right');
    };
    CommonLoginComponent.prototype.createFormGroup = function () {
        this.loginForm = new _angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormGroup"]({
            username: this.username,
            password: this.password
        });
    };
    CommonLoginComponent.prototype.createFormControl = function () {
        this.username = new _angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormControl"]('', [
            _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required,
            _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].minLength(5)
        ]);
        this.password = new _angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormControl"]('', [
            _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required,
            _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].minLength(5)
        ]);
    };
    CommonLoginComponent.prototype.loginFormSubmit = function () {
        var _this = this;
        this.progressBar.start();
        if (this.loginForm.valid) {
            this.loginModel = new _common_models_login_model__WEBPACK_IMPORTED_MODULE_3__["LoginModel"]();
            this.loginModel.username = this.username.value;
            this.loginModel.password = this.password.value;
            this.loginService.loginUserName = this.username.value;
            this.loginService.loginPassword = this.password.value;
            this.loginService.userLogin(this.loginModel).subscribe(function (data) {
                if (data == null) {
                    return false;
                }
                if (+data.errorCode == 412) {
                    _this.ngxNotificationService.sendMessage(data.developerMessage + "!", 'danger', 'top-right');
                }
                if (+data.errorCode == 400) {
                    _this.ngxNotificationService.sendMessage('try again later!', 'danger', 'top-right');
                }
                if (data != null && data.memberId != undefined) {
                    console.log(data);
                    _this.loginService.setUserDataToCacheAndLocal(data);
                    _this.loginService.switchTheUrl(data);
                }
                _this.progressBar.complete();
                _this.errorHandler();
            }, function (error) {
                _this.ngxNotificationService.sendMessage('Server not responding!', 'danger', 'top-right');
                _this.progressBar.complete();
            }, function () {
                _this.progressBar.complete();
            });
            //this.loginForm.reset();
        }
        else {
            this.ngxNotificationService.sendMessage('Enter a valid credentials!', 'danger', 'top-right');
            this.progressBar.complete();
        }
    };
    CommonLoginComponent.prototype.errorHandler = function () {
        this.progressBar.complete();
    };
    CommonLoginComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-common-login',
            template: __webpack_require__(/*! ./common-login.component.html */ "./src/app/common-login/common-login.component.html"),
            styles: [__webpack_require__(/*! ./common-login.component.css */ "./src/app/common-login/common-login.component.css")]
        }),
        __metadata("design:paramtypes", [_common_login_service__WEBPACK_IMPORTED_MODULE_1__["CommonLoginService"],
            _angular_router__WEBPACK_IMPORTED_MODULE_4__["Router"],
            _ngx_progressbar_core__WEBPACK_IMPORTED_MODULE_6__["NgProgress"],
            ngx_notification__WEBPACK_IMPORTED_MODULE_5__["NgxNotificationService"]])
    ], CommonLoginComponent);
    return CommonLoginComponent;
}());



/***/ }),

/***/ "./src/app/common-login/common-login.service.ts":
/*!******************************************************!*\
  !*** ./src/app/common-login/common-login.service.ts ***!
  \******************************************************/
/*! exports provided: CommonLoginService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "CommonLoginService", function() { return CommonLoginService; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var rxjs__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! rxjs */ "./node_modules/rxjs/_esm5/index.js");
/* harmony import */ var rxjs_operators__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! rxjs/operators */ "./node_modules/rxjs/_esm5/operators/index.js");
/* harmony import */ var _common_enums_uesr_types__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ../common-enums/uesr-types */ "./src/app/common-enums/uesr-types.ts");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _common_enums_user_permissions__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ../common-enums/user-permissions */ "./src/app/common-enums/user-permissions.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};







var ROOT_URL_ADMIN = '/tap/api/admin';
var ROOT_URL_ORGANIZATION = '/tap/api/admin/organization';
var ROOT_URL_ORGANIZATION_MEMBER = '/tap/api/admin/organization/member';
var header = new _angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpHeaders"]({
    'token': localStorage.getItem('userToken'),
});
var CommonLoginService = /** @class */ (function () {
    function CommonLoginService(http, router) {
        this.http = http;
        this.router = router;
        this.isUserLoggedin = false;
        this.showTapNotification = false;
    }
    CommonLoginService.prototype.ngOnInit = function () { };
    CommonLoginService.prototype.userLogin = function (data) {
        var _this = this;
        return this.http.post(ROOT_URL_ADMIN + '/login', data).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_3__["catchError"])(function (error) {
            return _this.errorHandler(error);
        }));
    };
    // used to fetch user data after refresh window
    CommonLoginService.prototype.reloginByUserId = function (userId) {
        return this.http.get(ROOT_URL_ORGANIZATION_MEMBER + '/' + userId);
    };
    CommonLoginService.prototype.resetPassword = function (data) {
        return this.http.post(ROOT_URL_ADMIN + '/reset', data);
    };
    CommonLoginService.prototype.userLogout = function (data) {
        var _this = this;
        this.http.post(ROOT_URL_ADMIN + '/logout', data).subscribe(function () {
            _this.removeUserDataFromCacheAndLocal();
        });
    };
    CommonLoginService.prototype.commonLogout = function () {
        this.removeUserDataFromCacheAndLocal();
    };
    CommonLoginService.prototype.setUserDataToCacheAndLocal = function (user) {
        this.userData = user; // very important task
        this.memberId = user.memberId;
        this.isUserLoggedin = true;
        localStorage.setItem('memberId', user.memberId.toString());
    };
    CommonLoginService.prototype.removeUserDataFromCacheAndLocal = function () {
        this.userData = null;
        this.memberId = null;
        this.isUserLoggedin = false;
        localStorage.removeItem('memberId');
        this.router.navigate(['/login']);
    };
    CommonLoginService.prototype.switchTheUrl = function (user) {
        switch (user.memberType) {
            case _common_enums_uesr_types__WEBPACK_IMPORTED_MODULE_4__["UserTypes"].SuperAdministrator:
                this.router.navigate(['/superadmin']);
                break;
            case _common_enums_uesr_types__WEBPACK_IMPORTED_MODULE_4__["UserTypes"].SubAdminstrator:
            case _common_enums_uesr_types__WEBPACK_IMPORTED_MODULE_4__["UserTypes"].Administrator:
                if (user.showResetPassword) {
                    this.router.navigate(['/resetpassword']);
                }
                else {
                    this.router.navigate(['/subscription']);
                }
                break;
            default:
                break;
        }
    };
    CommonLoginService.prototype.checkUserPermission = function (permission) {
        if (this.userData) {
            return this.userData.userPermissions.some(function (vendor) { return vendor['securityPermissionId'] === permission; });
        }
        return false;
    };
    CommonLoginService.prototype.checkUserPermissionByUser = function (user, permission) {
        if (user) {
            return user.userPermissions.some(function (vendor) { return vendor['securityPermissionId'] === permission; });
        }
        return false;
    };
    CommonLoginService.prototype.errorHandler = function (error) {
        return rxjs__WEBPACK_IMPORTED_MODULE_2__["Observable"].throw(error.message);
    };
    CommonLoginService.prototype.createCustomerAccess = function () {
        if (this.checkUserPermission(_common_enums_user_permissions__WEBPACK_IMPORTED_MODULE_6__["UserPermission"].ADD_CUSTOMER)) {
            return true;
        }
        return false;
    };
    CommonLoginService.prototype.createFeedbackAccess = function () {
        if (this.userData.memberType != _common_enums_uesr_types__WEBPACK_IMPORTED_MODULE_4__["UserTypes"].Administrator) {
            if (this.checkUserPermission(_common_enums_user_permissions__WEBPACK_IMPORTED_MODULE_6__["UserPermission"].ADD_FEEDBACK)) {
                return true;
            }
            return false;
        }
        return false;
    };
    CommonLoginService.prototype.createPollAccess = function () {
        if (this.userData.memberType != _common_enums_uesr_types__WEBPACK_IMPORTED_MODULE_4__["UserTypes"].Administrator) {
            if (this.checkUserPermission(_common_enums_user_permissions__WEBPACK_IMPORTED_MODULE_6__["UserPermission"].ADD_POLL)) {
                return true;
            }
            return false;
        }
        return false;
    };
    CommonLoginService.prototype.createNoticeAccess = function () {
        if (this.userData.memberType != _common_enums_uesr_types__WEBPACK_IMPORTED_MODULE_4__["UserTypes"].Administrator) {
            if (this.checkUserPermission(_common_enums_user_permissions__WEBPACK_IMPORTED_MODULE_6__["UserPermission"].ADD_NOTICE)) {
                return true;
            }
            return false;
        }
        return false;
    };
    CommonLoginService.prototype.deleteCustomerAccess = function () {
        if (this.checkUserPermission(_common_enums_user_permissions__WEBPACK_IMPORTED_MODULE_6__["UserPermission"].ADD_CUSTOMER)) {
            return true;
        }
        return false;
    };
    CommonLoginService.prototype.deleteFeedbackAccess = function () {
        if (this.checkUserPermission(_common_enums_user_permissions__WEBPACK_IMPORTED_MODULE_6__["UserPermission"].DELETE_FEEDBACK)) {
            return true;
        }
        return false;
    };
    CommonLoginService.prototype.deleteNoticeAccess = function () {
        if (this.checkUserPermission(_common_enums_user_permissions__WEBPACK_IMPORTED_MODULE_6__["UserPermission"].DELETE_NOTICE)) {
            return true;
        }
        return false;
    };
    CommonLoginService.prototype.deletePollAccess = function () {
        if (this.checkUserPermission(_common_enums_user_permissions__WEBPACK_IMPORTED_MODULE_6__["UserPermission"].DELETE_POLL)) {
            return true;
        }
        return false;
    };
    CommonLoginService.prototype.addCustomerAccess = function () {
        if (this.checkUserPermission(_common_enums_user_permissions__WEBPACK_IMPORTED_MODULE_6__["UserPermission"].ADD_CUSTOMER) || _common_enums_uesr_types__WEBPACK_IMPORTED_MODULE_4__["UserTypes"].Administrator) {
            return true;
        }
        return false;
    };
    CommonLoginService.prototype.removeCustomerAccess = function () {
        if (this.checkUserPermission(_common_enums_user_permissions__WEBPACK_IMPORTED_MODULE_6__["UserPermission"].DELETE_CUSTOMER) || _common_enums_uesr_types__WEBPACK_IMPORTED_MODULE_4__["UserTypes"].Administrator) {
            return true;
        }
        return false;
    };
    CommonLoginService = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Injectable"])({
            providedIn: 'root'
        }),
        __metadata("design:paramtypes", [_angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpClient"],
            _angular_router__WEBPACK_IMPORTED_MODULE_5__["Router"]])
    ], CommonLoginService);
    return CommonLoginService;
}());



/***/ }),

/***/ "./src/app/common-models/login-model.ts":
/*!**********************************************!*\
  !*** ./src/app/common-models/login-model.ts ***!
  \**********************************************/
/*! exports provided: LoginModel */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "LoginModel", function() { return LoginModel; });
var LoginModel = /** @class */ (function () {
    function LoginModel() {
    }
    return LoginModel;
}());



/***/ }),

/***/ "./src/app/page-notfound/page-notfound.component.css":
/*!***********************************************************!*\
  !*** ./src/app/page-notfound/page-notfound.component.css ***!
  \***********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".page-not-found-container{\r\n    width: 100%;\r\n    height: 100Vh;\r\n    background: rgba(13, 147, 249, 1);\r\n    background: -webkit-gradient(left top, left bottom, color-stop(0%, rgba(13, 147, 249, 1)), color-stop(99%, rgba(66, 74, 221, 1)), color-stop(100%, rgba(69, 69, 220, 1)));\r\n    background: linear-gradient(to bottom, rgba(13, 147, 249, 1) 0%, rgba(66, 74, 221, 1) 99%, rgba(69, 69, 220, 1) 100%);\r\n    filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#0d93f9', endColorstr='#4545dc', GradientType=0);\r\n}\r\n\r\n.error-code{\r\n    margin: 0 20px 0 20px;\r\n    color: white;\r\n    font-weight: 700;\r\n    font-size: 20rem;\r\n}\r\n\r\n.error-message{\r\n    margin: 0 20px 0 20px;\r\n    color: white;\r\n    font-weight: 400;\r\n    font-size: 5rem;\r\n}"

/***/ }),

/***/ "./src/app/page-notfound/page-notfound.component.html":
/*!************************************************************!*\
  !*** ./src/app/page-notfound/page-notfound.component.html ***!
  \************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"page-not-found-container\">\n  <span class=\"error-code\">{{errorCode}}</span>\n  <span class=\"error-message\">{{errorMessage}}</span>\n</div>\n"

/***/ }),

/***/ "./src/app/page-notfound/page-notfound.component.ts":
/*!**********************************************************!*\
  !*** ./src/app/page-notfound/page-notfound.component.ts ***!
  \**********************************************************/
/*! exports provided: PageNotfoundComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "PageNotfoundComponent", function() { return PageNotfoundComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var PageNotfoundComponent = /** @class */ (function () {
    function PageNotfoundComponent() {
        this.errorCode = '404';
        this.errorMessage = 'page not found';
    }
    PageNotfoundComponent.prototype.ngOnInit = function () {
    };
    PageNotfoundComponent.prototype.setPNF = function (code, message) {
        this.errorCode = code;
        this.errorMessage = message;
    };
    PageNotfoundComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-page-notfound',
            template: __webpack_require__(/*! ./page-notfound.component.html */ "./src/app/page-notfound/page-notfound.component.html"),
            styles: [__webpack_require__(/*! ./page-notfound.component.css */ "./src/app/page-notfound/page-notfound.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], PageNotfoundComponent);
    return PageNotfoundComponent;
}());



/***/ }),

/***/ "./src/app/reset-password/reset-password.component.css":
/*!*************************************************************!*\
  !*** ./src/app/reset-password/reset-password.component.css ***!
  \*************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".reset-page-wrapper {\r\n    background-color: #f9fdff;\r\n    padding-top: 80px;\r\n    text-align: center;\r\n  }\r\n  \r\n  .reset-content h1 {\r\n    color: #1488f5;\r\n  }\r\n  \r\n  .reset-content form {\r\n    width: 290px;\r\n    margin: 0px auto;\r\n  }\r\n  \r\n  .login-form-wrapper form {\r\n    padding: 30px 20px;\r\n    background-color: #fff;\r\n    border-radius: 10px;\r\n    display: inline-block;\r\n    opacity: 0.8;\r\n    margin-top: 100px;\r\n  }\r\n  \r\n  .reset-content .input-icons {\r\n    display: inline-block;\r\n    margin-right: 5px;\r\n  }\r\n  \r\n  .reset-content .form-control {\r\n    display: inline-block;\r\n    width: 270px;\r\n    border: 0;\r\n    border-bottom: 1px solid #ccc;\r\n    box-shadow: none;\r\n    border-radius: 0;\r\n    padding-left: 0;\r\n    text-transform: capitalize;\r\n    background-color: transparent;\r\n  }\r\n  \r\n  .reset-content .reset-btn {\r\n    color: #fff;\r\n    background-color: #0085f0;\r\n    display: block;\r\n    text-align: center;\r\n    border-radius: 10px;\r\n    text-transform: capitalize;\r\n    padding: 7px 4px;\r\n    margin-top: 30px;\r\n    width: 100%;\r\n    border: 0;\r\n  }\r\n  \r\n  .reset-content .rest-btn:hover,\r\n  .reset-content .rest-btn:focus {\r\n    opacity: 0.8;\r\n  }\r\n  \r\n  .resetpassword-title-panel{\r\n    height: 150px;\r\n  }"

/***/ }),

/***/ "./src/app/reset-password/reset-password.component.html":
/*!**************************************************************!*\
  !*** ./src/app/reset-password/reset-password.component.html ***!
  \**************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"reset-page-wrapper\">\n  <div class=\"container-fluid\">\n    <div class=\"reset-logo\">\n      <img src=\"assets/images/logo.png\" width=\"140\" alt=\"logo\">\n    </div>\n    <div class=\"reset-content\">\n      <div class=\"resetpassword-title-panel\">\n        <h1>Welcome!!</h1>\n        <p>Before you are going to access the subscription portal,<br>Please reset your password</p>\n        <p>{{errorMessage}}</p>\n      </div>\n      <div>\n        <form [formGroup]=\"passwordConfirmForm\" (ngSubmit)=\"resetPasswordSubmit()\" novalidate>\n          <div class=\"form-group\">\n            <span class=\"input-icons\"><i class=\"fa fa-lock\"></i></span>\n            <input type=\"password\" formControlName=\"password\" class=\"form-control\" placeholder=\"Enter new password\">\n          </div>\n          <div class=\"form-group\">\n            <span class=\"input-icons\"><i class=\"fa fa-lock\"></i></span>\n            <input type=\"password\" formControlName=\"confirmPassword\" class=\"form-control\" placeholder=\"re-enter new password\">\n          </div>\n          <div class=\"login-btn-wrap\">\n            <!-- <a href=\"#\" type=\"submit\" class=\"reset-btn\">Reset</a> -->\n            <input type=\"submit\" value=\"Reset\" class=\"reset-btn\">\n            <div class=\"clearfix\"></div>\n          </div>\n        </form>\n      </div>\n    </div>\n  </div>\n</div>"

/***/ }),

/***/ "./src/app/reset-password/reset-password.component.ts":
/*!************************************************************!*\
  !*** ./src/app/reset-password/reset-password.component.ts ***!
  \************************************************************/
/*! exports provided: ResetPasswordComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ResetPasswordComponent", function() { return ResetPasswordComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _common_login_common_login_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../common-login/common-login.service */ "./src/app/common-login/common-login.service.ts");
/* harmony import */ var _ngx_progressbar_core__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @ngx-progressbar/core */ "./node_modules/@ngx-progressbar/core/fesm5/ngx-progressbar-core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var ResetPasswordComponent = /** @class */ (function () {
    function ResetPasswordComponent(router, commeonService, progressBar) {
        this.router = router;
        this.commeonService = commeonService;
        this.progressBar = progressBar;
    }
    ResetPasswordComponent.prototype.ngOnInit = function () {
        this.createFormControl();
        this.createFromGroup();
    };
    ResetPasswordComponent.prototype.createFormControl = function () {
        this.password = new _angular_forms__WEBPACK_IMPORTED_MODULE_1__["FormControl"]('', [
            _angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"].required,
            _angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"].minLength(5)
        ]);
        this.confirmPassword = new _angular_forms__WEBPACK_IMPORTED_MODULE_1__["FormControl"]();
    };
    ResetPasswordComponent.prototype.checkPasswords = function (group) {
        var pass = group.controls.password.value;
        var confirmPass = group.controls.confirmPass.value;
        return pass === confirmPass ? null : { notSame: true };
    };
    ResetPasswordComponent.prototype.createFromGroup = function () {
        this.passwordConfirmForm = new _angular_forms__WEBPACK_IMPORTED_MODULE_1__["FormGroup"]({
            password: this.password,
            confirmPassword: this.confirmPassword
        });
    };
    ResetPasswordComponent.prototype.resetPasswordSubmit = function () {
        var _this = this;
        setTimeout(function () {
            _this.errorMessage = '';
        }, 5000);
        if (!this.password.valid) {
            this.errorMessage = 'Enter a valid Password!';
            return false;
        }
        if (!this.confirmPassword.valid) {
            this.errorMessage = 'Enter a valid Password Again!';
            return false;
        }
        if (this.password.value != this.confirmPassword.value) {
            this.errorMessage = 'Password not Match!';
            return false;
        }
        this.progressBar.start();
        if (this.passwordConfirmForm.valid) {
            var resetModel = {
                username: this.commeonService.loginUserName,
                password: this.commeonService.loginPassword,
                newpassword: this.password.value
            };
            this.progressBar.set(80);
            this.commeonService.resetPassword(resetModel).subscribe(function (data) {
                if (data.errorCode) {
                    _this.errorMessage = data.developerMessage;
                }
                else {
                    _this.commeonService.loginPassword = null;
                    _this.router.navigate(['/subscription']);
                    _this.progressBar.complete();
                }
            });
        }
        this.progressBar.complete();
    };
    ResetPasswordComponent.prototype.passwordResetSubmit = function () {
        this.router.navigate(['/subscription']);
    };
    ResetPasswordComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-reset-password',
            template: __webpack_require__(/*! ./reset-password.component.html */ "./src/app/reset-password/reset-password.component.html"),
            styles: [__webpack_require__(/*! ./reset-password.component.css */ "./src/app/reset-password/reset-password.component.css")]
        }),
        __metadata("design:paramtypes", [_angular_router__WEBPACK_IMPORTED_MODULE_2__["Router"],
            _common_login_common_login_service__WEBPACK_IMPORTED_MODULE_3__["CommonLoginService"],
            _ngx_progressbar_core__WEBPACK_IMPORTED_MODULE_4__["NgProgress"]])
    ], ResetPasswordComponent);
    return ResetPasswordComponent;
}());



/***/ }),

/***/ "./src/environments/environment.ts":
/*!*****************************************!*\
  !*** ./src/environments/environment.ts ***!
  \*****************************************/
/*! exports provided: environment */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "environment", function() { return environment; });
// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.
var environment = {
    production: false
};
/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.


/***/ }),

/***/ "./src/main.ts":
/*!*********************!*\
  !*** ./src/main.ts ***!
  \*********************/
/*! no exports provided */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_platform_browser_dynamic__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/platform-browser-dynamic */ "./node_modules/@angular/platform-browser-dynamic/fesm5/platform-browser-dynamic.js");
/* harmony import */ var _app_app_module__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./app/app.module */ "./src/app/app.module.ts");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./environments/environment */ "./src/environments/environment.ts");




if (_environments_environment__WEBPACK_IMPORTED_MODULE_3__["environment"].production) {
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["enableProdMode"])();
}
Object(_angular_platform_browser_dynamic__WEBPACK_IMPORTED_MODULE_1__["platformBrowserDynamic"])().bootstrapModule(_app_app_module__WEBPACK_IMPORTED_MODULE_2__["AppModule"])
    .catch(function (err) { return console.log(err); });


/***/ }),

/***/ 0:
/*!***************************!*\
  !*** multi ./src/main.ts ***!
  \***************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(/*! C:\Workspace\cyspan-knowledgeT\tap\angular-webapp\src\main.ts */"./src/main.ts");


/***/ })

},[[0,"runtime","vendor"]]]);
//# sourceMappingURL=main.js.map