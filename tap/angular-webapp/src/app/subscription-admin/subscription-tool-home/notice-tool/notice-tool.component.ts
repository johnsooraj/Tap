import { Component, OnInit } from '@angular/core';
import { NoticeObject } from 'src/app/common-models/notice';
import { CommonLoginService } from 'src/app/common-login/common-login.service';
import { SubscriptionService } from '../../subscription.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgProgress } from '@ngx-progressbar/core';
import { NgxNotificationService } from 'ngx-notification';
import { Router } from '@angular/router';

@Component({
  selector: 'app-notice-tool',
  templateUrl: './notice-tool.component.html',
  styleUrls: ['./notice-tool.component.css']
})
export class NoticeToolComponent implements OnInit {
  noticeList = Array<NoticeObject>();

  constructor(
    private commonService: CommonLoginService,
    private localService: SubscriptionService,
    private modalService: NgbModal,
    private progressbar: NgProgress,
    private toster: NgxNotificationService,
    private router: Router,
    private loginService: CommonLoginService
  ) { }

  ngOnInit() {
    this.localService.getOrganizationNoticesByOrgId().subscribe(data => {
      this.noticeList = data;
    });
  }

  checkBoxEvent(event, type) {
    if (event.target.checked) {
      if (type === 'selectall') {
        this.noticeList.forEach(obj => (obj.checkboxStatus = true));
      }
    } else {
      if (type === 'selectall') {
        this.noticeList.forEach(obj => (obj.checkboxStatus = false));
      }
    }
  }

  openDeleteModal(content) {
    let status = false;
    this.noticeList.forEach(obj => {
      if (obj.checkboxStatus) {
        status = true;
        return false;
      }
    });
    if (status)
      this.modalService
        .open(content, {
          windowClass: 'confirmModal',
          centered: true
        })
        .result.then(
          result => {
            if (result === 'delete') {
              this.deleteNotices();
            }
          },
          reason => {
            console.log(reason);
          }
        );
  }

  deleteNotices() {
    this.progressbar.start();
    const selectedNoticesbacks = Array<Number>();
    this.noticeList.forEach(fee => {
      if (fee.checkboxStatus) {
        selectedNoticesbacks.push(fee.noticeId);
      }
    });
    this.progressbar.set(80);
    this.localService.deleteNoticesById(selectedNoticesbacks).subscribe(
      res => {
        res.forEach(element => {
          const index = this.noticeList.findIndex(obj => obj.noticeId === element);
          if (index !== -1) {
            this.noticeList.splice(index, 1);
          }
        });
        this.progressbar.complete();
        this.toster.sendMessage(`Seletcted Notices Deleted Successfully!`, 'success', 'top-right');
      },
      error => {
        this.progressbar.complete();
        this.toster.sendMessage(`Deleting Notice failed!`, 'danger', 'top-right');
      }
    );
  }

  navigateToCustomers() {
    const selectedNotices = Array<Number>();
    this.noticeList.forEach(fee => {
      if (fee.checkboxStatus) {
        selectedNotices.push(fee.noticeId);
      }
    });
    console.log(selectedNotices);
    this.router.navigate(['/subscription/subscription-tools/forward']);
  }
}
