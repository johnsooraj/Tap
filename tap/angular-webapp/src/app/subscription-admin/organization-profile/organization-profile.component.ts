import { Component, OnInit, ViewChild, ElementRef, HostListener } from '@angular/core';
import { CommonLoginService } from 'src/app/common-login/common-login.service';
import { Organization } from 'src/app/common-models/organization';
import { SubscriptionService } from '../subscription.service';
import { Address } from 'src/app/common-models/address';
import { NgProgress } from '@ngx-progressbar/core';

@Component({
  selector: 'app-organization-profile',
  templateUrl: './organization-profile.component.html',
  styleUrls: ['./organization-profile.component.css']
})
export class OrganizationProfileComponent implements OnInit {

  @ViewChild('profileCoverFileInput') profileCoverPicture: ElementRef;
  @ViewChild('profileProfileFileInput') profileProfilePicture: ElementRef;

  coverPicUrl: string = '../../../assets/images/cover-pic.png';
  profilePicUrl: string = '';
  orgAddress: Address;

  myOrg: Organization;
  base64textStringProfile: string;
  base64textStringCover: string;

  constructor(
    private commonService: CommonLoginService,
    private localService: SubscriptionService,
    private progressbar: NgProgress
  ) {
    this.myOrg = this.commonService.userData.organization;
    if (this.myOrg.coverPhoto) {
      this.coverPicUrl = this.myOrg.coverPhoto;
    }
    if (this.myOrg.profilePhoto) {
      this.profilePicUrl = this.myOrg.profilePhoto;
    }
  }


  ngOnInit() {

  }

  openFileChooser(type) {
    if (type === 'cover') {
      this.profileCoverPicture.nativeElement.click();
    }
    if (type === 'profile') {
      this.profileProfilePicture.nativeElement.click();
    }
  }

  profileCoverPictureChange(event) {
    let img: any;
    let fileList: FileList = event.target.files;
    if (fileList.length > 0) {
      let file: File = fileList[0];
      img = document.querySelector("#coverpicture");
      var reader = new FileReader();
      reader.readAsDataURL(file);
      setTimeout(() => {
        img.src = reader.result;
        reader.onload = this.coverOnLoad.bind(this);
        reader.readAsBinaryString(file);
      }, 500);
    }
  }

  _handleReaderLoaded(readerEvt) {
    var binaryString = readerEvt.target.result;
    this.base64textStringProfile = btoa(binaryString);
    this.base64textStringCover = btoa(binaryString);
  }

  profileProfilePictureChange(event) {
    let img: any;
    let fileList: FileList = event.target.files;
    if (fileList.length > 0) {
      let file: File = fileList[0];
      img = document.querySelector("#profilepicture");
      var reader = new FileReader();
      reader.readAsDataURL(file);
      setTimeout(() => {
        img.src = reader.result;
        reader.onload = this.profileOnLoad.bind(this);
        reader.readAsBinaryString(file);
      }, 500);
    }
  }

  coverOnLoad(event) {
    var binaryString = event.target.result;
    this.base64textStringCover = btoa(binaryString);
  }

  profileOnLoad(event) {
    var binaryString = event.target.result;
    this.base64textStringProfile = btoa(binaryString);
  }

  onsubmitButtonClick() {
    this.progressbar.start();
    if (this.base64textStringCover) {
      this.myOrg.coverPhotoByte = this.base64textStringCover;
    }
    if (this.base64textStringProfile) {
      this.myOrg.profilePhotoByte = this.base64textStringProfile;
    }
    this.progressbar.set(.6);
    this.localService.updateOrganization(this.myOrg).subscribe((result) => {
      if (result.organizationId) {
        this.myOrg = result;
        this.progressbar.complete();
      } else {
        console.log('error on update')
        this.progressbar.complete();
      }
    });
  }
}
