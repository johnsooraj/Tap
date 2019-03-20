import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonLoginService } from '../common-login/common-login.service';
import { NgProgress } from '@ngx-progressbar/core';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {

  password: FormControl;
  confirmPassword: FormControl;
  passwordConfirmForm: FormGroup;
  errorMessage: string;

  constructor(
    private router: Router,
    private commeonService: CommonLoginService,
    private progressBar: NgProgress
  ) { }

  ngOnInit() {
    this.createFormControl();
    this.createFromGroup();
  }

  createFormControl() {
    this.password = new FormControl('',
      [
        Validators.required,
        Validators.minLength(5)
      ]);
    this.confirmPassword = new FormControl();
  }

  checkPasswords(group: FormGroup) { // here we have the 'passwords' group
    let pass = group.controls.password.value;
    let confirmPass = group.controls.confirmPass.value;
    return pass === confirmPass ? null : { notSame: true }
  }

  createFromGroup() {
    this.passwordConfirmForm = new FormGroup({
      password: this.password,
      confirmPassword: this.confirmPassword
    });
  }

  resetPasswordSubmit() {
    setTimeout(() => {
      this.errorMessage = '';
    }, 5000);

    if (!this.password.valid) {
      this.errorMessage = 'Enter a valid Password!'
      return false;
    }
    if (!this.confirmPassword.valid) {
      this.errorMessage = 'Enter a valid Password Again!'
      return false;
    }
    if (this.password.value != this.confirmPassword.value) {
      this.errorMessage = 'Password not Match!';
      return false;
    }
    this.progressBar.start();
    if (this.passwordConfirmForm.valid) {
      let resetModel = {
        username: this.commeonService.loginUserName,
        password: this.commeonService.loginPassword,
        newpassword: this.password.value
      }
      this.progressBar.set(80);
      this.commeonService.resetPassword(resetModel).subscribe((data) => {
        if (data.errorCode) {
          this.errorMessage = data.developerMessage;
        } else {
          this.commeonService.loginPassword = null;
          this.router.navigate(['/subscription']);
          this.progressBar.complete();
        }
      });
    }
    this.progressBar.complete();
  }
  passwordResetSubmit() {
    this.router.navigate(['/subscription']);
  }

}
