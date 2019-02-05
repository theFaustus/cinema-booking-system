import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MovieSessionModalComponent } from './movie-session-modal.component';

describe('MovieSessionModalComponent', () => {
  let component: MovieSessionModalComponent;
  let fixture: ComponentFixture<MovieSessionModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MovieSessionModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MovieSessionModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
