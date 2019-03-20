import { Component, OnInit } from '@angular/core';
import { PollObject } from 'src/app/common-models/poll';
import { CommonLoginService } from 'src/app/common-login/common-login.service';
import { SubscriptionService } from '../../subscription.service';
import { NgxNotificationService } from 'ngx-notification';
import { NgProgress } from '@ngx-progressbar/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import { SubscriptionOptionType } from 'src/app/common-enums/subscription-types';

@Component({
  selector: 'app-poll-tool',
  templateUrl: './poll-tool.component.html',
  styleUrls: ['./poll-tool.component.css']
})
export class PollToolComponent implements OnInit {
  pollsList = Array<PollObject>();

  constructor(
    private commonService: CommonLoginService,
    private localService: SubscriptionService,
    private router: Router,
    private modalService: NgbModal,
    private progressbar: NgProgress,
    private toster: NgxNotificationService,
    private loginService: CommonLoginService
  ) { }

  ngOnInit() {
    this.localService.getOrganizationPollsByOrgId().subscribe(data => {
      this.pollsList = data;
    });
  }

  checkBoxEvent(event, type) {
    if (type === 'selectAll') {
      if (event.target.checked) {
        this.pollsList.forEach(poll => (poll.checkboxStatus = true));
      } else {
        this.pollsList.forEach(poll => (poll.checkboxStatus = false));
      }
    }
  }

  removePollModal(content) {
    let status = false;
    this.pollsList.forEach(obj => {
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
              this.deleteSelectedPolls();
            }
          },
          error => { }
        );
  }

  deleteSelectedPolls() {
    const temparray = Array<number>();
    this.pollsList.forEach(obj => {
      if (obj.checkboxStatus) {
        temparray.push(obj.pollId);
      }
    });
    this.progressbar.start();
    this.localService.deletePollsById(temparray).subscribe(
      data => {
        data.forEach(id => {
          const index = this.pollsList.findIndex(poll => poll.pollId === id);
          if (index !== -1) {
            this.pollsList.splice(index, 1);
          }
          this.progressbar.complete();
          this.toster.sendMessage(`Deleting Polls Success!`, 'success', 'top-right');
        });
      },
      error => {
        this.progressbar.complete();
        this.toster.sendMessage(`Deleting Polls failed!`, 'danger', 'top-right');
      }
    );
  }

  navigateToCustomers() {
    const selectedPolls = Array<Number>();
    this.pollsList.forEach(fee => {
      if (fee.checkboxStatus) {
        selectedPolls.push(fee.pollId);
      }
    });
    console.log(selectedPolls);
    this.router.navigate(['/subscription/subscription-tools/forward']);
  }
}
