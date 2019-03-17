import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material";
import {MovieService} from "../../services/movie.service";
import {MovieSessionService} from "../../services/movie-session.service";
import {HallService} from "../../services/hall.service";
import {TokenStorageService} from "../../auth/token-storage.service";

@Component({
  selector: 'app-booking-notification-modal',
  templateUrl: './booking-notification-modal.component.html',
  styleUrls: ['./booking-notification-modal.component.css']
})
export class BookingNotificationModalComponent implements OnInit  {

  constructor(private dialogRef: MatDialogRef<BookingNotificationModalComponent>,
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
  }
}
