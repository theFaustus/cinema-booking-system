import {Component, Inject, OnInit} from '@angular/core';
import {Movie} from "../../model/movie";
import {NotifierService} from "angular-notifier";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material";
import {MovieService} from "../../services/movie.service";
import {TokenStorageService} from "../../auth/token-storage.service";
import {AuthService} from "../../auth/auth.service";
import {MovieSessionService} from "../../services/movie-session.service";
import {MovieSession} from "../../model/movie-session";
import {Hall} from "../../model/hall";
import {HallService} from "../../services/hall.service";

@Component({
  selector: 'app-add-movie-session-modal',
  templateUrl: './add-movie-session-modal.component.html',
  styleUrls: ['./add-movie-session-modal.component.css']
})
export class AddMovieSessionModalComponent implements OnInit {

  form: any = {};
  movieSession: MovieSession;
  halls: Hall[];
  movie: Movie;
  isSignUpFailed = false;
  errorMessage = '';
  private readonly notifier: NotifierService;

  constructor(private dialogRef: MatDialogRef<AddMovieSessionModalComponent>,
              @Inject(MAT_DIALOG_DATA) public matData: any,
              private movieSessionService: MovieSessionService,
              public dialog: MatDialog,
              private token: TokenStorageService,
              private authService: AuthService,
              private hallService: HallService,
              notifierService: NotifierService) {
    this.notifier = notifierService;

  }

  public closeDialog() {
    this.dialogRef.close();
  }

  ngOnInit() {
    this.hallService.getHalls().subscribe(data => {
      this.halls = data;
    });
    this.movie = this.matData.movie;
  }

  onSubmit() {
    console.log(this.form);

    this.movieSession = new MovieSession(null,
      this.form.hallId, null, this.form.showTime, this.matData.movie.id);
    console.log(this.movieSession);

    this.movieSessionService.createMovieSession(this.movieSession).subscribe(
      data => {
        console.log(data);
        this.notifier.notify('success', 'Movie session for movie [' + this.matData.movie.name + '] created!');
        this.matData.movieSessionTableRef.redraw();
      },
      error => {
        this.notifier.notify('success', 'Movie session for movie [' + this.matData.movie.name + '] created!');
        console.log(error);
      }
    );

    this.closeDialog();
    this.matData.movieSessionTableRef.redraw();
  }
}
