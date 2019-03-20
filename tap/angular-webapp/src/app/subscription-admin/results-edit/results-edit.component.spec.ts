import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ResultsEditComponent } from './results-edit.component';

describe('ResultsEditComponent', () => {
  let component: ResultsEditComponent;
  let fixture: ComponentFixture<ResultsEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ResultsEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResultsEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
