(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["super-admin-super-admin-module"],{

/***/ "./node_modules/angular-datatables/index.js":
/*!**************************************************!*\
  !*** ./node_modules/angular-datatables/index.js ***!
  \**************************************************/
/*! exports provided: DataTableDirective, DataTablesModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _src_angular_datatables_directive__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./src/angular-datatables.directive */ "./node_modules/angular-datatables/src/angular-datatables.directive.js");
/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "DataTableDirective", function() { return _src_angular_datatables_directive__WEBPACK_IMPORTED_MODULE_0__["DataTableDirective"]; });

/* harmony import */ var _src_angular_datatables_module__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./src/angular-datatables.module */ "./node_modules/angular-datatables/src/angular-datatables.module.js");
/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "DataTablesModule", function() { return _src_angular_datatables_module__WEBPACK_IMPORTED_MODULE_1__["DataTablesModule"]; });

/**
 * @license
 *
 * Use of this source code is governed by an MIT-style license that can be
 * found in the LICENSE file at https://raw.githubusercontent.com/l-lin/angular-datatables/master/LICENSE
 */


//# sourceMappingURL=index.js.map

/***/ }),

/***/ "./node_modules/angular-datatables/src/angular-datatables.directive.js":
/*!*****************************************************************************!*\
  !*** ./node_modules/angular-datatables/src/angular-datatables.directive.js ***!
  \*****************************************************************************/
/*! exports provided: DataTableDirective */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "DataTableDirective", function() { return DataTableDirective; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var rxjs__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! rxjs */ "./node_modules/rxjs/_esm5/index.js");
/**
 * @license
 *
 * Use of this source code is governed by an MIT-style license that can be
 * found in the LICENSE file at https://raw.githubusercontent.com/l-lin/angular-datatables/master/LICENSE
 */


var DataTableDirective = /** @class */ (function () {
    function DataTableDirective(el) {
        this.el = el;
        /**
           * The DataTable option you pass to configure your table.
           */
        this.dtOptions = {};
    }
    DataTableDirective.prototype.ngOnInit = function () {
        var _this = this;
        if (this.dtTrigger) {
            this.dtTrigger.subscribe(function () {
                _this.displayTable();
            });
        }
        else {
            this.displayTable();
        }
    };
    DataTableDirective.prototype.ngOnDestroy = function () {
        if (this.dtTrigger) {
            this.dtTrigger.unsubscribe();
        }
        if (this.dt) {
            this.dt.destroy(true);
        }
    };
    DataTableDirective.prototype.displayTable = function () {
        var _this = this;
        this.dtInstance = new Promise(function (resolve, reject) {
            Promise.resolve(_this.dtOptions).then(function (dtOptions) {
                // Using setTimeout as a "hack" to be "part" of NgZone
                setTimeout(function () {
                    _this.dt = $(_this.el.nativeElement).DataTable(dtOptions);
                    resolve(_this.dt);
                });
            });
        });
    };
    DataTableDirective.decorators = [
        { type: _angular_core__WEBPACK_IMPORTED_MODULE_0__["Directive"], args: [{
                    selector: '[datatable]'
                },] },
    ];
    /** @nocollapse */
    DataTableDirective.ctorParameters = function () { return [
        { type: _angular_core__WEBPACK_IMPORTED_MODULE_0__["ElementRef"], },
    ]; };
    DataTableDirective.propDecorators = {
        "dtOptions": [{ type: _angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"] },],
        "dtTrigger": [{ type: _angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"] },],
    };
    return DataTableDirective;
}());

//# sourceMappingURL=angular-datatables.directive.js.map

/***/ }),

/***/ "./node_modules/angular-datatables/src/angular-datatables.module.js":
/*!**************************************************************************!*\
  !*** ./node_modules/angular-datatables/src/angular-datatables.module.js ***!
  \**************************************************************************/
/*! exports provided: DataTablesModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "DataTablesModule", function() { return DataTablesModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common */ "./node_modules/@angular/common/fesm5/common.js");
/* harmony import */ var _angular_datatables_directive__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./angular-datatables.directive */ "./node_modules/angular-datatables/src/angular-datatables.directive.js");
/**
 * @license
 *
 * Use of this source code is governed by an MIT-style license that can be
 * found in the LICENSE file at https://raw.githubusercontent.com/l-lin/angular-datatables/master/LICENSE
 */



var DataTablesModule = /** @class */ (function () {
    function DataTablesModule() {
    }
    DataTablesModule.forRoot = function () {
        return {
            ngModule: DataTablesModule
        };
    };
    DataTablesModule.decorators = [
        { type: _angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"], args: [{
                    imports: [_angular_common__WEBPACK_IMPORTED_MODULE_1__["CommonModule"]],
                    declarations: [_angular_datatables_directive__WEBPACK_IMPORTED_MODULE_2__["DataTableDirective"]],
                    exports: [_angular_datatables_directive__WEBPACK_IMPORTED_MODULE_2__["DataTableDirective"]]
                },] },
    ];
    return DataTablesModule;
}());

//# sourceMappingURL=angular-datatables.module.js.map

/***/ }),

/***/ "./src/app/common-models/organization.ts":
/*!***********************************************!*\
  !*** ./src/app/common-models/organization.ts ***!
  \***********************************************/
/*! exports provided: Organization */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "Organization", function() { return Organization; });
var Organization = /** @class */ (function () {
    function Organization() {
    }
    return Organization;
}());



/***/ }),

/***/ "./src/app/super-admin/admin-home-content/admin-home-content.component.css":
/*!*********************************************************************************!*\
  !*** ./src/app/super-admin/admin-home-content/admin-home-content.component.css ***!
  \*********************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".super-admin-content{\r\n    display: block;\r\n    width: 100%;\r\n    padding-top: 20px;\r\n}\r\n.super-admin-content{\r\n    width: 100%;\r\n    height: 100vh;\r\n    background-color: #fff;\r\n}\r\n.loggedInUserName{\r\n    display: flex;\r\n    justify-content: flex-end;\r\n    margin-right: 20px;\r\n}\r\n.loggedInUserName > p{\r\n    margin-left: 5px;\r\n}"

/***/ }),

