import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Info} from "../model/actuator/info";
import {Health} from "../model/actuator/health";
import {HttpServerRequest} from "../model/actuator/http-server-request";


const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};


@Injectable({
  providedIn: 'root'
})
export class ActuatorService {

  constructor(private http: HttpClient) { }


  getInfo() {
    return this.http.get<Info>('/actuator/info', httpOptions);
  }

  getHealth() {
    return this.http.get<Health>('/actuator/health', httpOptions);
  }

  getHttpServerRequestMetrics() {
    return this.http.get<HttpServerRequest>('/actuator/metrics/http.server.requests', httpOptions);
  }

  getHttpProcessUpTimeMetrics() {
    return this.http.get<HttpServerRequest>('/actuator/metrics/process.uptime', httpOptions);
  }

}
