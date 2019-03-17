import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material";
import {MovieSessionModalComponent} from "../movie-session-modal/movie-session-modal.component";
import {MovieService} from "../../services/movie.service";
import {MovieSessionService} from "../../services/movie-session.service";
import {HallService} from "../../services/hall.service";
import {TokenStorageService} from "../../auth/token-storage.service";
import {Seat} from "../../model/seat";
import {BookedMovie} from "../../model/booked-movie";
import {BookingNotificationModalComponent} from "../booking-notification-modal/booking-notification-modal.component";

@Component({
  selector: 'app-seat-booking-confirm-modal',
  templateUrl: './seat-booking-confirm-modal.component.html',
  styleUrls: ['./seat-booking-confirm-modal.component.css']
})
export class SeatBookingConfirmModalComponent implements OnInit {

  constructor(
    private movieSessionDialogRef: MatDialogRef<MovieSessionModalComponent>,
    private seatBookingConfirmDialogRef: MatDialogRef<SeatBookingConfirmModalComponent>,
    private seatBookingDialogRef: MatDialogRef<SeatBookingConfirmModalComponent>,
    @Inject(MAT_DIALOG_DATA) public matData: any,
    private movieService: MovieService,
    private movieSessionService: MovieSessionService,
    private hallService: HallService,
    public dialog: MatDialog,
    private token: TokenStorageService) {

  }


  public closeDialog() {
    this.seatBookingDialogRef.close();
  }

  ngOnInit() {
  }

  bookSeat(seat: Seat, movieSessionId: number) {
    var bookedMovie = new BookedMovie(movieSessionId, seat.seatNumber, this.token.getUsername());
    this.movieSessionService.bookMovie(bookedMovie).subscribe(data => {
      console.log(data);
      this.dialog.open(BookingNotificationModalComponent, {
        data: {
        },
        width: "700px",
      });
    });
    this.movieSessionDialogRef.close();
    this.seatBookingConfirmDialogRef.close();
    this.seatBookingDialogRef.close();
  }
}
