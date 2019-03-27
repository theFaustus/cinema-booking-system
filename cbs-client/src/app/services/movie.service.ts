import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Movie} from "../model/movie";
import {User} from "../model/user";

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  constructor(private http: HttpClient) { }

  getMovies() {
    return this.http.get<Movie[]>('/server/v1/api/movies', httpOptions);
  }

  deleteMovie(movie: Movie) {
    return this.http.delete('/server/v1/api/movies/' + movie.id + '/', httpOptions);
  }
}



