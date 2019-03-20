import { Component, OnInit } from '@angular/core';
import { CommonLoginService } from '../../common-login/common-login.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {

  constructor(private commenService: CommonLoginService) { }

  ngOnInit() {
  }

  logout() {
    this.commenService.removeUserDataFromCacheAndLocal();
  }

}
