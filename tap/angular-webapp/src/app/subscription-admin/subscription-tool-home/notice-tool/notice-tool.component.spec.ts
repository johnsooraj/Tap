import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NoticeToolComponent } from './notice-tool.component';

describe('NoticeToolComponent', () => {
  let component: NoticeToolComponent;
  let fixture: ComponentFixture<NoticeToolComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NoticeToolComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NoticeToolComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
