import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SeatBookingConfirmModalComponent } from './seat-booking-confirm-modal.component';

describe('SeatBookingConfirmModalComponent', () => {
  let component: SeatBookingConfirmModalComponent;
  let fixture: ComponentFixture<SeatBookingConfirmModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SeatBookingConfirmModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SeatBookingConfirmModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
