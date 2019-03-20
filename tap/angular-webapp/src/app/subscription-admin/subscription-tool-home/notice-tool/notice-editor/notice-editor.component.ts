import { Component, OnInit, ViewChild } from '@angular/core';
import { NoticeObject } from 'src/app/common-models/notice';
import { CommonLoginService } from 'src/app/common-login/common-login.service';
import { SubscriptionService } from 'src/app/subscription-admin/subscription.service';
import { SubscriptionToolService } from '../../subscription-tool.service';
import { Router } from '@angular/router';
import { NgbModal, NgbDateStruct, NgbDate } from '@ng-bootstrap/ng-bootstrap';
import { NgProgress } from '@ngx-progressbar/core';
import { NgxNotificationService } from 'ngx-notification';

@Component({
  selector: 'app-notice-editor',
  templateUrl: './notice-editor.component.html',
  styleUrls: ['./notice-editor.component.css']
})
export class NoticeEditorComponent implements OnInit {

  @ViewChild('noticePdfFile') noticePdfFile: any;
  @ViewChild('noticeImageFile') noticeImageFile: any;
  @ViewChild('imageNgbPopover') imageNgbPopover: any;
  @ViewChild('pdfNgbPopover') pdfNgbPopover: any;
  @ViewChild('noticeTitleNgbPopover') noticeTitleNgbPopover: any;
  @ViewChild('noticeDescriptionNgbPopover') noticeDescriptionNgbPopover: any;

  currentNoticeModel = new NoticeObject();

  ngDatePicModal: NgbDateStruct;

  constructor(
    private commonService: CommonLoginService,
    private localService: SubscriptionService,
    private closedService: SubscriptionToolService,
    private router: Router,
    private modalService: NgbModal,
    private progressbar: NgProgress,
    private toster: NgxNotificationService
  ) { }

  ngOnInit() {
    this.currentNoticeModel.attachmentByte = { image: null, pdf: null }
    let dt = new Date();
    this.ngDatePicModal = new NgbDate(dt.getFullYear(), (dt.getMonth() + 1), (dt.getDate() + 7));
  }

  noticeFileChange(event, type) {
    if (event.target.files && event.target.files[0]) {
      let myFile = event.target.files[0];

      if (type == 'image') {
        if (myFile.type.search('image')) {
          this.imageNgbPopover.open();
          setTimeout(() => {
            this.imageNgbPopover.close();
          }, 4000);
          return false;
        }
      } else if (type == 'pdf') {
        if (myFile.type.search('application')) {
          this.pdfNgbPopover.open();
          setTimeout(() => {
            this.pdfNgbPopover.close();
          }, 4000);
          return false;
        }
      }


      var reader = new FileReader();
      reader.readAsBinaryString(myFile);
      reader.onload = (event: any) => {
        var binaryString = event.target.result;
        let bytearray = btoa(binaryString);
        if (type == 'image') {
          this.currentNoticeModel.attachmentByte.image = bytearray;
        } else if (type == 'pdf') {
          this.currentNoticeModel.attachmentByte.pdf = bytearray;
        }
      }
    }
  }

  noticeButtonClickEvent(type) {

    if (type == 'cancel')
      this.router.navigate(['/subscription/subscription-tools/notice'])

    // check for title
    if (!this.currentNoticeModel.noticeText) {
      this.noticeTitleNgbPopover.open();
      return false;
    }

    if (this.currentNoticeModel.referencelink) {
      if (!this.currentNoticeModel.referencelink.includes('https://') || !this.currentNoticeModel.referencelink.includes('http://')) {
        this.currentNoticeModel.referencelink = 'http://' + this.currentNoticeModel.referencelink;
      }
    }

    // check for documents: optional

    // check for description 
    if (!this.currentNoticeModel.description) {
      this.noticeDescriptionNgbPopover.open();
      return false;
    }

    if (this.ngDatePicModal) {
      let exDate = new Date(this.ngDatePicModal.year, this.ngDatePicModal.month, this.ngDatePicModal.day);
      this.currentNoticeModel.exprieDate = exDate.getTime();
    }

    if (this.commonService.userData.userData) {
      this.currentNoticeModel.createdBy = this.commonService.userData.userData.userId;
      this.currentNoticeModel.createdUserName = this.commonService.userData.userData.firstName;
    }

    this.progressbar.start();
    this.localService.organizationModel.notices = [this.currentNoticeModel]
    this.localService.saveSubscriptionByOrganizationModel(this.localService.organizationModel).subscribe((res) => {
      this.localService.organizationModel.notices = [];
      this.toster.sendMessage(`${this.currentNoticeModel.noticeText} create success!`, 'success', 'top-right');
      this.currentNoticeModel = new NoticeObject();
      this.progressbar.complete();
      this.router.navigate(['/subscription/subscription-tools/notice']);
    }, (error) => {
      this.progressbar.complete();
      this.toster.sendMessage(`${this.currentNoticeModel.noticeText} create failed!`, 'danger', 'top-right');
    });


  }

}
