import { Component, OnInit } from '@angular/core';
import { CommonLoginService } from './common-login/common-login.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  constructor(private commonService: CommonLoginService) {

  }

  ngOnInit() {
  }

}
