import { Component, OnInit, ElementRef, ViewChild, QueryList, ViewChildren } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators, Form } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonLoginService } from 'src/app/common-login/common-login.service';
import { FeedbackObject } from 'src/app/common-models/feedback';
import { MultipleOptionsQuestion } from 'src/app/common-models/multiple-options-question';
import { UserDetails } from 'src/app/common-models/user-details';
import { SubscriptionService } from 'src/app/subscription-admin/subscription.service';
import { SubscriptionToolService } from '../../subscription-tool.service';
import { SubscriptionOptionType } from 'src/app/common-enums/subscription-types';
import { Organization } from 'src/app/common-models/organization';
import { NgProgress } from '@ngx-progressbar/core';
import { NgxNotificationService } from 'ngx-notification';
import { FeedbackGroupObject } from 'src/app/common-models/feedback-group';
import { trigger, state, style, animate, transition } from '@angular/animations';
import { UserTypes } from 'src/app/common-enums/uesr-types';
import { NgbTabset, NgbDateStruct, NgbDate } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-feedback-editor',
  templateUrl: './feedback-editor.component.html',
  styleUrls: ['./feedback-editor.component.css'],
  animations: [
    trigger('showHide', [
      state(
        'show',
        style({
          display: 'block',
          transform: 'translateX(0)',
          opacity: 1
        })
      ),
      state(
        'hide',
        style({
          display: 'none',
          transform: 'translateX(100%)',
          opacity: 0
        })
      ),
      transition('show => hide', [animate('0.25s ease-in')]),
      transition('hide => show', [animate('0.25s ease-in')])
    ])
  ]
})
export class FeedbackEditorComponent implements OnInit {
  feedbackGroupFormGroup: FormGroup;
  ngTabOptions = {
    justify: 'justified',
    orientation: 'horizontal',
    selectedTap: 'multipleoption'
  };
  queNumber: number = 0;
  feedbackGroupId: number;
  ngDatePicModal: NgbDateStruct;

  @ViewChildren(NgbTabset) ngTabsets: QueryList<NgbTabset>;

  constructor(
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private commonService: CommonLoginService,
    private closedService: SubscriptionToolService,
    private localService: SubscriptionService,
    private router: Router,
    private progressbar: NgProgress,
    private toster: NgxNotificationService
  ) { }

  ngOnInit() {
    this.setFeedbackGroupFormGroup();
    this.fetchAndSetFeedbackGroup();
    let dt = new Date();
    this.feedbackGroupFormGroup.get('exprieDate').setValue(new NgbDate(dt.getFullYear(), (dt.getMonth() + 1), (dt.getDate() + 7)));
  }

  fetchAndSetFeedbackGroup() {
    this.route.params.subscribe(params => {
      const feedbackid = params['id'];
      if (feedbackid > 0) {
        this.localService.fetchFeedbackGroupById(feedbackid).subscribe(data => {
          this.setFeedbacGroupValues(data);
          this.feedbackGroupId = data.id;
        });
      }
    });
  }

  setFeedbacGroupValues(data: FeedbackGroupObject) {
    if (data.feedbackFormName) {
      let exDate = new Date(data.exprieDate);
      this.feedbackGroupFormGroup.controls['feedbackFormName'].setValue(data.feedbackFormName);
      this.feedbackGroupFormGroup.controls['id'].setValue(data.id);
      this.feedbackGroupFormGroup.controls['exprieDate'].setValue(new NgbDate(exDate.getFullYear(), (exDate.getMonth() + 1), (exDate.getDate())));
    }
    if (data.feedbacks.length > 0) {
      data.feedbacks.forEach((obj, index) => {
        let formGroup = (<FormArray>this.feedbackGroupFormGroup.controls['feedbacks']).at(index) as FormGroup;
        if (formGroup) {
          formGroup.controls['feedbackText'].setValue(obj.feedbackText);
        } else {
          this.addSingleFeedback();
          formGroup = (<FormArray>this.feedbackGroupFormGroup.controls['feedbacks']).at(index) as FormGroup;
        }
        formGroup.controls['feedbackText'].setValue(obj.feedbackText);
        formGroup.controls['feedbackId'].setValue(obj.feedbackId);
        if (obj.feedbackType == SubscriptionOptionType.MultipleChoice) {
          if (obj.questions.length > 0) {
            obj.questions.forEach((obj, indexx) => {
              this.addSngleOptionToFeedbackArray(index);
              const optionFormArray = (<FormArray>this.feedbackGroupFormGroup.controls['feedbacks']).at(index).get('questions') as FormArray;
              let texxt = optionFormArray.at(indexx).get("optionText");
              let id = optionFormArray.at(indexx).get("choiceId");
              id.setValue(obj.choiceId);
              texxt.setValue(obj.optionText)
            });
          }
        }
      });
      setTimeout(() => {
        data.feedbacks.forEach((obj, index) => {
          this.ngTabsets.forEach((tab, indexx) => {
            if (index == indexx) {
              if (obj.feedbackType == SubscriptionOptionType.Rating) {
                tab.select('ratingoption' + index);
              }
              if (obj.feedbackType == SubscriptionOptionType.FreeComment) {
                tab.select('freecommentoption' + index);
              }
            }
          })
        });
      }, 100);
    }
  }