/***/ "./src/app/super-admin/admin-home-content/admin-home-content.component.html":
/*!**********************************************************************************!*\
  !*** ./src/app/super-admin/admin-home-content/admin-home-content.component.html ***!
  \**********************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"content\">\n  <div class=\"super-admin-content\">\n    <div class=\"logo loggedInUserName\">\n      <span><i class=\"fa fa-user\" aria-hidden=\"true\"></i>\n      </span>\n      <p>{{userName}}</p>\n    </div>\n    <div class=\"customer-cotrol\">\n      <router-outlet></router-outlet>\n    </div>\n  </div>\n</div>"

/***/ }),

/***/ "./src/app/super-admin/admin-home-content/admin-home-content.component.ts":
/*!********************************************************************************!*\
  !*** ./src/app/super-admin/admin-home-content/admin-home-content.component.ts ***!
  \********************************************************************************/
/*! exports provided: AdminHomeContentComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AdminHomeContentComponent", function() { return AdminHomeContentComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _common_login_common_login_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../common-login/common-login.service */ "./src/app/common-login/common-login.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var AdminHomeContentComponent = /** @class */ (function () {
    function AdminHomeContentComponent(commonService) {
        this.commonService = commonService;
        this.userName = '';
        if (commonService.userData.userData.firstName == null && commonService.userData.userData.lastName == null)
            this.userName = '-';
        else
            this.userName = commonService.userData.userData.firstName + commonService.userData.userData.lastName;
    }
    AdminHomeContentComponent.prototype.ngOnInit = function () {
    };
    AdminHomeContentComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-admin-home-content',
            template: __webpack_require__(/*! ./admin-home-content.component.html */ "./src/app/super-admin/admin-home-content/admin-home-content.component.html"),
            styles: [__webpack_require__(/*! ./admin-home-content.component.css */ "./src/app/super-admin/admin-home-content/admin-home-content.component.css")]
        }),
        __metadata("design:paramtypes", [_common_login_common_login_service__WEBPACK_IMPORTED_MODULE_1__["CommonLoginService"]])
    ], AdminHomeContentComponent);
    return AdminHomeContentComponent;
}());



/***/ }),

/***/ "./src/app/super-admin/admin-home/admin-home.component.css":
/*!*****************************************************************!*\
  !*** ./src/app/super-admin/admin-home/admin-home.component.css ***!
  \*****************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".main-page{\r\n    height: 100vh;\r\n}"

/***/ }),

/***/ "./src/app/super-admin/admin-home/admin-home.component.html":
/*!******************************************************************!*\
  !*** ./src/app/super-admin/admin-home/admin-home.component.html ***!
  \******************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"main-page add-new-admin-page\">\n    <app-sidebar></app-sidebar>\n    <app-admin-home-content></app-admin-home-content>\n</div>"

/***/ }),

/***/ "./src/app/super-admin/admin-home/admin-home.component.ts":
/*!****************************************************************!*\
  !*** ./src/app/super-admin/admin-home/admin-home.component.ts ***!
  \****************************************************************/
/*! exports provided: AdminHomeComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AdminHomeComponent", function() { return AdminHomeComponent; });
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

var AdminHomeComponent = /** @class */ (function () {
    function AdminHomeComponent() {
    }
    AdminHomeComponent.prototype.ngOnInit = function () {
    };
    AdminHomeComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-admin-home',
            template: __webpack_require__(/*! ./admin-home.component.html */ "./src/app/super-admin/admin-home/admin-home.component.html"),
            styles: [__webpack_require__(/*! ./admin-home.component.css */ "./src/app/super-admin/admin-home/admin-home.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], AdminHomeComponent);
    return AdminHomeComponent;
}());



/***/ }),

/***/ "./src/app/super-admin/edit-organization/edit-organization.component.css":
/*!*******************************************************************************!*\
  !*** ./src/app/super-admin/edit-organization/edit-organization.component.css ***!
  \*******************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".addAdminModalBody{\r\n    height: 400px;\r\n}\r\n\r\n.addAdminModalBody{\r\n    overflow-y: scroll;\r\n}\r\n\r\n.addAdminModalBody ul {\r\n    padding: 10px;\r\n}\r\n\r\n.addAdminModalBody li {\r\n    display: flex;\r\n}\r\n\r\n.modal-footer-addAdmin{\r\n    width: 100%;\r\n    justify-content: space-between;\r\n    display: flex;\r\n}\r\n\r\n.modal-footer-addAdmin a {\r\n    color: #2b48ec;\r\n    cursor: pointer;\r\n}\r\n\r\n.modal-button-wrapper {\r\n    width: 100%;\r\n    text-align: center;\r\n    display: flex;\r\n    justify-content: space-evenly;\r\n  }\r\n\r\n.confirmation-message {\r\n    height: 100px;\r\n    text-align: center;\r\n    padding: 20px;\r\n  }\r\n\r\n.confirmation-message span {\r\n    word-wrap: break-word;\r\n    font-size: 20px;\r\n  }"

/***/ }),

