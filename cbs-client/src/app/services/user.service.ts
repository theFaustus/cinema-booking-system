import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Movie} from "../model/movie";
import {User} from "../model/user";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'If-Modified-Since': 'Mon, 26 Jul 1997 05:00:00 GMT',
    'Cache-Control': 'no-cache',
    'Pragma': 'no-cache'
  })
};

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  getUserBoard(): Observable<string> {
    return this.http.get('/server/v1/api/test/user', {responseType: 'text'});
  }

  getAdminBoard(): Observable<string> {
    return this.http.get('/server/v1/api/test/admin', {responseType: 'text'});
  }

  getUsers() {
    return this.http.get<User[]>('/server/v1/api/users', httpOptions);
  }

  getByUsername(username: string) {
    return this.http.get<User>('/server/v1/api/users/' + username + '/', httpOptions);
  }

  enableUser(user: User) {
    return this.http.post<User>('/server/v1/api/users/' + user.id + '/enable', httpOptions);
  }

  disableUser(user: User) {
    return this.http.post<User>('/server/v1/api/users/' + user.id + '/disable', httpOptions);
  }

  deleteUser(user: User) {
    return this.http.delete('/server/v1/api/users/' + user.id + "/", httpOptions);
  }
}