  setFeedbackGroupFormGroup() {
    this.feedbackGroupFormGroup = this.formBuilder.group({
      feedbackFormName: ['', Validators.required],
      exprieDate: ['', Validators.required],
      id: [],
      feedbacks: this.formBuilder.array([this.getFeedbackArrayForm()])
    });
  }

  getFeedbackArrayForm(): FormGroup {
    return this.formBuilder.group({
      feedbackId: [],
      feedbackText: ['', Validators.required],
      feedbackType: [2, Validators.required], // Default is multiple option
      questions: this.formBuilder.array([])
    });
  }

  getMultipleOptionsFormArray(): FormGroup {
    return this.formBuilder.group({
      choiceId: [],
      optionText: ['', Validators.required]
    });
  }

  addSingleFeedback() {
    const control = <FormArray>this.feedbackGroupFormGroup.controls['feedbacks'];
    if (control['controls'].length >= 5) {
      this.toster.sendMessage(`Only 5 Feedbacks Allowed!`, 'none', 'top-right');
    } else {
      control.push(this.getFeedbackArrayForm());
      this.gotoQuestion(control['controls'].length === 1 ? 0 : this.queNumber + 1);
    }
  }

  addSngleOptionToFeedbackArray(fIndex) {
    const control = (<FormArray>this.feedbackGroupFormGroup.controls['feedbacks']).at(fIndex).get('questions') as FormArray;
    if (control['controls'].length >= 5) {
      this.toster.sendMessage(`only 5 Feedbacks Options Allowed!`, 'none', 'top-right');
    } else {
      control.push(this.getMultipleOptionsFormArray());
    }
  }

  removeFeedback(fIndex) {
    if (fIndex >= 0) {
      const control = <FormArray>this.feedbackGroupFormGroup.controls['feedbacks'];
      control.removeAt(fIndex);
      this.gotoQuestion(fIndex === 0 ? 0 : fIndex - 1);
    }
  }

  removeFeedbackOption(fIndex, oIndex) {
    const control = (<FormArray>this.feedbackGroupFormGroup.controls['feedbacks']).at(fIndex).get('questions') as FormArray;
    control.removeAt(oIndex);
  }

  feedbackOptionChange(event, ifeed) {
    const control = (<FormArray>this.feedbackGroupFormGroup.controls['feedbacks']).at(ifeed);
    const type =
      event.nextId === `multipleoption${ifeed}`
        ? 2
        : event.nextId === `ratingoption${ifeed}`
          ? 3
          : event.nextId === `freecommentoption${ifeed}`
            ? 4
            : null;
    control.get('feedbackType').setValue(type);
  }

  feedbackEditorButtonEvent(event) {
    if (event.target.id === 'cancel') {
      this.router.navigate(['/subscription/subscription-tools/feedback']);
      return true;
    }
    if (event.target.id === 'draft' || event.target.id === 'finish') {

      if (this.commonService.userData.memberType == UserTypes.Administrator) {
        this.toster.sendMessage(`Adminstrator's can't create subscriptions!`, 'warning', 'top-right');
        return false;
      }

      const finalData = this.feedbackGroupFormGroup.value;
      finalData.status = event.target.id === 'draft' ? 0 : 1;
      finalData.feedbacks.forEach(element => {
        element.feedbackCreaterId = this.commonService.userData.userData.userId;
      });
      if (this.feedbackGroupFormGroup.valid) {
        let exDate = new Date(finalData.exprieDate.year, (finalData.exprieDate.month - 1), finalData.exprieDate.day);
        finalData.exprieDate = exDate.getTime();
        this.localService.organizationModel.feedbackGroups = [];
        this.localService.organizationModel.feedbackGroups.push(finalData);
        if (!this.feedbackGroupId) {
          this.localService.saveSubscriptionByOrganizationModel(this.localService.organizationModel).subscribe(res => {
            this.localService.organizationModel.feedbackGroups = [];
            this.feedbackGroupFormGroup.reset();
            this.toster.sendMessage(`Feedbacks Saved Successfully!`, 'success', 'top-right');
            this.router.navigate(['/subscription/subscription-tools/feedback']);
          });
        } else {
          this.localService.updateSubscriptionByOrganizationModel(this.localService.organizationModel).subscribe(res => {
            this.localService.organizationModel.feedbackGroups = [];
            this.feedbackGroupFormGroup.reset();
            this.toster.sendMessage(`Feedbacks Saved Successfully!`, 'success', 'top-right');
            this.router.navigate(['/subscription/subscription-tools/feedback']);
          });
        }
      } else {
        this.toster.sendMessage(`All Fields Must be filled!`, 'warning', 'top-right');
      }
    }
  }

  gotoQuestion(queNumber: number): void {
    // console.log('queNumber: ', queNumber);
    this.queNumber = queNumber;
  }
}