/***/ "./src/app/super-admin/edit-organization/edit-organization.component.html":
/*!********************************************************************************!*\
  !*** ./src/app/super-admin/edit-organization/edit-organization.component.html ***!
  \********************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"profile-container\">\n  <div class=\"profile-wraper\">\n    <div class=\"profile-cover\">\n      <img class=\"profile-cover-img\" src=\"{{oragnizationModel.coverPhoto}}\">\n    </div>\n    <div class=\"profile-picture\">\n      <img class=\"profile-profilepic-img\" src=\"{{oragnizationModel.profilePhoto}}\">\n    </div>\n    <div class=\"profile-body\">\n      <h3 class=\"profile-title\">{{oragnizationModel.organizationName}}</h3>\n      <div class=\"profile-edit-panel\">\n        <button (click)=\"openResetpasswordModal(updatecontent, oragnizationModel.organizationId)\" type=\"button\"\n          class=\"btn btn-secondary\">Reset\n          Organization Admin\n          Password</button>\n      </div>\n      <div class=\"profile-body-wrapper\">\n        <div class=\"row\">\n          <div class=\"col-md-6\">\n            <div class=\"profile-details\">\n              <dl class=\"row\">\n                <dt class=\"col-sm-3\">Website</dt>\n                <dd class=\"col-sm-9\" *ngIf=\"checkForAddress()\">{{oragnizationModel.organizationAddress.website}}</dd>\n\n                <dt class=\"col-sm-3\">Contact Number</dt>\n                <dd class=\"col-sm-9\" *ngIf=\"checkForAddress()\">{{oragnizationModel.organizationAddress.contact_number}}\n                </dd>\n\n                <dt class=\"col-sm-3\">Description</dt>\n                <dd class=\"col-sm-9\" *ngIf=\"checkForAddress()\">{{oragnizationModel.organizationAddress.description}}\n                </dd>\n\n                <dt class=\"col-sm-3\">Authority Name</dt>\n                <dd class=\"col-sm-9\" *ngIf=\"checkForAddress()\">{{oragnizationModel.organizationAddress.authorityName}}\n                </dd>\n\n                <dt class=\"col-sm-3\">Members</dt>\n                <dd class=\"col-sm-9\">\n                  <dl class=\"row\">\n                    <dt class=\"col-sm-4\">Total</dt>\n                    <dd class=\"col-sm-8\" *ngIf=\"checkForAddress()\">{{oragnizationModel.members.length}}.</dd>\n                  </dl>\n                  <dl class=\"row\">\n                    <dt class=\"col-sm-4\">Administrator</dt>\n                    <dd class=\"col-sm-8\" *ngIf=\"checkForAddress()\">1.</dd>\n                  </dl>\n                </dd>\n              </dl>\n            </div>\n          </div>\n          <div class=\"col-md-6\">\n            <div class=\"profile-details\">\n              <div class=\"row\">\n                <div class=\"col-12\">\n                  <ul class=\"list-group\">\n                    <li *ngFor=\"let user of oragnizationModel.members\" class=\"list-group-item\">\n                      <div *ngIf=\"user.userData != null; then content else other_content\">here is ignored</div>\n                      <ng-template #content>User ({{user.userData.email}})</ng-template>\n                      <ng-template #other_content>Administrator</ng-template>\n                    </li>\n                  </ul>\n                </div>\n              </div>\n            </div>\n          </div>\n        </div>\n      </div>\n    </div>\n  </div>\n</div>\n\n<ng-template #updatecontent let-modal>\n  <div class=\"modal-body\">\n    <div class=\"confirmation-message\">\n      <span>Password will be replaced.<br>Do You Wish to Proceed?</span>\n    </div>\n  </div>\n  <div class=\"modal-footer\">\n    <div class=\"modal-button-wrapper\">\n      <button type=\"button\" class=\"btn customer-category-btn\" (click)=\"modal.close('cancel')\">Cancel</button>\n      <button type=\"button\" class=\"btn customer-category-btn\" (click)=\"modal.close('update')\">Update</button>\n    </div>\n  </div>\n</ng-template>"

/***/ }),

/***/ "./src/app/super-admin/edit-organization/edit-organization.component.ts":
/*!******************************************************************************!*\
  !*** ./src/app/super-admin/edit-organization/edit-organization.component.ts ***!
  \******************************************************************************/
/*! exports provided: EditOrganizationComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "EditOrganizationComponent", function() { return EditOrganizationComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common */ "./node_modules/@angular/common/fesm5/common.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _super_admin_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../super-admin.service */ "./src/app/super-admin/super-admin.service.ts");
/* harmony import */ var _common_models_organization__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ../../common-models/organization */ "./src/app/common-models/organization.ts");
/* harmony import */ var _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @ng-bootstrap/ng-bootstrap */ "./node_modules/@ng-bootstrap/ng-bootstrap/fesm5/ng-bootstrap.js");
/* harmony import */ var src_app_common_enums_uesr_types__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! src/app/common-enums/uesr-types */ "./src/app/common-enums/uesr-types.ts");
/* harmony import */ var _ngx_progressbar_core__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @ngx-progressbar/core */ "./node_modules/@ngx-progressbar/core/fesm5/ngx-progressbar-core.js");
/* harmony import */ var ngx_notification__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! ngx-notification */ "./node_modules/ngx-notification/fesm5/ngx-notification.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};









var EditOrganizationComponent = /** @class */ (function () {
    function EditOrganizationComponent(route, adminService, location, modalService, progressBar, toster) {
        this.route = route;
        this.adminService = adminService;
        this.location = location;
        this.modalService = modalService;
        this.progressBar = progressBar;
        this.toster = toster;
        this.defaultCoverURL = "./assets/images/cover-pic.png";
        this.defaultProfilePicURL = "./assets/images/profile-pic.png";
        this.oragnizationModel = new _common_models_organization__WEBPACK_IMPORTED_MODULE_4__["Organization"]();
        this.oragnizationModel.coverPhoto = this.defaultCoverURL;
        this.oragnizationModel.profilePhoto = this.defaultProfilePicURL;
        var id = parseInt(this.route.snapshot.paramMap.get('id'));
        this.loadOrganizationById(id);
    }
    EditOrganizationComponent.prototype.ngOnInit = function () {
        console.log(src_app_common_enums_uesr_types__WEBPACK_IMPORTED_MODULE_6__["UserTypes"].Administrator);
    };
    EditOrganizationComponent.prototype.loadOrganizationById = function (id) {
        var _this = this;
        this.adminService.getOrganizationById(id).subscribe(function (data) {
            _this.oragnizationModel = data;
            if (data.coverPhoto == null) {
                data.coverPhoto = _this.defaultCoverURL;
            }
            if (data.profilePhoto == null) {
                data.profilePhoto = _this.defaultProfilePicURL;
            }
        });
    };
    EditOrganizationComponent.prototype.updateOrganizationModel = function () {
        var _this = this;
        this.progressBar.start();
        this.oragnizationModel.members.splice(0, 1);
        this.adminService.updateOrganization(this.oragnizationModel).subscribe(function (data) {
            _this.progressBar.set(.8);
            _this.oragnizationModel = data;
            _this.progressBar.complete();
        });
        this.progressBar.complete();
    };
    EditOrganizationComponent.prototype.gotoLastPage = function () {
        this.location.back();
    };
    EditOrganizationComponent.prototype.checkForAddress = function () {
        return this.oragnizationModel.organizationAddress ? true : false;
    };
    EditOrganizationComponent.prototype.resetAdminPassword = function (id) {
        var _this = this;
        this.adminService.resetAdminPassword(id).subscribe(function (res) {
            if (res) {
                _this.toster.sendMessage("Password Updated Successfully!", 'success', 'top-right');
            }
        });
    };
    EditOrganizationComponent.prototype.openResetpasswordModal = function (content, id) {
        var _this = this;
        this.modalService.open(content, {
            centered: true
        }).result.then(function (result) {
            if (result == 'update') {
                _this.resetAdminPassword(id);
            }
        }, function (reason) {
            console.log(reason);
        });
    };
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["ViewChild"])('addAdminModalTemplate'),
        __metadata("design:type", _angular_core__WEBPACK_IMPORTED_MODULE_0__["ElementRef"])
    ], EditOrganizationComponent.prototype, "addAdminModalTemplate", void 0);
    EditOrganizationComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-edit-organization',
            template: __webpack_require__(/*! ./edit-organization.component.html */ "./src/app/super-admin/edit-organization/edit-organization.component.html"),
            styles: [__webpack_require__(/*! ./edit-organization.component.css */ "./src/app/super-admin/edit-organization/edit-organization.component.css")]
        }),
        __metadata("design:paramtypes", [_angular_router__WEBPACK_IMPORTED_MODULE_2__["ActivatedRoute"],
            _super_admin_service__WEBPACK_IMPORTED_MODULE_3__["SuperAdminService"],
            _angular_common__WEBPACK_IMPORTED_MODULE_1__["Location"],
            _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_5__["NgbModal"],
            _ngx_progressbar_core__WEBPACK_IMPORTED_MODULE_7__["NgProgress"],
            ngx_notification__WEBPACK_IMPORTED_MODULE_8__["NgxNotificationService"]])
    ], EditOrganizationComponent);
    return EditOrganizationComponent;
}());



