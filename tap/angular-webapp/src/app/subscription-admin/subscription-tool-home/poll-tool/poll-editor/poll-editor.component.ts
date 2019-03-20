import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { NgbTabsetConfig, NgbTimeStruct, NgbDateStruct, NgbCalendar, NgbTimepickerConfig, NgbModal, NgbDate } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';
import { PollObject } from 'src/app/common-models/poll';
import { SubscriptionOptionType } from 'src/app/common-enums/subscription-types';
import { CommonLoginService } from 'src/app/common-login/common-login.service';
import { SubscriptionService } from 'src/app/subscription-admin/subscription.service';
import { SubscriptionToolService } from '../../subscription-tool.service';
import { Router } from '@angular/router';
import { NgProgress } from '@ngx-progressbar/core';
import { NgxNotificationService } from 'ngx-notification';
import { MultipleOptionsQuestion } from 'src/app/common-models/multiple-options-question';
import { ImageObject } from 'src/app/common-models/image-options';
import { NgbTimeStructAdapter } from '@ng-bootstrap/ng-bootstrap/timepicker/ngb-time-adapter';

@Component({
  selector: 'app-poll-editor',
  templateUrl: './poll-editor.component.html',
  styleUrls: ['./poll-editor.component.css']
})
export class PollEditorComponent implements OnInit {

  @ViewChild('pollQuestionInput') pollQuestionInput: any;
  @ViewChild('pollOptionExpireInput') pollOptionExpireInput: any;
  @ViewChild('pollOptionQuestionInput') pollOptionQuestionInput: any;
  @ViewChild('pollOptionImageInput') pollOptionImageInput: any;
  @ViewChild('pollImage1FileInput') pollImage1FileInput: ElementRef;
  @ViewChild('pollImage2FileInput') pollImage2FileInput: ElementRef;
  @ViewChild('pollImage3FileInput') pollImage3FileInput: ElementRef;
  @ViewChild('pollImage4FileInput') pollImage4FileInput: ElementRef;

  ngTabOptions = {
    justify: 'justified',
    orientation: 'horizontal',
    selectedTap: 'multipleoption'
  };
  pollFormGroup: FormGroup;
  ngDatePicModal: NgbDateStruct;
  ngTimePicModal: NgbTimeStruct;
  currentPollModel: PollObject;
  pollSelectedOption: number;
  pollLiveResult = false;
  pollClosePoll = false;

  constructor(
    ngbTabsetConfig: NgbTabsetConfig,
    private formBuilder: FormBuilder,
    private calendar: NgbCalendar,
    ngbTimepickerConfig: NgbTimepickerConfig,
    private commonService: CommonLoginService,
    private localService: SubscriptionService,
    private closedService: SubscriptionToolService,
    private router: Router,
    private modalService: NgbModal,
    private progressbar: NgProgress,
    private toster: NgxNotificationService
  ) {
    ngbTimepickerConfig.seconds = false;
    ngbTimepickerConfig.spinners = false;
    ngbTimepickerConfig.size = 'small';
    ngbTimepickerConfig.meridian = true;

  }

  ngOnInit() {

    let dt = new Date();
    this.ngDatePicModal = new NgbDate(dt.getFullYear(), (dt.getMonth() + 1), (dt.getDate() + 7));
    this.ngTimePicModal = {
      "hour": dt.getHours(),
      "minute": dt.getMinutes(),
      "second": dt.getSeconds()
    };

    this.pollSelectedOption = SubscriptionOptionType.MultipleChoice;
    this.pollFormGroup = this.formBuilder.group({
      pollQuestion: ['', Validators.required],
      options: this.formBuilder.array([
        this.createOptionFormGroup(),
        this.createOptionFormGroup()
      ]),
      images: this.formBuilder.array([
        this.createImagesFormGroup(),
        this.createImagesFormGroup(),
        this.createImagesFormGroup(),
        this.createImagesFormGroup(),
      ])
    });
  }

  createOptionFormGroup(): FormGroup {
    return this.formBuilder.group({
      'optionText': ['', Validators.required]
    });
  }

