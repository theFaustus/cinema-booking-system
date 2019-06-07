import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {AuthLoginInfo} from "../model/login-info";
import {JwtResponse} from "../model/jwt-response";
import {SignUpInfo} from "../model/sigup-info";


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private loginUrl = '/v1/api/auth/sign-in';
  private signupUrl = '/v1/api/auth/sign-up';

  constructor(private http: HttpClient) {
  }

  attemptAuth(credentials: AuthLoginInfo): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(this.loginUrl, credentials, httpOptions);
  }

  signUp(info: SignUpInfo): Observable<string> {
    return this.http.post<string>(this.signupUrl, info, httpOptions);
  }
}
