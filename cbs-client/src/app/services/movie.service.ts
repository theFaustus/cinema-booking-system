import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Movie} from "../model/movie";
import {User} from "../model/user";
import {SignUpInfo} from "../model/sigup-info";
import {Observable} from "rxjs";

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  constructor(private http: HttpClient) { }

  getMovies() {
    return this.http.get<Movie[]>('/v1/api/movies', httpOptions);
  }

  deleteMovie(movie: Movie) {
    return this.http.delete('/v1/api/movies/' + movie.id + '/', httpOptions);
  }

  createMovie(movie: Movie): Observable<string> {
    return this.http.post<string>('/v1/api/movies', movie, httpOptions);
  }
}



