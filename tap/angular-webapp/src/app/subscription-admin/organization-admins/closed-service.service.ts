import { Injectable, OnInit } from '@angular/core';
import { UserPermission } from 'src/app/common-enums/user-permissions';
import { CommonLoginService } from 'src/app/common-login/common-login.service';
import { User } from 'src/app/common-models/user';
import { UserPermissionsInterface } from 'src/app/common-models/user-permission-interface';
import { UserPermissions } from 'src/app/common-models/user-permissions';
import { SubscriptionService } from '../subscription.service';
import { AdminPermissionModal } from 'src/app/common-models/admin-permission-modal';

@Injectable({
  providedIn: 'root'
})
export class ClosedServiceService implements OnInit {

  subadminsList: Array<User> = [];
  currentAdminModel: User;
  showDeleteAdminButton = false;
  showAddAdmin = false;
  showEditAdmin = false;

  constructor(
    private commonService: CommonLoginService,
    private localService: SubscriptionService
  ) { }

  ngOnInit(): void {

  }

  checkAndPushIntoArray(data: UserPermission) {
    if (!this.currentAdminModel.userPermissions.some(vendor => vendor['securityPermissionId'] === data)) {
      this.currentAdminModel.userPermissions.push(new UserPermissions(data));
    }
  }

  checkUserPermission(permission: UserPermission): boolean {
    if (this.currentAdminModel.userPermissions) {
      return this.currentAdminModel.userPermissions.some(vendor => vendor['securityPermissionId'] === permission);
    }
    return false;
  }

  populateAdminPermissions(object: AdminPermissionModal) {

    //admin
    if (object.admin.all) {
      this.checkAndPushIntoArray(UserPermission.FULL_ADMIN);
    }
    if (object.admin.create) {
      this.checkAndPushIntoArray(UserPermission.ADD_ADMIN);
    }
    if (object.admin.delete) {
      this.checkAndPushIntoArray(UserPermission.DELETE_ADMIN);
    }
    if (object.admin.update) {
      this.checkAndPushIntoArray(UserPermission.EDIT_ADMIN);
    }
    if (object.admin.view) {
      this.checkAndPushIntoArray(UserPermission.VIEW_ADMIN);
    }

    //customer
    if (object.customer.all) {
      this.checkAndPushIntoArray(UserPermission.CUSTOMER_MANAGEMENT);
      this.checkAndPushIntoArray(UserPermission.FULL_CUSTOMER);
    }
    if (object.customer.create) {
      this.checkAndPushIntoArray(UserPermission.ADD_CUSTOMER);
    }
    if (object.customer.delete) {
      this.checkAndPushIntoArray(UserPermission.DELETE_CUSTOMER);
    }
    if (object.customer.update) {
      this.checkAndPushIntoArray(UserPermission.EDIT_CUSTOMER);
    }
    if (object.customer.view) {
      this.checkAndPushIntoArray(UserPermission.VIEW_CUSTOMER);
    }

    //feedback
    if (object.feedback.all) {
      this.checkAndPushIntoArray(UserPermission.FULL_FEEDBACK);
    }
    if (object.feedback.create) {
      this.checkAndPushIntoArray(UserPermission.ADD_FEEDBACK);
    }
    if (object.feedback.delete) {
      this.checkAndPushIntoArray(UserPermission.DELETE_FEEDBACK);
    }
    if (object.feedback.update) {
      this.checkAndPushIntoArray(UserPermission.EDIT_FEEDBACK);
    }
    if (object.feedback.view) {
      this.checkAndPushIntoArray(UserPermission.VIEW_FEEDBACK);
    }

    //poll
    if (object.poll.all) {
      this.checkAndPushIntoArray(UserPermission.FULL_POLL);
    }
    if (object.poll.create) {
      this.checkAndPushIntoArray(UserPermission.ADD_POLL);
    }
    if (object.poll.delete) {
      this.checkAndPushIntoArray(UserPermission.DELETE_POLL);
    }
    if (object.poll.update) {
      this.checkAndPushIntoArray(UserPermission.EDIT_POLL);
    }
    if (object.poll.view) {
      this.checkAndPushIntoArray(UserPermission.VIEW_POLL);
    }

    //notice
    if (object.notice.all) {
      this.checkAndPushIntoArray(UserPermission.FULL_NOTICE);
    }
    if (object.notice.create) {
      this.checkAndPushIntoArray(UserPermission.ADD_NOTICE);
    }
    if (object.notice.delete) {
      this.checkAndPushIntoArray(UserPermission.DELETE_NOTICE);
    }
    if (object.notice.update) {
      this.checkAndPushIntoArray(UserPermission.EDIT_NOTICE);
    }
    if (object.notice.view) {
      this.checkAndPushIntoArray(UserPermission.VIEW_NOTICE);
    }

    //profile
    if (object.profile.all) {
      this.checkAndPushIntoArray(UserPermission.FULL_PROFILE);
    }
    if (object.profile.create) {
      this.checkAndPushIntoArray(UserPermission.ADD_PROFILE);
    }
    if (object.profile.delete) {
      this.checkAndPushIntoArray(UserPermission.DELETE_PROFILE);
    }
    if (object.profile.update) {
      this.checkAndPushIntoArray(UserPermission.EDIT_PROFILE);
    }
    if (object.profile.view) {
      this.checkAndPushIntoArray(UserPermission.VIEW_PROFILE);
    }

    //download pdf
    if (object.downloadResult.view) {
      this.checkAndPushIntoArray(UserPermission.DOWNLOAD_RESULT);
      this.checkAndPushIntoArray(UserPermission.VIEW_DOWNLOAD_RESULT);
      this.checkAndPushIntoArray(UserPermission.VIEW_RESULT);
    }

    //view result
    if (object.viewResults.all) {
      this.checkAndPushIntoArray(UserPermission.VIEW_DOWNLOAD_RESULT);
      this.checkAndPushIntoArray(UserPermission.VIEW_RESULT);
    }
  }