/***/ }),

/***/ "./src/app/super-admin/organization/organization.component.css":
/*!*********************************************************************!*\
  !*** ./src/app/super-admin/organization/organization.component.css ***!
  \*********************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".super-admin-home-container{\r\n    width: calc(100% - 30px);\r\n    height: 100%;\r\n    position: relative;\r\n    margin-top: 20px;\r\n    overflow-y: scroll;\r\n    overflow-x: hidden;\r\n    max-height: calc(100vh - 150px);\r\n}\r\n.tableBodyInnerDiv1{\r\n    overflow-y: scroll;\r\n    max-height: calc(100vh - 150px);\r\n}\r\n.viewAllOrganization a{\r\n    cursor: pointer;\r\n}\r\n.admin-modal-form-wrapper{\r\n    width: 80%;\r\n    padding: 20px 40px;\r\n    margin: 10px auto;\r\n    border: 2px solid #b0b0b0;\r\n    border-radius: 10px;\r\n}"

/***/ }),

/***/ "./src/app/super-admin/organization/organization.component.html":
/*!**********************************************************************!*\
  !*** ./src/app/super-admin/organization/organization.component.html ***!
  \**********************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div>\r\n    <div class=\"customer-cotrol\">\r\n        <a href=\"#\" (click)=\"openAddOrg(content)\" class=\"btn customer-category-btn\">Add New Organization</a>\r\n        <a href=\"#\" (click)=\"openDeleteConfirmation()\" class=\"btn customer-category-btn\">Remove\r\n            Organization</a>\r\n    </div>\r\n</div>\r\n<div class=\"super-admin-home-container\">\r\n    <div class=\"viewAllOrganization\">\r\n        <div class=\"tap-tbl tap-tbl-header\">\r\n            <table cellpadding=\"5\" cellspacing=\"5\" border=\"0\">\r\n                <thead>\r\n                    <tr>\r\n                        <th class=\"tbl-col-sm\">\r\n                            <label class=\"custom-control fill-checkbox\">\r\n                                <input type=\"checkbox\" (change)=\"checkEventListner($event, 'all')\"\r\n                                    class=\"fill-control-input\">\r\n                                <span class=\"fill-control-indicator\"></span>\r\n                            </label>\r\n                        </th>\r\n                        <th scope=\"col\">Organization Name</th>\r\n                        <th scope=\"col\">Administrator</th>\r\n                        <th scope=\"col\">Email</th>\r\n                        <th scope=\"col\">Create Date</th>\r\n                    </tr>\r\n                </thead>\r\n            </table>\r\n        </div>\r\n        <div class=\"tap-tbl tap-tbl-content\">\r\n            <table cellpadding=\"5\" cellspacing=\"5\" border=\"0\">\r\n                <tbody>\r\n                    <tr class=\"tap-table-row\" *ngFor=\"let data of organizationList; let i = index\">\r\n                        <td class=\"tbl-col-sm\">\r\n                            <label class=\"custom-control fill-checkbox\">\r\n                                <input [checked]=\"data.checkboxStatus\"\r\n                                    (change)=\"data.checkboxStatus = !data.checkboxStatus\" type=\"checkbox\"\r\n                                    class=\"fill-control-input\">\r\n                                <span class=\"fill-control-indicator\"></span>\r\n                            </label>\r\n                        </td>\r\n                        <td (click)=\"viewEditOrganization(data)\"><a>{{data.organizationName}}</a></td>\r\n                        <td>{{data.authorityName}}</td>\r\n                        <td>{{data.email}}</td>\r\n                        <td>{{data.createDateTime | date:'medium'}}</td>\r\n                    </tr>\r\n                </tbody>\r\n            </table>\r\n        </div>\r\n        <!-- <div class=\"table-wrapper\">\r\n            <div class=\"table-responsive\">\r\n                <table class=\"table table-bordered\">\r\n                    <thead class=\"tap-thead-dark\">\r\n                        <tr>\r\n                            <th>\r\n                                <div class=\"checkbox-wrap\">\r\n                                    <label class=\"custom-control fill-checkbox\">\r\n                                        <input type=\"checkbox\" [checked]=\"allCheckBoxEnabler\" (change)=\"checkAllCheckbox($event, organizationList)\"\r\n                                            class=\"fill-control-input\">\r\n                                        <span class=\"fill-control-indicator\"></span>\r\n                                    </label>\r\n                                </div>\r\n                            </th>\r\n                            <th scope=\"col\">#</th>\r\n                            <th scope=\"col\">Organization Name</th>\r\n                            <th scope=\"col\">Administrator</th>\r\n                            <th scope=\"col\">Email</th>\r\n                            <th scope=\"col\">Create Date</th>\r\n                        </tr>\r\n                    </thead>\r\n                    <tbody>\r\n                        <tr *ngFor=\"let data of organizationList; let i = index\">\r\n                            <td>\r\n                                <div class=\"checkbox-wrap\">\r\n                                    <label class=\"custom-control fill-checkbox\">\r\n                                        <input type=\"checkbox\" [checked]=\"enableAllCheckBox\" (change)=\"checkboxChangeEvent($event, data)\"\r\n                                            value=\"{{data.organizationId}}\" class=\"fill-control-input\">\r\n                                        <span class=\"fill-control-indicator\"></span>\r\n                                    </label>\r\n                                </div>\r\n                            </td>\r\n                            <td scope=\"row\">{{i}}</td>\r\n                            <td (click)=\"viewEditOrganization(data)\"><a>{{data.organizationName}}</a></td>\r\n                            <td>{{data.authorityName}}</td>\r\n                            <td>{{data.email}}</td>\r\n                            <td>{{data.createDateTime | date:'medium'}}</td>\r\n                        </tr>\r\n                    </tbody>\r\n                </table>\r\n            </div>\r\n        </div> -->\r\n    </div>\r\n</div>\r\n\r\n<ng-template #content let-modal>\r\n    <form [formGroup]=\"addOrganizationForm\" (ngSubmit)=\"addOrgFormSubmit()\" novalidate>\r\n        <div class=\"modal-header\">\r\n            <h5 class=\"modal-title\" id=\"modal-basic-title\">Add Oragnization</h5>\r\n            <button type=\"button\" class=\"close\" aria-label=\"Close\" (click)=\"modal.dismiss('Cross click')\">\r\n                <span aria-hidden=\"true\">&times;</span>\r\n            </button>\r\n        </div>\r\n        <div class=\"modal-body\">\r\n            <div class=\"admin-modal-form-wrapper\">\r\n                <div class=\"form-group has-danger\">\r\n                    <label for=\"formGroupExampleInput\">Organization Name</label>\r\n                    <input type=\"text\" class=\"form-control\" formControlName=\"organizationName\"\r\n                        id=\"formGroupExampleInput\" placeholder=\"organization name\" [ngClass]=\"{\r\n                            'is-invalid' : !organizationName.valid && organizationName.touched\r\n                        }\">\r\n                    <div class=\"invalid-feedback\">\r\n                        Please choose a Organization name.\r\n                    </div>\r\n                </div>\r\n                <div class=\"form-group\">\r\n                    <label for=\"formGroupExampleInput\">Authorised Person</label>\r\n                    <input type=\"text\" class=\"form-control\" formControlName=\"organizationAuthorityName\"\r\n                        id=\"formGroupExampleInput\" placeholder=\"administrator name\" [ngClass]=\"{\r\n                            'is-invalid' : !organizationAuthorityName.valid && organizationAuthorityName.touched\r\n                        }\">\r\n                    <div class=\"invalid-feedback\">\r\n                        Please choose a user name.\r\n                    </div>\r\n                </div>\r\n                <div class=\"form-group\">\r\n                    <label class=\"is-invalid\" for=\"formGroupExampleInput2\">Email</label>\r\n                    <input type=\"email\" class=\"form-control\" formControlName=\"organizationEmail\"\r\n                        id=\"formGroupExampleInput2\" placeholder=\"contact email\" [ngClass]=\"{\r\n                            'is-invalid' : !organizationEmail.valid && organizationEmail.touched\r\n                        }\">\r\n                    <div class=\"invalid-feedback\">\r\n                        Please choose a valid email.\r\n                    </div>\r\n                </div>\r\n                <div class=\"row\">\r\n                    <div class=\"col-md-6\">\r\n                        <div class=\"form-group\">\r\n                            <div class=\"custom-file\">\r\n                                <input type=\"file\" formControlName=\"organizationProfilePic\"\r\n                                    (change)=\"profilePictureEventListner($event)\" class=\"custom-file-input\"\r\n                                    id=\"customFile\">\r\n                                <label class=\"custom-file-label\" for=\"customFile\">Profile\r\n                                    Picture</label>\r\n                            </div>\r\n                        </div>\r\n                    </div>\r\n                    <div class=\"col-md-6\">\r\n                        <div class=\"form-group\">\r\n                            <div class=\"custom-file\">\r\n                                <input type=\"file\" formControlName=\"organizationProfilePic\"\r\n                                    (change)=\"profileCoverEventListner($event)\" class=\"custom-file-input\"\r\n                                    id=\"customFile\">\r\n                                <label class=\"custom-file-label\" for=\"customFile\">Cover\r\n                                    Picture</label>\r\n                            </div>\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n            </div>\r\n        </div>\r\n        <div class=\"modal-footer\">\r\n            <button type=\"submit\" class=\"btn btn-success\">Save</button>\r\n            <button type=\"button\" class=\"btn btn-default\" (click)=\"modal.close('Cancel click')\">Cancel</button>\r\n        </div>\r\n    </form>\r\n</ng-template>\r\n\r\n\r\n<ng-template #content2 let-modal>\r\n    <div class=\"modal-header\">\r\n        <h4 class=\"modal-title\" id=\"modal-title\">Oragnization deletion</h4>\r\n        <button type=\"button\" class=\"close\" aria-label=\"Close button\" aria-describedby=\"modal-title\"\r\n            (click)=\"modal.dismiss('Cross click')\">\r\n            <span aria-hidden=\"true\">&times;</span>\r\n        </button>\r\n    </div>\r\n    <div class=\"modal-body\">\r\n        <p><strong>Are you sure you want to delete <span class=\"text-primary\">Organization</span></strong></p>\r\n        <p>All information associated to this organization will be permanently deleted.\r\n            <span class=\"text-danger\">This operation can not be undone.</span>\r\n        </p>\r\n    </div>\r\n    <div class=\"modal-footer\">\r\n        <button type=\"button\" class=\"btn btn-outline-secondary\" (click)=\"modal.dismiss('cancel')\">Cancel</button>\r\n        <button type=\"button\" ngbAutofocus class=\"btn btn-danger\" (click)=\"modal.close('delete')\">Ok</button>\r\n    </div>\r\n</ng-template>"

/***/ }),

