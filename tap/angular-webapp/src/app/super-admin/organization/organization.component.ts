import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgProgress } from '@ngx-progressbar/core';
import { AppComponent } from 'src/app/app.component';
import { Organization } from '../../common-models/organization';
import { SuperAdminService } from '../super-admin.service';
import { NgxNotificationService } from 'ngx-notification';


@Component({
  selector: 'app-organization',
  templateUrl: './organization.component.html',
  styleUrls: ['./organization.component.css']
})
export class OrganizationComponent implements OnInit {

  @ViewChild('content2') confirmationModal: ElementRef;

  organizationList = Array<Organization>();

  addOrganizationForm: FormGroup;
  organizationName: FormControl;
  organizationEmail: FormControl;
  organizationAddress: FormControl;
  organizationAuthorityName: FormControl;
  organizationProfilePic: FormControl;
  organizationCoverPic: FormControl;
  base64textStringProfile: string;
  base64textStringCover: string;

  constructor(
    private superAdminService: SuperAdminService,
    private modalService: NgbModal,
    private progressBar: NgProgress,
    private router: Router,
    private appComponet: AppComponent,
    private toster: NgxNotificationService
  ) {
  }

  // fetch all organizations
  ngOnInit() {
    this.superAdminService.getAllOrganization().subscribe((data) => {
      if (data == null) {
        this.toster.sendMessage(`Organization fetch failed!`, 'danger', 'top-right');
      } else if (data.length > 0) {
        this.organizationList = data;
        this.organizationList.forEach(org => org.checkboxStatus = false);
      } else {

      }
    });
    this.setFormControls();
    this.setFormGroup();
  }

  openAddOrg(content) {
    this.modalService.open(content, {
      ariaLabelledBy: 'modal-basic-title',
      size: 'lg',
      centered: true
    }).result.then((result) => {
      console.log(result)
    }, (reason) => {
      console.log(reason)
    });
  }

  setFormControls() {
    this.organizationName = new FormControl('', [
      Validators.required,
      Validators.minLength(5)
    ]);
    this.organizationEmail = new FormControl('', [
      Validators.required,
    ]);
    this.organizationAuthorityName = new FormControl('', [
      Validators.required,
    ]);
    this.organizationAddress = new FormControl();
    this.organizationProfilePic = new FormControl();
    this.organizationCoverPic = new FormControl();
  }

  setFormGroup() {
    this.addOrganizationForm = new FormGroup({
      organizationName: this.organizationName,
      organizationEmail: this.organizationEmail,
      organizationAddress: this.organizationAddress,
      organizationProfilePic: this.organizationProfilePic,
      organizationAuthorityName: this.organizationAuthorityName
    });
  }

  profilePictureEventListner(evt) {
    var files = evt.target.files;
    var file = files[0];
    if (files && file) {
      var reader = new FileReader();
      reader.onload = this._handleReaderLoaded.bind(this);
      reader.readAsBinaryString(file);
    }
  }

  _handleReaderLoaded(readerEvt) {
    var binaryString = readerEvt.target.result;
    this.base64textStringProfile = btoa(binaryString);
    this.base64textStringCover = btoa(binaryString);
  }

  profileCoverEventListner(evt) {
    var files = evt.target.files;
    var file = files[0];
    if (files && file) {
      var reader = new FileReader();
      reader.onload = this._handleReaderLoaded2.bind(this);
      reader.readAsBinaryString(file);
    }
  }

  _handleReaderLoaded2(readerEvt) {
    var binaryString = readerEvt.target.result;
    this.base64textStringCover = btoa(binaryString);
  }



  checkEventListner(evt, type: string) {
    if (type == 'all') {
      if (evt.target.checked) {
        this.checkAllRow();
      } else {
        this.uncheckAllRow();
      }
    }
  }

  checkAllRow() {
    this.organizationList.forEach(org => (org.checkboxStatus = true));
  }

  uncheckAllRow() {
    this.organizationList.forEach(org => (org.checkboxStatus = false));
  }

  addOrgFormSubmit() {
    if (this.addOrganizationForm.valid) {
      if (this.organizationName.valid && this.organizationEmail.valid) {
        this.progressBar.start();
        let orgObj = new Organization();
        // Address not fixed
        orgObj.organizationName = this.organizationName.value;
        orgObj.email = this.organizationEmail.value;
        orgObj.profilePhotoByte = this.base64textStringProfile;
        orgObj.coverPhotoByte = this.base64textStringCover;
        orgObj.authorityName = this.organizationAuthorityName.value;
        this.superAdminService.saveOrganization(orgObj).subscribe((data) => {
          this.organizationList.unshift(data)
          this.progressBar.complete();
          this.addOrganizationForm.reset();
        });
        this.progressBar.set(0.8);
        this.modalService.dismissAll();
      }
    }
  }

  deleteSelectedOrg() {
    let idArray = [];

    this.organizationList.forEach(org => {
      if (org.checkboxStatus) {
        idArray.push(org.organizationId);
      }
    });
    this.superAdminService.deleteMultipleOrganization(idArray).subscribe((reslt) => {
      if (reslt) {
        let temparray = Array<Organization>();
        this.organizationList.forEach(org => {
          if (!org.checkboxStatus) {
            temparray.push(org);
          }
        });
        this.organizationList = [];
        temparray.forEach(obj => {
          this.organizationList.push(obj);
        });
        this.toster.sendMessage(`Organization delete Success!`, 'success', 'top-right');
      } else {
        this.toster.sendMessage(`Organization delete failed!`, 'danger', 'top-right');
      }
    });
  }

  openDeleteConfirmation() {
    this.modalService.open(this.confirmationModal).result.then((result) => {
      if (result === 'delete') {
        this.deleteSelectedOrg();
      }
    }, (reason) => {
      console.log(reason)
    });
  }

  deleteConfirmation(): boolean {
    return true;
  }

  viewEditOrganization(data: Organization) {
    this.router.navigate(['/superadmin/organization', data.organizationId]);
  }

}
