import { Component, OnInit } from '@angular/core';
import { CommonLoginService } from '../../common-login/common-login.service';
import { SubscriptionService } from '../subscription.service';
import { UserTypes } from 'src/app/common-enums/uesr-types';

@Component({
  selector: 'app-admin-home-content',
  templateUrl: './admin-home-content.component.html',
  styleUrls: ['./admin-home-content.component.css']
})
export class AdminHomeContentComponent implements OnInit {

  userName: string = '';

  constructor(
    private commonService: CommonLoginService,
    private localService: SubscriptionService
  ) {
    this.userName = `${this.commonService.userData.organization.organizationName}`;
  }

  ngOnInit() {

  }
}
