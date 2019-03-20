import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubscriptionCustomersComponent } from './subscription-customers.component';

describe('SubscriptionCustomersComponent', () => {
  let component: SubscriptionCustomersComponent;
  let fixture: ComponentFixture<SubscriptionCustomersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubscriptionCustomersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubscriptionCustomersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
