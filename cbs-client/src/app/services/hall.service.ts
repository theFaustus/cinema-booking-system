import { Injectable } from '@angular/core';
import {MovieSession} from "../model/movie-session";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Seat} from "../model/seat";
import {Hall} from "../model/hall";

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};


@Injectable({
  providedIn: 'root'
})
export class HallService {

  constructor(private http: HttpClient) { }


  getSeatsByHallId(hallId: number) {
    return this.http.get<Seat[]>('/server/v1/api/halls/' + hallId + "/seats", httpOptions);
  }

  getHalls() {
    return this.http.get<Hall[]>('/server/v1/api/halls', httpOptions);
  }
}
