export class BookedMovie {
  movieSessionId: number;
  seatNumber: string;
  username: string;


  constructor(movieSessionId: number, seatNumber: string, username: string) {
    this.movieSessionId = movieSessionId;
    this.seatNumber = seatNumber;
    this.username = username;
  }
}
