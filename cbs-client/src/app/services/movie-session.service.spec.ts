import { TestBed } from '@angular/core/testing';

import { MovieSessionService } from './movie-session.service';

describe('MovieSessionService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: MovieSessionService = TestBed.get(MovieSessionService);
    expect(service).toBeTruthy();
  });
});
