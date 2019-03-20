import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OrganizationAdminsComponent } from './organization-admins.component';

describe('OrganizationAdminsComponent', () => {
  let component: OrganizationAdminsComponent;
  let fixture: ComponentFixture<OrganizationAdminsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OrganizationAdminsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrganizationAdminsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
