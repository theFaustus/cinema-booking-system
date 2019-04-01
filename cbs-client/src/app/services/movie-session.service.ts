import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Movie} from "../model/movie";
import {MovieSession} from "../model/movie-session";
import {AuthLoginInfo} from "../model/login-info";
import {Observable} from "rxjs";
import {BookedMovie} from "../model/booked-movie";
import {Ticket} from "../model/ticket";

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

  bookMovie(bookedMovie: BookedMovie): Observable<Ticket> {
    return this.http.post<Ticket>('/server/v1/api/movie-sessions/' + bookedMovie.movieSessionId + "/booked-movies", bookedMovie, httpOptions);
  }

  deleteMovieSession(movieSession: MovieSession) {
    return this.http.delete('/server/v1/api/movie-sessions/' + movieSession.movieSessionId + '/', httpOptions);
  }

  deleteMovieSessionsByMovieId(movie: Movie) {
    return this.http.delete('/server/v1/api/movies/' + movie.id + '/sessions', httpOptions);
  }

  deleteMovieSessionById(movieSession: MovieSession) {
    return this.http.delete('/server/v1/api/movie-sessions/' + movieSession.movieSessionId + '/', httpOptions);
  }
}
