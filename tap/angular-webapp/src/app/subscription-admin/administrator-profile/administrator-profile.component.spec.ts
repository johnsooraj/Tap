import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministratorProfileComponent } from './administrator-profile.component';

describe('AdministratorProfileComponent', () => {
  let component: AdministratorProfileComponent;
  let fixture: ComponentFixture<AdministratorProfileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdministratorProfileComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdministratorProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
