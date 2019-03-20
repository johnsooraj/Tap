import { Component, OnInit } from '@angular/core';
import { ClosedServiceService } from '../closed-service.service';
import { SubscriptionService } from '../../subscription.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgxNotificationService } from 'ngx-notification';
import { User } from 'src/app/common-models/user';

@Component({
  selector: 'app-view-administrator',
  templateUrl: './view-administrator.component.html',
  styleUrls: ['./view-administrator.component.css']
})
export class ViewAdministratorComponent implements OnInit {
  selectAllRows = false;
  searchOptions = Array<String>('Admin Name', 'Contact Number');
  isSearch: boolean = false;
  filteredTableData: Array<User>;

  constructor(
    private closedService: ClosedServiceService,
    private localService: SubscriptionService,
    private toster: NgxNotificationService,
    private modalService: NgbModal
  ) { }

  ngOnInit() { }

  checkBoxEvent(event, type) {
    if (event.target.checked) {
      this.closedService.subadminsList.forEach((obj) => {
        obj.checkboxStatus = true;
      })
    }else{
      this.closedService.subadminsList.forEach((obj) => {
        obj.checkboxStatus = false;
      })
    }
  }

  openUpdateAdminModel(content, data: User) {
    this.closedService.currentAdminModel = data;
    this.modalService.open(content, { centered: false, size: 'lg' }).result.then(
      result => {
        this.closedService.currentAdminModel = null;
      },
      reason => {
        this.closedService.currentAdminModel = null;
      }
    );
  }

  searchTable(searchData: any): void {
    this.filteredTableData = [];
    let regex: RegExp;

    this.isSearch = searchData.isSearch;
    if (this.isSearch) {
      switch (searchData.searchField) {
        case 'Admin Name':
          regex = new RegExp(searchData.searchText, 'i');
          for (const rowData of this.closedService.subadminsList) {
            const value: string = rowData.userData.firstName;
            if (regex.test(value)) {
              this.filteredTableData.push(rowData);
            }
          }
          break;
        case 'Contact Number':
          regex = new RegExp(searchData.searchText, 'i');
          for (const rowData of this.closedService.subadminsList) {
            const value: string = rowData.userData.phone === '-' ? '-' : rowData.userData.phone;
            if (regex.test(value)) {
              this.filteredTableData.push(rowData);
            }
          }
          break;
      }
    }
  }
}