/***/ "./src/app/super-admin/organization/organization.component.ts":
/*!********************************************************************!*\
  !*** ./src/app/super-admin/organization/organization.component.ts ***!
  \********************************************************************/
/*! exports provided: OrganizationComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "OrganizationComponent", function() { return OrganizationComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @ng-bootstrap/ng-bootstrap */ "./node_modules/@ng-bootstrap/ng-bootstrap/fesm5/ng-bootstrap.js");
/* harmony import */ var _ngx_progressbar_core__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @ngx-progressbar/core */ "./node_modules/@ngx-progressbar/core/fesm5/ngx-progressbar-core.js");
/* harmony import */ var src_app_app_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! src/app/app.component */ "./src/app/app.component.ts");
/* harmony import */ var _common_models_organization__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ../../common-models/organization */ "./src/app/common-models/organization.ts");
/* harmony import */ var _super_admin_service__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ../super-admin.service */ "./src/app/super-admin/super-admin.service.ts");
/* harmony import */ var ngx_notification__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! ngx-notification */ "./node_modules/ngx-notification/fesm5/ngx-notification.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};









var OrganizationComponent = /** @class */ (function () {
    function OrganizationComponent(superAdminService, modalService, progressBar, router, appComponet, toster) {
        this.superAdminService = superAdminService;
        this.modalService = modalService;
        this.progressBar = progressBar;
        this.router = router;
        this.appComponet = appComponet;
        this.toster = toster;
        this.organizationList = Array();
    }
    // fetch all organizations
    OrganizationComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.superAdminService.getAllOrganization().subscribe(function (data) {
            if (data == null) {
                _this.toster.sendMessage("Organization fetch failed!", 'danger', 'top-right');
            }
            else if (data.length > 0) {
                _this.organizationList = data;
                _this.organizationList.forEach(function (org) { return org.checkboxStatus = false; });
            }
            else {
            }
        });
        this.setFormControls();
        this.setFormGroup();
    };
    OrganizationComponent.prototype.openAddOrg = function (content) {
        this.modalService.open(content, {
            ariaLabelledBy: 'modal-basic-title',
            size: 'lg',
            centered: true
        }).result.then(function (result) {
            console.log(result);
        }, function (reason) {
            console.log(reason);
        });
    };
    OrganizationComponent.prototype.setFormControls = function () {
        this.organizationName = new _angular_forms__WEBPACK_IMPORTED_MODULE_1__["FormControl"]('', [
            _angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"].required,
            _angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"].minLength(5)
        ]);
        this.organizationEmail = new _angular_forms__WEBPACK_IMPORTED_MODULE_1__["FormControl"]('', [
            _angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"].required,
        ]);
        this.organizationAuthorityName = new _angular_forms__WEBPACK_IMPORTED_MODULE_1__["FormControl"]('', [
            _angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"].required,
        ]);
        this.organizationAddress = new _angular_forms__WEBPACK_IMPORTED_MODULE_1__["FormControl"]();
        this.organizationProfilePic = new _angular_forms__WEBPACK_IMPORTED_MODULE_1__["FormControl"]();
        this.organizationCoverPic = new _angular_forms__WEBPACK_IMPORTED_MODULE_1__["FormControl"]();
    };
    OrganizationComponent.prototype.setFormGroup = function () {
        this.addOrganizationForm = new _angular_forms__WEBPACK_IMPORTED_MODULE_1__["FormGroup"]({
            organizationName: this.organizationName,
            organizationEmail: this.organizationEmail,
            organizationAddress: this.organizationAddress,
            organizationProfilePic: this.organizationProfilePic,
            organizationAuthorityName: this.organizationAuthorityName
        });
    };
    OrganizationComponent.prototype.profilePictureEventListner = function (evt) {
        var files = evt.target.files;
        var file = files[0];
        if (files && file) {
            var reader = new FileReader();
            reader.onload = this._handleReaderLoaded.bind(this);
            reader.readAsBinaryString(file);
        }
    };
    OrganizationComponent.prototype._handleReaderLoaded = function (readerEvt) {
        var binaryString = readerEvt.target.result;
        this.base64textStringProfile = btoa(binaryString);
        this.base64textStringCover = btoa(binaryString);
    };
    OrganizationComponent.prototype.profileCoverEventListner = function (evt) {
        var files = evt.target.files;
        var file = files[0];
        if (files && file) {
            var reader = new FileReader();
            reader.onload = this._handleReaderLoaded2.bind(this);
            reader.readAsBinaryString(file);
        }
    };
    OrganizationComponent.prototype._handleReaderLoaded2 = function (readerEvt) {
        var binaryString = readerEvt.target.result;
        this.base64textStringCover = btoa(binaryString);
    };
    OrganizationComponent.prototype.checkEventListner = function (evt, type) {
        if (type == 'all') {
            if (evt.target.checked) {
                this.checkAllRow();
            }
            else {
                this.uncheckAllRow();
            }
        }
    };
    OrganizationComponent.prototype.checkAllRow = function () {
        this.organizationList.forEach(function (org) { return (org.checkboxStatus = true); });
    };
    OrganizationComponent.prototype.uncheckAllRow = function () {
        this.organizationList.forEach(function (org) { return (org.checkboxStatus = false); });
    };
    OrganizationComponent.prototype.addOrgFormSubmit = function () {
        var _this = this;
        if (this.addOrganizationForm.valid) {
            if (this.organizationName.valid && this.organizationEmail.valid) {
                this.progressBar.start();
                var orgObj = new _common_models_organization__WEBPACK_IMPORTED_MODULE_6__["Organization"]();
                // Address not fixed
                orgObj.organizationName = this.organizationName.value;
                orgObj.email = this.organizationEmail.value;
                orgObj.profilePhotoByte = this.base64textStringProfile;
                orgObj.coverPhotoByte = this.base64textStringCover;
                orgObj.authorityName = this.organizationAuthorityName.value;
                this.superAdminService.saveOrganization(orgObj).subscribe(function (data) {
                    _this.organizationList.unshift(data);
                    _this.progressBar.complete();
                    _this.addOrganizationForm.reset();
                });
                this.progressBar.set(0.8);
                this.modalService.dismissAll();
            }
        }
    };
    OrganizationComponent.prototype.deleteSelectedOrg = function () {
        var _this = this;
        var idArray = [];
        this.organizationList.forEach(function (org) {
            if (org.checkboxStatus) {
                idArray.push(org.organizationId);
            }
        });
        this.superAdminService.deleteMultipleOrganization(idArray).subscribe(function (reslt) {
            if (reslt) {
                var temparray_1 = Array();
                _this.organizationList.forEach(function (org) {
                    if (!org.checkboxStatus) {
                        temparray_1.push(org);
                    }
                });
                _this.organizationList = [];
                temparray_1.forEach(function (obj) {
                    _this.organizationList.push(obj);
                });
                _this.toster.sendMessage("Organization delete Success!", 'success', 'top-right');
            }
            else {
                _this.toster.sendMessage("Organization delete failed!", 'danger', 'top-right');
            }
        });
    };
    OrganizationComponent.prototype.openDeleteConfirmation = function () {
        var _this = this;
        this.modalService.open(this.confirmationModal).result.then(function (result) {
            if (result === 'delete') {
                _this.deleteSelectedOrg();
            }
        }, function (reason) {
            console.log(reason);
        });
    };
    OrganizationComponent.prototype.deleteConfirmation = function () {
        return true;
    };
    OrganizationComponent.prototype.viewEditOrganization = function (data) {
        this.router.navigate(['/superadmin/organization', data.organizationId]);
    };
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["ViewChild"])('content2'),
        __metadata("design:type", _angular_core__WEBPACK_IMPORTED_MODULE_0__["ElementRef"])
    ], OrganizationComponent.prototype, "confirmationModal", void 0);
    OrganizationComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-organization',
            template: __webpack_require__(/*! ./organization.component.html */ "./src/app/super-admin/organization/organization.component.html"),
            styles: [__webpack_require__(/*! ./organization.component.css */ "./src/app/super-admin/organization/organization.component.css")]
        }),
        __metadata("design:paramtypes", [_super_admin_service__WEBPACK_IMPORTED_MODULE_7__["SuperAdminService"],
            _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_3__["NgbModal"],
            _ngx_progressbar_core__WEBPACK_IMPORTED_MODULE_4__["NgProgress"],
            _angular_router__WEBPACK_IMPORTED_MODULE_2__["Router"],
            src_app_app_component__WEBPACK_IMPORTED_MODULE_5__["AppComponent"],
            ngx_notification__WEBPACK_IMPORTED_MODULE_8__["NgxNotificationService"]])
    ], OrganizationComponent);
    return OrganizationComponent;
}());