  createImagesFormGroup(): FormGroup {
    return this.formBuilder.group({
      'imageByte': '',
      'imageURL': ''
    });
  }

  addOption() {
    const options = this.pollFormGroup.get('options') as FormArray;
    if (options['controls'].length >= 5) {
      this.toster.sendMessage(`Only 5 Poll Options Allowed!`, 'none', 'top-right');
    } else {
      options.push(this.createOptionFormGroup());
    }
  }

  optionIconClickEvent(event, id) {
    const index = id + 2;
    // console.log(event, id);
    const options = this.pollFormGroup.get('options') as FormArray;
    if (event === 'add') {
      if (options['controls'].length < 5) {
        this.addOption();
      }
    }
    if (event === 'remove' || id > -1) {
      options.removeAt(id);
    }
  }

  imageChooserClickEvent(imageElement) {
    if (imageElement.target.name === 'image1') {
      this.pollImage1FileInput.nativeElement.click();
    }
    if (imageElement.target.name === 'image2') {
      this.pollImage2FileInput.nativeElement.click();
    }
    if (imageElement.target.name === 'image3') {
      this.pollImage3FileInput.nativeElement.click();
    }
    if (imageElement.target.name === 'image4') {
      this.pollImage4FileInput.nativeElement.click();
    }
  }

  fileChangeEvent(nextElement) {
    if (nextElement.name === 'image1') {
      if (this.pollImage1FileInput.nativeElement.files && this.pollImage1FileInput.nativeElement.files[0]) {
        const file: File = this.pollImage1FileInput.nativeElement.files[0];
        this.insertImageByteToFormControl(file, 0, nextElement);
      }
    }
    if (nextElement.name === 'image2') {
      if (this.pollImage2FileInput.nativeElement.files && this.pollImage2FileInput.nativeElement.files[0]) {
        const file: File = this.pollImage2FileInput.nativeElement.files[0];
        this.insertImageByteToFormControl(file, 1, nextElement);
      }
    }
    if (nextElement.name === 'image3') {
      if (this.pollImage3FileInput.nativeElement.files && this.pollImage3FileInput.nativeElement.files[0]) {
        const file: File = this.pollImage3FileInput.nativeElement.files[0];
        this.insertImageByteToFormControl(file, 2, nextElement);
      }
    }
    if (nextElement.name === 'image4') {
      if (this.pollImage4FileInput.nativeElement.files && this.pollImage4FileInput.nativeElement.files[0]) {
        const file: File = this.pollImage4FileInput.nativeElement.files[0];
        this.insertImageByteToFormControl(file, 3, nextElement);
      }
    }
  }

  insertImageByteToFormControl(file, index, element) {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = (event: any) => {
      element.src = event.target.result;
    };
    setTimeout(() => {
      reader.readAsBinaryString(file);
      reader.onload = (event: any) => {
        const binaryString = event.target.result;
        const bytearray = btoa(binaryString);
        const images = this.pollFormGroup.get('images') as FormArray;
        images['controls'][index].value.imageByte = bytearray;
      };
    }, 500);
  }

  pollOptionChange(event) {
    if (event.nextId === 'multiplepolloption') {
      this.pollFormGroup.get('options').reset();
      this.pollFormGroup.get('images').reset();
      this.pollSelectedOption = SubscriptionOptionType.MultipleChoice;
    } else if (event.nextId === 'ratingpolloption') {
      this.pollFormGroup.get('options').reset();
      this.pollFormGroup.get('images').reset();
      this.pollSelectedOption = SubscriptionOptionType.Rating;
    } else if (event.nextId === 'imagespolloption') {
      this.pollFormGroup.get('options').reset();
      this.pollFormGroup.get('images').reset();
      this.pollSelectedOption = SubscriptionOptionType.Images;
    }
  }

  checkPollQuestionValidation(): boolean {
    return this.pollFormGroup.get('pollQuestion').valid;
  }

  checkPollOptionsValidation(): boolean {
    return this.pollFormGroup.get('options').valid;
  }

