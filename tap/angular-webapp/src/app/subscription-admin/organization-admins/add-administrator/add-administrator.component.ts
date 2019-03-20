import { Component, OnInit, ViewChild } from '@angular/core';
import { ClosedServiceService } from '../closed-service.service';
import { SubscriptionService } from '../../subscription.service';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { UserPermissionsInterface } from 'src/app/common-models/user-permission-interface';
import { User } from 'src/app/common-models/user';
import { LoginModel } from 'src/app/common-models/login-model';
import { UserDetails } from 'src/app/common-models/user-details';
import { UserTypes } from 'src/app/common-enums/uesr-types';
import { NgProgress } from '@ngx-progressbar/core';
import { NgxNotificationService } from 'ngx-notification';
import { CommonLoginService } from 'src/app/common-login/common-login.service';
import { UserPermission } from 'src/app/common-enums/user-permissions';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AdminPermissionModal } from 'src/app/common-models/admin-permission-modal';

@Component({
  selector: 'app-add-administrator',
  templateUrl: './add-administrator.component.html',
  styleUrls: ['./add-administrator.component.css']
})
export class AddAdministratorComponent implements OnInit {

  adminAccess: AdminPermissionModal = new AdminPermissionModal();

  adminName: FormControl;
  adminPhone: FormControl;

  adminUsername: FormControl;
  adminPassword: FormControl;

  adminFormGroup: FormGroup;

  constructor(
    private commonService: CommonLoginService,
    private closedService: ClosedServiceService,
    private localService: SubscriptionService,
    private formBuilder: FormBuilder,
    private progressbar: NgProgress,
    private toster: NgxNotificationService,
    private modalService: NgbModal
  ) {
    this.setFormControls();
    this.setFormGroups();

    // set default access to admin
    this.adminAccess.customer.view = true;
    this.adminAccess.feedback.view = true;
    this.adminAccess.poll.view = true;
    this.adminAccess.notice.view = true;
    this.adminAccess.profile.view = true;
  }

  ngOnInit() {
    if (this.closedService.currentAdminModel == undefined) {
      this.closedService.currentAdminModel = new User();
    }

    // set member data for update
    if (this.closedService.currentAdminModel.memberId) {
      const myid = this.closedService.currentAdminModel.memberId
      this.adminName.setValue(this.closedService.currentAdminModel.userData.firstName);
      this.adminPhone.setValue(this.closedService.currentAdminModel.userData.phone);
      this.adminUsername.setValue(this.closedService.currentAdminModel.credentials.username);
      this.adminAccess = this.closedService.perpareAdminAccessModel(this.closedService.currentAdminModel.userPermissions);
      this.closedService.currentAdminModel = new User();
      this.closedService.currentAdminModel.memberId = myid;
    }

  }

  setFormControls() {
    this.adminName = new FormControl('', [
      Validators.required
    ]);
    this.adminPhone = new FormControl('', [
      Validators.required
    ]);
    this.adminUsername = new FormControl('', [
      Validators.required,
      Validators.pattern("[^ @]*@[^ @]*")
    ]);
    this.adminPassword = new FormControl('', [
      Validators.required,
      Validators.minLength(5)
    ]);
  }

  setFormGroups() {
    this.adminFormGroup = new FormGroup({
      adminName: this.adminName,
      adminPhone: this.adminPhone,
      adminUsername: this.adminUsername,
      adminPassword: this.adminPassword
    });
  }

  checkBoxEventListner(event, type: string) {
    this.adminAccess[type].view = event.target.checked;
    this.adminAccess[type].create = event.target.checked;
    this.adminAccess[type].delete = event.target.checked;
    this.adminAccess[type].update = event.target.checked;
    this.adminAccess[type].all = event.target.checked;
  }


  prepareUserData(data) {
    if (this.closedService.currentAdminModel.memberId) {
      this.modalService.open(data, { centered: true }).result.then(
        result => {
          if (result === 'update') {
            this.modalService.dismissAll();
          }
        },
        reason => {
          console.log('error reson' + reason);
        }
      );
    } else {
    }
  }

  // final setp of create new admin
  onComplete(event) {
    this.closedService.showAddAdmin = false;
    this.closedService.showEditAdmin = true;
    this.modalService.dismissAll();
    this.closedService.currentAdminModel.userPermissions = [];
    this.closedService.populateAdminPermissions(this.adminAccess);
    this.prepareAdminDataFromForm();
  }



  prepareAdminDataFromForm() {
    if (this.adminFormGroup.valid || this.closedService.currentAdminModel.memberId) {
      const userDetails = new UserDetails();
      const userCredentials = new LoginModel();
      if (this.adminName.dirty) {
        userDetails.firstName = this.adminName.value;
      }
      if (this.adminPhone.dirty) {
        userDetails.phone = this.adminPhone.value;
      }
      if (this.adminUsername.dirty) {
        userDetails.email = this.adminUsername.value;
        userCredentials.username = this.adminUsername.value;
      }
      if (this.adminPassword.dirty) {
        userCredentials.password = this.adminPassword.value;
      }
      this.closedService.currentAdminModel.userData = userDetails;
      this.closedService.currentAdminModel.credentials = userCredentials;
      this.closedService.currentAdminModel.memberType = UserTypes.SubAdminstrator;
      this.closedService.currentAdminModel.organization = this.localService.organizationModel;
      if (!this.closedService.currentAdminModel.memberId) {
        this.createAdmin();
      } else {
        this.updateAdmin();
      }
    }
  }

  createAdmin() {
    this.localService.createSubAdminForOrganization(this.closedService.currentAdminModel).subscribe((val) => {
     if(val.memberId){
      const index = this.closedService.subadminsList.findIndex(user => user.memberId === val.memberId);
      if (index === -1) {
        this.closedService.subadminsList.unshift(val);
      }
      this.toster.sendMessage(`${this.adminName.value} added as new Administrator!`, 'success', 'top-right');
     }else{
      this.toster.sendMessage(`error on create Administrator!`, 'error', 'top-right');
     }
    }, (error) => {
      console.log(error)
    });
  }

  updateAdmin() {
    this.localService.updateSubAdminForOrganization(this.closedService.currentAdminModel).subscribe((val) => {
      const index = this.closedService.subadminsList.findIndex(user => user.memberId === val.memberId);
      if (index === -1) {
        this.closedService.subadminsList.unshift(val);
      }else{
        this.closedService.subadminsList[index] = val;
      }
      this.toster.sendMessage(`${this.adminName.value} updated successfully!`, 'success', 'top-right');
    }, (error) => {
      console.log(error)
    });
  }
}