/***/ }),

/***/ "./src/app/super-admin/pagenot-found/pagenot-found.component.css":
/*!***********************************************************************!*\
  !*** ./src/app/super-admin/pagenot-found/pagenot-found.component.css ***!
  \***********************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/super-admin/pagenot-found/pagenot-found.component.html":
/*!************************************************************************!*\
  !*** ./src/app/super-admin/pagenot-found/pagenot-found.component.html ***!
  \************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<p>\n  pagenot-found works!\n</p>\n"

/***/ }),

/***/ "./src/app/super-admin/pagenot-found/pagenot-found.component.ts":
/*!**********************************************************************!*\
  !*** ./src/app/super-admin/pagenot-found/pagenot-found.component.ts ***!
  \**********************************************************************/
/*! exports provided: PagenotFoundComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "PagenotFoundComponent", function() { return PagenotFoundComponent; });
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

var PagenotFoundComponent = /** @class */ (function () {
    function PagenotFoundComponent() {
    }
    PagenotFoundComponent.prototype.ngOnInit = function () {
    };
    PagenotFoundComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-pagenot-found',
            template: __webpack_require__(/*! ./pagenot-found.component.html */ "./src/app/super-admin/pagenot-found/pagenot-found.component.html"),
            styles: [__webpack_require__(/*! ./pagenot-found.component.css */ "./src/app/super-admin/pagenot-found/pagenot-found.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], PagenotFoundComponent);
    return PagenotFoundComponent;
}());



/***/ }),

