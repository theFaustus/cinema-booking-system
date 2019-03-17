import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SeatBookingModalComponent } from './seat-booking-modal.component';

describe('SeatBookingModalComponent', () => {
  let component: SeatBookingModalComponent;
  let fixture: ComponentFixture<SeatBookingModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SeatBookingModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SeatBookingModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
