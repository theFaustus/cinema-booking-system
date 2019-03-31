import {Component, Inject, OnInit} from '@angular/core';
import {SignUpInfo} from "../../model/sigup-info";
import {NotifierService} from "angular-notifier";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material";
import {UserService} from "../../services/user.service";
import {TokenStorageService} from "../../auth/token-storage.service";
import {AuthService} from "../../auth/auth.service";
import {MovieService} from "../../services/movie.service";
import {Movie} from "../../model/movie";

@Component({
  selector: 'app-add-movie-modal',
  templateUrl: './add-movie-modal.component.html',
  styleUrls: ['./add-movie-modal.component.css']
})
export class AddMovieModalComponent implements OnInit {

  form: any = {};
  movie: Movie;
  isSignUpFailed = false;
  errorMessage = '';
  private readonly notifier: NotifierService;

  constructor(private dialogRef: MatDialogRef<AddMovieModalComponent>,
              @Inject(MAT_DIALOG_DATA) public matData: any,
              private movieService: MovieService,
              public dialog: MatDialog,
              private token: TokenStorageService,
              private authService: AuthService,
              notifierService: NotifierService) {
    this.notifier = notifierService;

  }

  public closeDialog() {
    this.dialogRef.close();
  }

  ngOnInit() {

  }

  onSubmit() {
    console.log(this.form);

    this.movie = new Movie(
      null,
      this.form.name,
      this.form.description,
      this.form.imdbRating,
      "PT" + this.form.movieDuration + "S",
      null,
      this.form.directors.split(","),
      this.form.actors.split(","),
      this.form.imagePath);

    console.log(this.movie);


    this.movieService.createMovie(this.movie).subscribe(
      data => {
        console.log(data);
      },
      error => {
        console.log(error);
      }
    );

    this.closeDialog();
    this.notifier.notify('success', 'Movie [' + this.form.name + '] created!');
    this.matData.userTableRef.redraw();
  }

}
