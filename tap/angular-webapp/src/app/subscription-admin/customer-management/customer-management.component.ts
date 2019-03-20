import { FormControl, Validators, FormGroup } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { CommonLoginService } from 'src/app/common-login/common-login.service';
import { SubscriptionService } from '../subscription.service';
import { User } from 'src/app/common-models/user';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgProgress } from '@ngx-progressbar/core';
import { NgxNotificationService } from 'ngx-notification';

class AddCustomerModel {
  phone: number;
  email: string;
}

@Component({
  selector: 'app-customer-management',
  templateUrl: './customer-management.component.html',
  styleUrls: ['./customer-management.component.css']
})
export class CustomerManagementComponent implements OnInit {
  organizationCustomers: Array<User> = [];
  customerModel = new AddCustomerModel();
  searchOptions = Array<String>('Customer Name', 'Contact Number');
  isSearch: boolean = false;
  filteredTableData: Array<User>;
  errormessage: string = '--';

  phoneNumber: FormControl;
  emailId: FormControl;
  newuserFormGroup: FormGroup;

  constructor(
    private commonService: CommonLoginService,
    private localService: SubscriptionService,
    private modalService: NgbModal,
    private progressbar: NgProgress,
    private toster: NgxNotificationService
  ) {
    this.localService.getOrganizationMembersByOrgId().subscribe(users => {
      this.organizationCustomers = users;
    });
  }

  ngOnInit() {
    this.phoneNumber = new FormControl('', [
      Validators.minLength(5)
    ]);
    this.emailId = new FormControl('', [
      Validators.required,
      Validators.pattern("[^ @]*@[^ @]*")
    ]);
    this.newuserFormGroup = new FormGroup({
      phoneNumber: this.phoneNumber,
      emailId: this.emailId
    });
  }

  addCustomerModal(content) {
    this.modalService
      .open(content, {
        windowClass: 'addCustomer',
        centered: true
      })
      .result.then(
        result => { },
        reason => {
          this.newuserFormGroup.reset();
        }
      );
  }

  removeCustomerModal(content) {
    let status = false;
    this.organizationCustomers.forEach(obj => {
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
              this.removeCustomers();
            }
          },
          reason => {

          }
        );
  }

  checkboxEventListner(event) {
    if (event.target.checked) {
      this.organizationCustomers.forEach(cust => cust.checkboxStatus = true);
    } else {
      this.organizationCustomers.forEach(cust => cust.checkboxStatus = false);
    }
  }

  clearValues() {
    this.customerModel.email = null;
    this.customerModel.phone = null;
  }

  addNewCustomer() {

    if (!this.phoneNumber.valid && !this.emailId.valid) {
      this.errormessage = 'enter phone number or email address';
    }

    if (this.phoneNumber.value) {
      if (!this.phoneNumber.valid) {
        this.errormessage = 'enter a valid phone number';
        return false;
      } else {
        this.customerModel.phone = this.phoneNumber.value;
        this.errormessage = '--';
      }
    }

    if (this.emailId.value) {
      if (!this.emailId.valid) {
        this.errormessage = 'enter a valid email address';
        return false;
      } else {
        this.customerModel.email = this.emailId.value;
        this.errormessage = '--';
      }
    }


    if (this.customerModel.email || this.customerModel.phone) {
      this.progressbar.start();
      this.localService.addCustomerToOrganizationByOrgId(this.customerModel).subscribe(
        result => {
          if (result.errorCode) {
            this.progressbar.complete();
            this.toster.sendMessage(result.developerMessage, 'danger', 'top-right');
          } else {
            const tempuser = result.members[result.members.length - 1];
            this.organizationCustomers.unshift(tempuser);
            this.toster.sendMessage(
              `${this.customerModel.email || this.customerModel.phone} added to this organization!`,
              'success',
              'top-right'
            );
          }
          this.progressbar.complete();
          this.clearValues();
          this.modalService.dismissAll();
        },
        error => {
          this.progressbar.complete();
          this.modalService.dismissAll();
        }
      );
    } else {
    }
    //this.modalService.dismissAll();
  }

  removeCustomers() {
    const tempUser = new Array<Number>();
    this.organizationCustomers.forEach(user => {
      if (user.checkboxStatus) {
        tempUser.push(user.memberId);
      }
    });
    if (tempUser.length > 0) {
      this.localService.removeCustomerFromOrganization(tempUser).subscribe(ids => {
        if (ids.length > 0) {
          ids.forEach(id => {
            const index = this.organizationCustomers.findIndex(obj => obj.memberId === id);
            if (index !== -1) {
              this.organizationCustomers.splice(index, 1);
            }
          });
        }
      });
    }
  }

  searchTable(searchData: any): void {
    this.filteredTableData = [];
    let regex: RegExp;

    this.isSearch = searchData.isSearch;
    if (this.isSearch) {
      switch (searchData.searchField) {
        case 'Customer Name':
          regex = new RegExp(searchData.searchText, 'i');
          for (const rowData of this.organizationCustomers) {
            const value: string = rowData.userData.firstName === '-' ? '-' : rowData.userData.firstName + ' ' + rowData.userData.lastName;
            if (regex.test(value)) {
              this.filteredTableData.push(rowData);
            }
          }
          break;
        case 'Contact Number':
          regex = new RegExp(searchData.searchText, 'i');
          for (const rowData of this.organizationCustomers) {
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
