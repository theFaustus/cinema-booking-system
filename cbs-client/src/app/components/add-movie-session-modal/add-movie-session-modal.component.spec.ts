import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddMovieSessionModalComponent } from './add-movie-session-modal.component';

describe('AddMovieSessionModalComponent', () => {
  let component: AddMovieSessionModalComponent;
  let fixture: ComponentFixture<AddMovieSessionModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddMovieSessionModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddMovieSessionModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
