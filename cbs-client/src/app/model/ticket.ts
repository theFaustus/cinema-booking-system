import {MovieSession} from "./movie-session";
import {Seat} from "./seat";

export class Ticket {
  ticketStatus : string;
  ticketType : string;
  movieSession : MovieSession;
  bookedSeat : Seat;


  constructor(ticketStatus: string, ticketType: string, movieSession: MovieSession, bookedSeat: Seat) {
    this.ticketStatus = ticketStatus;
    this.ticketType = ticketType;
    this.movieSession = movieSession;
    this.bookedSeat = bookedSeat;
  }
}