  perpareAdminAccessModel(permission: UserPermissions[]): AdminPermissionModal {
    let temp = new AdminPermissionModal();
    //admin
    if (this.checkUserPermission(UserPermission.FULL_ADMIN)) {
      temp.admin.all = true;
    }
    if (this.checkUserPermission(UserPermission.ADD_ADMIN)) {
      temp.admin.create = true;
    }
    if (this.checkUserPermission(UserPermission.DELETE_ADMIN)) {
      temp.admin.delete = true;
    }
    if (this.checkUserPermission(UserPermission.EDIT_ADMIN)) {
      temp.admin.update = true;
    }
    if (this.checkUserPermission(UserPermission.VIEW_ADMIN)) {
      temp.admin.view = true;
    }

    //customer
    if (this.checkUserPermission(UserPermission.FULL_CUSTOMER)) {
      temp.customer.all = true;
    }
    if (this.checkUserPermission(UserPermission.ADD_CUSTOMER)) {
      temp.customer.create = true;
    }
    if (this.checkUserPermission(UserPermission.DELETE_CUSTOMER)) {
      temp.customer.delete = true;
    }
    if (this.checkUserPermission(UserPermission.EDIT_CUSTOMER)) {
      temp.customer.update = true;
    }
    if (this.checkUserPermission(UserPermission.VIEW_CUSTOMER)) {
      temp.customer.view = true;
    }

    //feedback
    if (this.checkUserPermission(UserPermission.FULL_FEEDBACK)) {
      temp.feedback.all = true;
    }
    if (this.checkUserPermission(UserPermission.ADD_FEEDBACK)) {
      temp.feedback.create = true;
    }
    if (this.checkUserPermission(UserPermission.DELETE_FEEDBACK)) {
      temp.feedback.delete = true;
    }
    if (this.checkUserPermission(UserPermission.EDIT_FEEDBACK)) {
      temp.feedback.update = true;
    }
    if (this.checkUserPermission(UserPermission.VIEW_FEEDBACK)) {
      temp.feedback.view = true;
    }

    //poll
    if (this.checkUserPermission(UserPermission.FULL_POLL)) {
      temp.poll.all = true;
    }
    if (this.checkUserPermission(UserPermission.ADD_POLL)) {
      temp.poll.create = true;
    }
    if (this.checkUserPermission(UserPermission.DELETE_POLL)) {
      temp.poll.delete = true;
    }
    if (this.checkUserPermission(UserPermission.EDIT_POLL)) {
      temp.poll.update = true;
    }
    if (this.checkUserPermission(UserPermission.VIEW_POLL)) {
      temp.poll.view = true;
    }

    //notice
    if (this.checkUserPermission(UserPermission.FULL_NOTICE)) {
      temp.notice.all = true;
    }
    if (this.checkUserPermission(UserPermission.ADD_NOTICE)) {
      temp.notice.create = true;
    }
    if (this.checkUserPermission(UserPermission.DELETE_NOTICE)) {
      temp.notice.delete = true;
    }
    if (this.checkUserPermission(UserPermission.EDIT_NOTICE)) {
      temp.notice.update = true;
    }
    if (this.checkUserPermission(UserPermission.VIEW_NOTICE)) {
      temp.notice.view = true;
    }

    //profile
    if (this.checkUserPermission(UserPermission.FULL_PROFILE)) {
      temp.profile.all = true;
    }
    if (this.checkUserPermission(UserPermission.ADD_PROFILE)) {
      temp.profile.create = true;
    }
    if (this.checkUserPermission(UserPermission.DELETE_PROFILE)) {
      temp.profile.delete = true;
    }
    if (this.checkUserPermission(UserPermission.EDIT_PROFILE)) {
      temp.profile.update = true;
    }
    if (this.checkUserPermission(UserPermission.VIEW_PROFILE)) {
      temp.profile.view = true;
    }

    // dowlnload & view
    if (this.checkUserPermission(UserPermission.DOWNLOAD_RESULT)) {
      temp.downloadResult.all = true;
    }


    if (this.checkUserPermission(UserPermission.VIEW_DOWNLOAD_RESULT)) {
      temp.viewResults.all = true;
    }


    return temp;
  }

}
