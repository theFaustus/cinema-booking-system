import {Component, Inject, OnInit} from '@angular/core';
import {MovieService} from "../../services/movie.service";
import {MovieSessionService} from "../../services/movie-session.service";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material";
import {MovieSessionModalComponent} from "../movie-session-modal/movie-session-modal.component";
import {HallService} from "../../services/hall.service";
import {Seat} from "../../model/seat";
import {MovieSession} from "../../model/movie-session";
import {BookedMovie} from "../../model/booked-movie";
import {TokenStorageService} from "../../auth/token-storage.service";
import {SeatBookingConfirmModalComponent} from "../seat-booking-confirm-modal/seat-booking-confirm-modal.component";

@Component({
  selector: 'app-seat-booking-modal',
  templateUrl: './seat-booking-modal.component.html',
  styleUrls: ['./seat-booking-modal.component.css']
})
export class SeatBookingModalComponent implements OnInit{
  retrieveData: Array<Seat> = [];

  constructor(private dialogRef: MatDialogRef<MovieSessionModalComponent>,
              @Inject(MAT_DIALOG_DATA) public matData: any,
              private movieService: MovieService,
              private movieSessionService: MovieSessionService,
              private hallService: HallService,
              public dialog: MatDialog,
              private token: TokenStorageService) {

  }

  public closeDialog() {
    this.dialogRef.close();
  }

  ngOnInit() {
    this.hallService.getSeatsByHallId(this.matData.hallId).subscribe(data => {
      console.log(data);
      this.retrieveData = data;
      this.retrieveData.sort(function(a, b) {
        return a.seatNumber.localeCompare(b.seatNumber);
      });
      console.log(this.retrieveData);
    });
  }

  openBookSeatConfirmModal(seat: Seat, movieSessionId : number) {
    console.log(movieSessionId);
    this.dialog.open(SeatBookingConfirmModalComponent, {
      data: {
        movieSessionId: movieSessionId,
        seatSeatNumber: seat.seatNumber,
        username: this.token.getUsername(),
        movieName: this.matData.movieName,
        showTime: this.matData.showTime,
        price: seat.price,
        seat: seat
      },
      width: "700px",
    });
  }
}
