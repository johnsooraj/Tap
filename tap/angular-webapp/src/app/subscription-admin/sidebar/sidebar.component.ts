import { Component, OnInit } from '@angular/core';
import { CommonLoginService } from '../../common-login/common-login.service';
import { UserTypes } from 'src/app/common-enums/uesr-types';
import { SubscriptionService } from '../subscription.service';
import { UserDetails } from 'src/app/common-models/user-details';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  showAdminLink: boolean = true;
  organizationName: string = '';
  organizationPhoto: string = '';
  userDisplayName: string = '';
  userPhoto: string = '';

  constructor(private commonService: CommonLoginService, private localService: SubscriptionService) {
    // console.log('userData: ', this.commonService.userData);
    this.organizationName = this.commonService.userData.organization.organizationName;
    this.organizationPhoto = this.commonService.userData.organization.profilePhoto;
    if (this.commonService.userData.userData) {
      const userData: UserDetails = this.commonService.userData.userData;
      if (userData.firstName) {
        this.userDisplayName = userData.firstName;
      } else if (userData.email) {
        this.userDisplayName = userData.email;
      } else {
        this.userDisplayName = userData.phone;
      }
    } else {
      this.userDisplayName = this.commonService.userData.credentials.username;
    }
    if (this.commonService.userData.userData) {
      this.userPhoto = this.commonService.userData.userData.profilePhoto;
    }
    if (commonService.userData.memberType === UserTypes.SubAdminstrator) {
      this.showAdminLink = false;
    }
  }

  ngOnInit() {}

  logout() {
    this.commonService.removeUserDataFromCacheAndLocal();
  }
}
