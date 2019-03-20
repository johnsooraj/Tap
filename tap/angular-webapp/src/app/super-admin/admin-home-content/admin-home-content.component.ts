import { Component, OnInit, AfterViewInit } from '@angular/core';
import { CommonLoginService } from '../../common-login/common-login.service';

@Component({
  selector: 'app-admin-home-content',
  templateUrl: './admin-home-content.component.html',
  styleUrls: ['./admin-home-content.component.css']
})
export class AdminHomeContentComponent implements OnInit {

  userName: string = '';

  constructor(private commonService: CommonLoginService) {
    if (commonService.userData.userData.firstName == null && commonService.userData.userData.lastName == null)
      this.userName = '-'
    else
      this.userName = commonService.userData.userData.firstName + commonService.userData.userData.lastName
  }

  ngOnInit() {
  }
}
