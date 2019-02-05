import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Movie} from "../model/movie";
import {MovieSession} from "../model/movie-session";

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class MovieSessionService {

  constructor(private http: HttpClient) { }

  getMovieSessionsByMovieId(movieId: number) {
    return this.http.get<MovieSession[]>('/server/v1/api/movies/' + movieId + "/sessions", httpOptions);
  }
}
