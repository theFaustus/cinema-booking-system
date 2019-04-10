import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddHallModalComponent } from './add-hall-modal.component';

describe('AddHallModalComponent', () => {
  let component: AddHallModalComponent;
  let fixture: ComponentFixture<AddHallModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddHallModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddHallModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
