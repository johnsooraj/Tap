import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PollToolComponent } from './poll-tool.component';

describe('PollToolComponent', () => {
  let component: PollToolComponent;
  let fixture: ComponentFixture<PollToolComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PollToolComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PollToolComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
