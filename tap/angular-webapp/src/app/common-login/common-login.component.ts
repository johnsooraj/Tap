import { Component, OnInit } from '@angular/core';
import { CommonLoginService } from './common-login.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { LoginModel } from '../common-models/login-model';
import { Router } from '@angular/router';
import { NgxNotificationService } from 'ngx-notification';
import { NgProgress } from '@ngx-progressbar/core';

@Component({
  selector: 'app-common-login',
  templateUrl: './common-login.component.html',
  styleUrls: ['./common-login.component.css']
})
export class CommonLoginComponent implements OnInit {

  loginForm: FormGroup;
  loginModel: LoginModel;
  username: FormControl;
  password: FormControl;

  constructor(
    private loginService: CommonLoginService,
    private router: Router,
    private progressBar: NgProgress,
    private ngxNotificationService: NgxNotificationService
  ) { }

  ngOnInit() {
    this.createFormControl();
    this.createFormGroup();
  }

  sendNotification() {
    this.ngxNotificationService.sendMessage('This is my message to you!', 'danger', 'top-right');
  }

  createFormGroup() {
    this.loginForm = new FormGroup({
      username: this.username,
      password: this.password
    });
  }

  createFormControl() {
    this.username = new FormControl(
      '',
      [
        Validators.required,
        Validators.minLength(5)
      ]
    );
    this.password = new FormControl(
      '',
      [
        Validators.required,
        Validators.minLength(5)
      ]
    );
  }

  loginFormSubmit() {
    this.progressBar.start();
    if (this.loginForm.valid) {
      this.loginModel = new LoginModel();
      this.loginModel.username = this.username.value;
      this.loginModel.password = this.password.value;
      this.loginService.loginUserName = this.username.value;
      this.loginService.loginPassword = this.password.value;
      this.loginService.userLogin(this.loginModel).subscribe((data) => {
        if (data == null) {
          return false;
        }
        if (+data.errorCode == 412) {
          this.ngxNotificationService.sendMessage(`${data.developerMessage}!`, 'danger', 'top-right');
        }
        if (+data.errorCode == 400) {
          this.ngxNotificationService.sendMessage('try again later!', 'danger', 'top-right');
        }
        if (data != null && data.memberId != undefined) {
          console.log(data)
          this.loginService.setUserDataToCacheAndLocal(data);
          this.loginService.switchTheUrl(data);
        }
        this.progressBar.complete();
        this.errorHandler();
      },
        (error) => {
          this.ngxNotificationService.sendMessage('Server not responding!', 'danger', 'top-right');
          this.progressBar.complete();
        },
        () => {
          this.progressBar.complete();
        }
      );
      //this.loginForm.reset();
    } else {
      this.ngxNotificationService.sendMessage('Enter a valid credentials!', 'danger', 'top-right');
      this.progressBar.complete();
    }
  }

  errorHandler() {
    this.progressBar.complete();
  }

}
