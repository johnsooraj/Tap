import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FeedbackToolComponent } from './feedback-tool.component';

describe('FeedbackToolComponent', () => {
  let component: FeedbackToolComponent;
  let fixture: ComponentFixture<FeedbackToolComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FeedbackToolComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FeedbackToolComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
