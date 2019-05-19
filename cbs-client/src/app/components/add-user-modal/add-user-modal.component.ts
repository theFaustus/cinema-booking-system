import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material";
import {TokenStorageService} from "../../auth/token-storage.service";
import {UserService} from "../../services/user.service";
import {SignUpInfo} from "../../model/sigup-info";
import {AuthService} from "../../auth/auth.service";
import {NotifierService} from "angular-notifier";

@Component({
  selector: 'app-add-user-modal',
  templateUrl: './add-user-modal.component.html',
  styleUrls: ['./add-user-modal.component.css']
})
export class AddUserModalComponent implements OnInit {

  form: any = {};
  firstName: string;
  lastName: string;
  invalid: string;
  telephoneNumber: string;
  signupInfo: SignUpInfo;
  isSignUpFailed = false;
  errorMessage = '';
  private readonly notifier: NotifierService;

  constructor(private dialogRef: MatDialogRef<AddUserModalComponent>,
              @Inject(MAT_DIALOG_DATA) public matData: any,
              private userService: UserService,
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

    this.signupInfo = new SignUpInfo(
      this.form.username,
      this.form.email,
      this.form.password,
      this.form.telephoneNumber,
      this.form.firstName,
      this.form.lastName);

    console.log(this.signupInfo);


    this.authService.signUp(this.signupInfo).subscribe(
      data => {
        console.log(data);
        this.isSignUpFailed = false;
        this.matData.userTableRef.redraw();
      },
      error => {
        console.log(error);
        this.errorMessage = error.error.message;
        this.isSignUpFailed = true;
      }
    );

    this.closeDialog();
    this.notifier.notify('success', 'User [' + this.form.username + '] registered!');
    this.matData.userTableRef.redraw();
  }

}
