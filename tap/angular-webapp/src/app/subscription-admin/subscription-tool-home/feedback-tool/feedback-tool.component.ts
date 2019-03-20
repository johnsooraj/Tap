import { Component, OnInit } from '@angular/core';
import { CommonLoginService } from 'src/app/common-login/common-login.service';
import { SubscriptionService } from '../../subscription.service';
import { FeedbackObject } from 'src/app/common-models/feedback';
import { SubscriptionToolService } from '../subscription-tool.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SubscriptionOptionType } from 'src/app/common-enums/subscription-types';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgProgress } from '@ngx-progressbar/core';
import { NgxNotificationService } from 'ngx-notification';
import { FeedbackGroupObject } from 'src/app/common-models/feedback-group';
import { UserTypes } from 'src/app/common-enums/uesr-types';

@Component({
  selector: 'app-feedback-tool',
  templateUrl: './feedback-tool.component.html',
  styleUrls: ['./feedback-tool.component.css']
})
export class FeedbackToolComponent implements OnInit {
  feedbackList = Array<FeedbackGroupObject>();

  constructor(
    private commonService: CommonLoginService,
    private localService: SubscriptionService,
    private closedService: SubscriptionToolService,
    private router: Router,
    private modalService: NgbModal,
    private progressbar: NgProgress,
    private toster: NgxNotificationService,
    private loginService: CommonLoginService
  ) { }

  ngOnInit() {
    this.localService.getOrganizationFeedbacksByOrgId().subscribe(data => {
      this.feedbackList = data;
      console.log(this.feedbackList);
    });
  }

  fetchFeedbackById(data: FeedbackGroupObject) {
    if (data.status == SubscriptionOptionType.Draft) {
      this.router.navigate([`/subscription/subscription-tools/feedback/${data.id}`]);
      return true;
    }
  }

  checkBoxEvent(event, type) {
    if (type === 'selectAll') {
      if (event.target.checked) {
        this.feedbackList.forEach(obj => (obj.checkboxStatus = true));
      } else {
        this.feedbackList.forEach(obj => (obj.checkboxStatus = false));
      }
    }
  }

  removeSlectedFeedbacks(content) {
    let status = false;
    this.feedbackList.forEach(obj => {
      if (obj.checkboxStatus) {
        status = true;
        return false;
      }
    });
    if (status)
      this.modalService.open(content, {
        windowClass: 'confirmModal',
        centered: true
      }).result.then(
        result => {
          if (result === 'delete') {
            this.deleteFeedbacks();
          }
        },
        reason => {
          console.log(reason);
        }
      );
  }

  deleteFeedbacks() {
    const tempArrya = Array<Number>();
    this.feedbackList.forEach(fee => {
      if (fee.checkboxStatus) {
        tempArrya.push(fee.id);
      }
    });
    this.progressbar.start();
    this.localService.deleteFeedbackGroupById(tempArrya).subscribe(
      res => {
        res.forEach(element => {
          const index = this.feedbackList.findIndex(obj => obj.id === element);
          if (index !== -1) {
            this.feedbackList.splice(index, 1);
          }
          this.progressbar.complete();
          this.toster.sendMessage(`Deleting Feedbacks Success!`, 'success', 'top-right');
        });
      },
      error => {
        this.progressbar.complete();
        this.toster.sendMessage(`Deleting Feedbacks failed!`, 'danger', 'top-right');
      }
    );
  }

  navigateToCustomers() {
    const selectedFeedbacks = Array<Number>();
    this.feedbackList.forEach(fee => {
      if (fee.checkboxStatus) {
        selectedFeedbacks.push(fee.id);
      }
    });
    console.log(selectedFeedbacks);
    this.router.navigate(['/subscription/subscription-tools/forward']);
  }
}