/***/ "./src/app/super-admin/sidebar/sidebar.component.css":
/*!***********************************************************!*\
  !*** ./src/app/super-admin/sidebar/sidebar.component.css ***!
  \***********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/super-admin/sidebar/sidebar.component.html":
/*!************************************************************!*\
  !*** ./src/app/super-admin/sidebar/sidebar.component.html ***!
  \************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"side-nav desktop-side-nav\">\n  <ul>\n    <li><a routerLink=\"./organization\" routerLinkActive=\"active\">Organization management</a></li>\n    <li><a routerLink=\"#\" (click)=\"logout()\" routerLinkActive=\"active\">logout </a></li>\n  </ul>\n</div>"

/***/ }),

/***/ "./src/app/super-admin/sidebar/sidebar.component.ts":
/*!**********************************************************!*\
  !*** ./src/app/super-admin/sidebar/sidebar.component.ts ***!
  \**********************************************************/
/*! exports provided: SidebarComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SidebarComponent", function() { return SidebarComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _common_login_common_login_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../common-login/common-login.service */ "./src/app/common-login/common-login.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var SidebarComponent = /** @class */ (function () {
    function SidebarComponent(commenService) {
        this.commenService = commenService;
    }
    SidebarComponent.prototype.ngOnInit = function () {
    };
    SidebarComponent.prototype.logout = function () {
        this.commenService.removeUserDataFromCacheAndLocal();
    };
    SidebarComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-sidebar',
            template: __webpack_require__(/*! ./sidebar.component.html */ "./src/app/super-admin/sidebar/sidebar.component.html"),
            styles: [__webpack_require__(/*! ./sidebar.component.css */ "./src/app/super-admin/sidebar/sidebar.component.css")]
        }),
        __metadata("design:paramtypes", [_common_login_common_login_service__WEBPACK_IMPORTED_MODULE_1__["CommonLoginService"]])
    ], SidebarComponent);
    return SidebarComponent;
}());



/***/ }),

/***/ "./src/app/super-admin/super-admin-routing.module.ts":
/*!***********************************************************!*\
  !*** ./src/app/super-admin/super-admin-routing.module.ts ***!
  \***********************************************************/
/*! exports provided: SuperAdminRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SuperAdminRoutingModule", function() { return SuperAdminRoutingModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _admin_home_admin_home_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./admin-home/admin-home.component */ "./src/app/super-admin/admin-home/admin-home.component.ts");
/* harmony import */ var _pagenot_found_pagenot_found_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./pagenot-found/pagenot-found.component */ "./src/app/super-admin/pagenot-found/pagenot-found.component.ts");
/* harmony import */ var _organization_organization_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./organization/organization.component */ "./src/app/super-admin/organization/organization.component.ts");
/* harmony import */ var _edit_organization_edit_organization_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./edit-organization/edit-organization.component */ "./src/app/super-admin/edit-organization/edit-organization.component.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};






