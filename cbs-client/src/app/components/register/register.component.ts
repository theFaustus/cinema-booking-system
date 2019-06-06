import { Component, OnInit } from '@angular/core';
import {SignUpInfo} from "../../model/sigup-info";
import {AuthService} from "../../auth/auth.service";


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  form: any = {};
  signupInfo: SignUpInfo;
  isSignedUp = false;
  isSignUpFailed = false;
  errorMessage = '';

  constructor(private authService: AuthService) { }

  ngOnInit() { }

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
        this.isSignedUp = true;
        this.isSignUpFailed = false;
      },
      error => {
        console.log(error);
        this.errorMessage = error.message;
        this.isSignUpFailed = true;
      }
    );
  }
}
