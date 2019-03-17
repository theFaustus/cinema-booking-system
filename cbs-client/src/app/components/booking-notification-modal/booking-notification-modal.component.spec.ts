import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BookingNotificationModalComponent } from './booking-notification-modal.component';

describe('BookingNotificationModalComponent', () => {
  let component: BookingNotificationModalComponent;
  let fixture: ComponentFixture<BookingNotificationModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BookingNotificationModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BookingNotificationModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