var routes = [
    {
        path: '',
        component: _admin_home_admin_home_component__WEBPACK_IMPORTED_MODULE_2__["AdminHomeComponent"],
        children: [
            {
                path: '',
                redirectTo: 'organization',
                pathMatch: 'full'
            },
            {
                path: 'organization',
                component: _organization_organization_component__WEBPACK_IMPORTED_MODULE_4__["OrganizationComponent"]
            },
            {
                path: 'organization/:id',
                component: _edit_organization_edit_organization_component__WEBPACK_IMPORTED_MODULE_5__["EditOrganizationComponent"]
            },
            {
                path: '**',
                component: _pagenot_found_pagenot_found_component__WEBPACK_IMPORTED_MODULE_3__["PagenotFoundComponent"]
            }
        ]
    }
];
var SuperAdminRoutingModule = /** @class */ (function () {
    function SuperAdminRoutingModule() {
    }
    SuperAdminRoutingModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"].forChild(routes)],
            exports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"]]
        })
    ], SuperAdminRoutingModule);
    return SuperAdminRoutingModule;
}());



/***/ }),

/***/ "./src/app/super-admin/super-admin.module.ts":
/*!***************************************************!*\
  !*** ./src/app/super-admin/super-admin.module.ts ***!
  \***************************************************/
/*! exports provided: SuperAdminModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SuperAdminModule", function() { return SuperAdminModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common */ "./node_modules/@angular/common/fesm5/common.js");
/* harmony import */ var angular_datatables__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! angular-datatables */ "./node_modules/angular-datatables/index.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _super_admin_routing_module__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./super-admin-routing.module */ "./src/app/super-admin/super-admin-routing.module.ts");
/* harmony import */ var _admin_home_admin_home_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./admin-home/admin-home.component */ "./src/app/super-admin/admin-home/admin-home.component.ts");
/* harmony import */ var _sidebar_sidebar_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./sidebar/sidebar.component */ "./src/app/super-admin/sidebar/sidebar.component.ts");
/* harmony import */ var _pagenot_found_pagenot_found_component__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ./pagenot-found/pagenot-found.component */ "./src/app/super-admin/pagenot-found/pagenot-found.component.ts");
/* harmony import */ var _organization_organization_component__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! ./organization/organization.component */ "./src/app/super-admin/organization/organization.component.ts");
/* harmony import */ var _admin_home_content_admin_home_content_component__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! ./admin-home-content/admin-home-content.component */ "./src/app/super-admin/admin-home-content/admin-home-content.component.ts");
/* harmony import */ var _super_admin_service__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! ./super-admin.service */ "./src/app/super-admin/super-admin.service.ts");
/* harmony import */ var _edit_organization_edit_organization_component__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! ./edit-organization/edit-organization.component */ "./src/app/super-admin/edit-organization/edit-organization.component.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};












var SuperAdminModule = /** @class */ (function () {
    function SuperAdminModule() {
    }
    SuperAdminModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [
                _angular_common__WEBPACK_IMPORTED_MODULE_1__["CommonModule"],
                _super_admin_routing_module__WEBPACK_IMPORTED_MODULE_4__["SuperAdminRoutingModule"],
                angular_datatables__WEBPACK_IMPORTED_MODULE_2__["DataTablesModule"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_3__["FormsModule"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_3__["ReactiveFormsModule"]
            ],
            declarations: [
                _admin_home_admin_home_component__WEBPACK_IMPORTED_MODULE_5__["AdminHomeComponent"],
                _sidebar_sidebar_component__WEBPACK_IMPORTED_MODULE_6__["SidebarComponent"],
                _pagenot_found_pagenot_found_component__WEBPACK_IMPORTED_MODULE_7__["PagenotFoundComponent"],
                _organization_organization_component__WEBPACK_IMPORTED_MODULE_8__["OrganizationComponent"],
                _admin_home_content_admin_home_content_component__WEBPACK_IMPORTED_MODULE_9__["AdminHomeContentComponent"],
                _edit_organization_edit_organization_component__WEBPACK_IMPORTED_MODULE_11__["EditOrganizationComponent"]
            ],
            providers: [
                _super_admin_service__WEBPACK_IMPORTED_MODULE_10__["SuperAdminService"]
            ]
        })
    ], SuperAdminModule);
    return SuperAdminModule;
}());



/***/ }),

/***/ "./src/app/super-admin/super-admin.service.ts":
/*!****************************************************!*\
  !*** ./src/app/super-admin/super-admin.service.ts ***!
  \****************************************************/
/*! exports provided: SuperAdminService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SuperAdminService", function() { return SuperAdminService; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var ROOT_URL_ORGANIZATION = '/tap/api/admin/organization';
var FETCH_ALL_USERS = '/tap/api/admin/users';
var FETCH_ALL_USERS_BY_NAME = '/tap/api/admin/users/name/';
var FETCH_ALL_USERS_BY_PHONE = '/tap/api/admin/users/phone/';
var SuperAdminService = /** @class */ (function () {
    function SuperAdminService(http) {
        this.http = http;
    }
    SuperAdminService.prototype.getAllOrganization = function () {
        return this.http.get(ROOT_URL_ORGANIZATION);
    };
    SuperAdminService.prototype.getOrganizationById = function (id) {
        return this.http.get(ROOT_URL_ORGANIZATION + '/' + id);
    };
    SuperAdminService.prototype.saveOrganization = function (org) {
        return this.http.post(ROOT_URL_ORGANIZATION, org);
    };
    SuperAdminService.prototype.updateOrganization = function (org) {
        return this.http.put(ROOT_URL_ORGANIZATION, org);
    };
    SuperAdminService.prototype.resetAdminPassword = function (id) {
        return this.http.put("/tap/api/admin/reset-organization//" + id, null);
    };
    SuperAdminService.prototype.deleteSingleOrganization = function (id) {
        return this.http.delete(ROOT_URL_ORGANIZATION + '/' + id);
    };
    SuperAdminService.prototype.deleteMultipleOrganization = function (ids) {
        return this.http.post(ROOT_URL_ORGANIZATION + '/delete', ids);
    };
    SuperAdminService.prototype.getAllCustomer = function () {
        return this.http.get(FETCH_ALL_USERS);
    };
    SuperAdminService.prototype.getAllCustomerByFirstName = function (name) {
        return this.http.get(FETCH_ALL_USERS_BY_NAME + name);
    };
    SuperAdminService.prototype.getAllCustomerByPhoneNumber = function (phone) {
        return this.http.get(FETCH_ALL_USERS_BY_PHONE + phone);
    };
    SuperAdminService = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Injectable"])({
            providedIn: 'root'
        }),
        __metadata("design:paramtypes", [_angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpClient"]])
    ], SuperAdminService);
    return SuperAdminService;
}());



/***/ })

}]);
//# sourceMappingURL=super-admin-super-admin-module.js.map