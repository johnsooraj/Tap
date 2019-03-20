import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubscriptionToolsComponent } from './subscription-tools.component';

describe('SubscriptionToolsComponent', () => {
  let component: SubscriptionToolsComponent;
  let fixture: ComponentFixture<SubscriptionToolsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubscriptionToolsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubscriptionToolsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
