export class MovieSession {
  movieSessionId: number;
  hallId: number;
  hallName: string;
  movieId: string;
  showTime: Date;


  constructor(movieSessionId: number, hallId: number, hallName: string, showTime: Date, movieId : string) {
    this.movieSessionId = movieSessionId;
    this.hallId = hallId;
    this.hallName = hallName;
    this.showTime = showTime;
    this.movieId = movieId;
  }
}
