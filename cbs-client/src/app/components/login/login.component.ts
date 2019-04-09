import { Component, OnInit } from '@angular/core';
import {AuthLoginInfo} from "../../model/login-info";
import {AuthService} from "../../auth/auth.service";
import {TokenStorageService} from "../../auth/token-storage.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {ActuatorService} from "../../services/actuator.service";
import {Health} from "../../model/actuator/health";
import {HttpServerRequest} from "../../model/actuator/http-server-request";
import {Info} from "../../model/actuator/info";
import {ProcessUptime} from "../../model/actuator/process-uptime";
import {App} from "../../model/actuator/app";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  form: any = {};
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];
  private loginInfo: AuthLoginInfo;
  loginForm: FormGroup;
  private authority: string;

  health: Health;
  httpServerRequest: HttpServerRequest;
  info: Info;
  processUpTime: ProcessUptime;



  constructor(private actuatorService: ActuatorService,
              private router: Router,
              private authService: AuthService,
              private tokenStorage: TokenStorageService,
              private formBuilder: FormBuilder
  ) { }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getAuthorities();
    }
    this.loginForm = this.formBuilder.group({
      email   : ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });

    if (this.tokenStorage.getToken()) {
      this.roles = this.tokenStorage.getAuthorities();
      this.roles.every(role => {
        if (role === 'ROLE_ADMIN') {
          this.authority = 'admin';
          return false;
        }
        this.authority = 'user';
        return true;
      });
    }

    this.actuatorService.getHealth().subscribe(value => {
      console.log(value);
      this.health = value;
    });

    this.actuatorService.getHttpProcessUpTimeMetrics().subscribe(value => {
      console.log(value);
      this.processUpTime = value;
    });

    this.actuatorService.getHttpServerRequestMetrics().subscribe(value => {
      console.log(value);
      this.httpServerRequest = value;
    });

    this.actuatorService.getInfo().subscribe(value => {
      this.info = value;
      console.log(value);

    });
  }

  onSubmit() {
    console.log(this.form);

    this.loginInfo = new AuthLoginInfo(
      this.form.username,
      this.form.password);

    this.authService.attemptAuth(this.loginInfo).subscribe(
      data => {
        this.tokenStorage.saveToken(data.token);
        console.log(data.token);
        this.tokenStorage.saveUsername(data.username);
        console.log(data.username);
        this.tokenStorage.saveAuthorities(data.authorities);
        console.log(data.authorities);

        if (this.tokenStorage.getToken()) {
          this.roles = this.tokenStorage.getAuthorities();
          this.roles.every(role => {
            if (role === 'ROLE_ADMIN') {
              this.authority = 'admin';
              return false;
            }
            this.authority = 'user';
            return true;
          });
        }

        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.roles = this.tokenStorage.getAuthorities();
        this.reloadPage();
      },
      error => {
        console.log(error);
        this.errorMessage = error.error.message;
        this.isLoginFailed = true;
      }
    );
  }

  reloadPage() {
    window.location.reload();
  }
}
