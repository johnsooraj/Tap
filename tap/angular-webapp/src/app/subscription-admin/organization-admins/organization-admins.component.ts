import { Component, OnInit } from '@angular/core';
import { SubscriptionService } from '../subscription.service';
import { ClosedServiceService } from './closed-service.service';
import { NgProgress } from '@ngx-progressbar/core';
import { NgxNotificationService } from 'ngx-notification';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-organization-admins',
  templateUrl: './organization-admins.component.html',
  styleUrls: ['./organization-admins.component.css']
})
export class OrganizationAdminsComponent implements OnInit {
  constructor(
    private closedService: ClosedServiceService,
    private localService: SubscriptionService,
    private progressbar: NgProgress,
    private toster: NgxNotificationService,
    private modalService: NgbModal
  ) { }

  ngOnInit() {
    this.adminstratorPanel('add');
    this.localService.getOrganizationAdministratorByOrgId().subscribe(data => {
      this.closedService.subadminsList = data;
      this.closedService.subadminsList.forEach(obj => obj.checkboxStatus = false);
      console.log('subadminsList: ', this.closedService.subadminsList);
    });
  }

  adminstratorPanel(value: string) {
    switch (value) {
      case 'add':
        this.closedService.showAddAdmin = true;
        this.closedService.showEditAdmin = false;
        break;
      case 'edit':
      default:
        this.closedService.showAddAdmin = false;
        this.closedService.showEditAdmin = true;
        break;
    }
  }

  removeAdmins() {
    this.progressbar.start();
    const tempUser = new Array<Number>();
    this.closedService.subadminsList.forEach(data => (data.checkboxStatus ? tempUser.push(data.memberId) : null));
    this.localService.removeCustomerFromOrganization(tempUser).subscribe(
      ids => {
        ids.forEach(id => {
          const index = this.closedService.subadminsList.findIndex(obj => obj.memberId === id);
          if (index !== -1) {
            this.closedService.subadminsList.splice(index, 1);
          }
        });
        this.closedService.showDeleteAdminButton = false;
        this.progressbar.complete();
        this.toster.sendMessage(tempUser.length + ' administrators has been removed!', 'success', 'top-right');
      },
      error => {
        this.toster.sendMessage(error.developerMessage, 'danger', 'top-right');
      }
    );
  }

  openRemoveAdminModel(content) {
    let status = false;
    this.closedService.subadminsList.forEach(obj => {
      if (obj.checkboxStatus) {
        status = true;
        return false;
      }
    });
    if (status)
      this.modalService.open(content, { windowClass: 'confirmModal', centered: true }).result.then(
        result => {
          if (result === 'delete') {
            this.removeAdmins();
          }
        },
        reason => {
          console.log('error reson' + reason);
        }
      );
  }
}
