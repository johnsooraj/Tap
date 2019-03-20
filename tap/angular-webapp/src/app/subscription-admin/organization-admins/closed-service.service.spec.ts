import { TestBed } from '@angular/core/testing';

import { ClosedServiceService } from './closed-service.service';

describe('ClosedServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ClosedServiceService = TestBed.get(ClosedServiceService);
    expect(service).toBeTruthy();
  });
});
