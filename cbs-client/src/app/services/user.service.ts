import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getUserBoard(): Observable<string> {
    return this.http.get('/server/v1/api/test/user', { responseType: 'text' });
  }

  getAdminBoard(): Observable<string> {
    return this.http.get('/server/v1/api/test/admin', { responseType: 'text' });
  }
}
