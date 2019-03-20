import { TestBed } from '@angular/core/testing';

import { CommonLoginService } from './common-login.service';

describe('CommonLoginService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CommonLoginService = TestBed.get(CommonLoginService);
    expect(service).toBeTruthy();
  });
});
