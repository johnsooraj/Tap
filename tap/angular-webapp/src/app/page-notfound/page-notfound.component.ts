import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-page-notfound',
  templateUrl: './page-notfound.component.html',
  styleUrls: ['./page-notfound.component.css']
})
export class PageNotfoundComponent implements OnInit {

  errorCode: string = '404'
  errorMessage: string = 'page not found'
  constructor() { }

  ngOnInit() {
  }

  setPNF(code: string, message: string) {
    this.errorCode = code;
    this.errorMessage = message;
  }

}
