import {Component, Inject, OnInit} from '@angular/core';
import {Movie} from "../../model/movie";
import {NotifierService} from "angular-notifier";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material";
import {MovieService} from "../../services/movie.service";
import {TokenStorageService} from "../../auth/token-storage.service";
import {AuthService} from "../../auth/auth.service";
import {HallService} from "../../services/hall.service";
import {Hall} from "../../model/hall";

@Component({
  selector: 'app-add-hall-modal',
  templateUrl: './add-hall-modal.component.html',
  styleUrls: ['./add-hall-modal.component.css']
})
export class AddHallModalComponent implements OnInit {

  form: any = {};
  hall: Hall;
  isSignUpFailed = false;
  errorMessage = '';
  private readonly notifier: NotifierService;

  constructor(private dialogRef: MatDialogRef<AddHallModalComponent>,
              @Inject(MAT_DIALOG_DATA) public matData: any,
              private hallService: HallService,
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

    this.hall = new Hall(
      null,
      this.form.name,
      this.form.description,
      this.form.numberOfSeats,
      this.form.imagePath);

    console.log(this.hall);


    this.hallService.createHall(this.hall, this.form.numberOfSeats).subscribe(
      data => {
        console.log(data);
        this.matData.hallTableRef.redraw();
        this.notifier.notify('success', 'Hall [' + this.form.name + '] created!');
      },
      error => {
        this.notifier.notify('error', 'Hall [' + this.form.name + '] not created!');
        console.log(error);
      }
    );

    this.closeDialog();
    this.matData.movieTableRef.redraw();
  }
}