  checkPollImagesValidation(): boolean {
    let count = 0;
    const images = this.pollFormGroup.get('images') as FormArray;
    images['controls'].forEach((opt) => {
      if (!opt.value.imageByte) {
        count = count + 1;
      }
    });
    return count === 0 ? true : false;
  }

  pollButtonClick(type) {

    if (type === 'cancel') {
      this.router.navigate(['/subscription/subscription-tools/poll']);
    }
    this.currentPollModel = new PollObject();

    // setting creater username and id
    if (this.commonService.userData.userData) {
      this.currentPollModel.createdBy = this.commonService.userData.userData.userId;
      this.currentPollModel.createdUserName = this.commonService.userData.userData.firstName;
    }

    // setting option type
    this.currentPollModel.pollType = this.pollSelectedOption;

    // check for poll question and validate it
    if (this.checkPollQuestionValidation()) {
      this.currentPollModel.pollText = this.pollFormGroup.get('pollQuestion').value;
    } else {
      this.pollQuestionInput.ngbPopover = 'Poll Question Required!';
      this.pollQuestionInput.open();
      return false;
    }

    // check for multiple option and validate it
    if (this.pollSelectedOption === SubscriptionOptionType.MultipleChoice) {
      if (this.checkPollOptionsValidation()) {
        const optionstemp = Array<MultipleOptionsQuestion>();
        const options = this.pollFormGroup.get('options') as FormArray;
        options['controls'].forEach((opt) => {
          const obj = new MultipleOptionsQuestion();
          obj.optionText = opt.value.optionText;
          optionstemp.push(obj);
        });
        this.currentPollModel.questions = optionstemp;
      } else {
        this.pollOptionQuestionInput.ngbPopover = 'Poll Options Question Required!';
        this.pollOptionQuestionInput.open();
        return false;
      }
    }

    // check for image option and validate it
    if (this.pollSelectedOption === SubscriptionOptionType.Images) {
      if (this.checkPollImagesValidation()) {
        const optionstemp = Array<ImageObject>();
        const images = this.pollFormGroup.get('images') as FormArray;
        images['controls'].forEach((opt) => {
          const obj = new ImageObject();
          obj.imageByte = opt.value.imageByte;
          optionstemp.push(obj);
        });
        this.currentPollModel.pollImages = optionstemp;
      } else {
        this.pollOptionImageInput.ngbPopover = 'Poll Options Images Required!';
        this.pollOptionImageInput.open();
        return false;
      }
    }

    // check for expire date and time
    const expireDate = new Date();
    if (this.ngDatePicModal && this.ngTimePicModal) {
      expireDate.setFullYear(this.ngDatePicModal.year);
      expireDate.setMonth(this.ngDatePicModal.month - 1);
      expireDate.setDate(this.ngDatePicModal.day);

      expireDate.setHours(this.ngTimePicModal.hour);
      expireDate.setMinutes(this.ngTimePicModal.minute);
      expireDate.setSeconds(this.ngTimePicModal.second);
    } else {
      this.pollOptionExpireInput.ngbPopover = 'Poll Expire date and time Required!';
      this.pollOptionExpireInput.open();
      return false;
    }
    this.currentPollModel.exprieDate = expireDate.getTime();
    this.currentPollModel.colsePoll = this.pollClosePoll;
    this.currentPollModel.liveResult = this.pollLiveResult;

    this.progressbar.start();
    this.localService.organizationModel.polls = [this.currentPollModel];
    this.progressbar.set(60);
    this.localService.saveSubscriptionByOrganizationModel(this.localService.organizationModel).subscribe((obj) => {
      this.localService.organizationModel.polls = [];
      this.pollFormGroup.reset();
      this.progressbar.complete();
      this.toster.sendMessage(`${this.currentPollModel.pollText} create success!`, 'success', 'top-right');
      this.router.navigate(['/subscription/subscription-tools/poll']);
    }, (error) => {
      this.progressbar.complete();
      this.toster.sendMessage(`${this.currentPollModel.pollText} create failed!`, 'danger', 'top-right');
    });
  }
}
