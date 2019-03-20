import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminHomeContentComponent } from './admin-home-content.component';

describe('AdminHomeContentComponent', () => {
  let component: AdminHomeContentComponent;
  let fixture: ComponentFixture<AdminHomeContentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminHomeContentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminHomeContentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
