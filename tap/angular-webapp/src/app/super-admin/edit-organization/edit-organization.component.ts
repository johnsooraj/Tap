import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { SuperAdminService } from '../super-admin.service';
import { Organization } from '../../common-models/organization';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { OrganizationComponent } from '../organization/organization.component';
import { UserDetails } from 'src/app/common-models/user-details';
import { User } from 'src/app/common-models/user';
import { UserTypes } from 'src/app/common-enums/uesr-types';
import { NgProgress } from '@ngx-progressbar/core';
import { NgxNotificationService } from 'ngx-notification';

@Component({
  selector: 'app-edit-organization',
  templateUrl: './edit-organization.component.html',
  styleUrls: ['./edit-organization.component.css']
})
export class EditOrganizationComponent implements OnInit {

  @ViewChild('addAdminModalTemplate') addAdminModalTemplate: ElementRef;
  defaultCoverURL: string = "./assets/images/cover-pic.png";
  defaultProfilePicURL: string = "./assets/images/profile-pic.png";
  oragnizationModel: Organization = new Organization();
  userEnums: UserTypes;
  constructor(
    private route: ActivatedRoute,
    private adminService: SuperAdminService,
    private location: Location,
    private modalService: NgbModal,
    private progressBar: NgProgress,
    private toster: NgxNotificationService
  ) {
    this.oragnizationModel.coverPhoto = this.defaultCoverURL;
    this.oragnizationModel.profilePhoto = this.defaultProfilePicURL;
    let id = parseInt(this.route.snapshot.paramMap.get('id'));
    this.loadOrganizationById(id);
  }

  ngOnInit() {
    console.log(UserTypes.Administrator)
  }

  loadOrganizationById(id) {
    this.adminService.getOrganizationById(id).subscribe((data) => {
      this.oragnizationModel = data;
      if (data.coverPhoto == null) {
        data.coverPhoto = this.defaultCoverURL;
      }
      if (data.profilePhoto == null) {
        data.profilePhoto = this.defaultProfilePicURL;
      }
    });
  }

  updateOrganizationModel() {
    this.progressBar.start();
    this.oragnizationModel.members.splice(0, 1);
    this.adminService.updateOrganization(this.oragnizationModel).subscribe((data) => {
      this.progressBar.set(.8);
      this.oragnizationModel = data;
      this.progressBar.complete();
    });
    this.progressBar.complete();
  }

  gotoLastPage() {
    this.location.back();
  }

  checkForAddress(): boolean {
    return this.oragnizationModel.organizationAddress ? true : false;
  }

  resetAdminPassword(id: number) {
    this.adminService.resetAdminPassword(id).subscribe((res) => {
      if (res) {
        this.toster.sendMessage(`Password Updated Successfully!`, 'success', 'top-right');
      }
    });
  }

  openResetpasswordModal(content, id) {
    this.modalService.open(content, {
      centered: true
    }).result.then((result) => {
      if (result == 'update') {
        this.resetAdminPassword(id);
      }
    }, (reason) => {
      console.log(reason)
    });
  }
}
