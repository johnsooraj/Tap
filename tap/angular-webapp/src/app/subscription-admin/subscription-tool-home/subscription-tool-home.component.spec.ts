import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubscriptionToolHomeComponent } from './subscription-tool-home.component';

describe('SubscriptionToolHomeComponent', () => {
  let component: SubscriptionToolHomeComponent;
  let fixture: ComponentFixture<SubscriptionToolHomeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubscriptionToolHomeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubscriptionToolHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
