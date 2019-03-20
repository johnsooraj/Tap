import { TestBed } from '@angular/core/testing';

import { SubscriptionToolService } from './subscription-tool.service';

describe('SubscriptionToolService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SubscriptionToolService = TestBed.get(SubscriptionToolService);
    expect(service).toBeTruthy();
  });
});
