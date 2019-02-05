import {MovieSession} from "./movie-session";

export class Movie {
  id: number
  name: string;
  description: string;
  imdbRating: string;
  movieDuration: number;
  movieSession: MovieSession;
  directors: Array<string>;
  actors: Array<string>;
  imagePath: string;


  constructor(id: number, name: string, description: string, imdbRating: string, movieDuration: number, movieSession: MovieSession, directors: Array<string>, actors: Array<string>, imagePath: string) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.imdbRating = imdbRating;
    this.movieDuration = movieDuration;
    this.movieSession = movieSession;
    this.directors = directors;
    this.actors = actors;
    this.imagePath = imagePath;
  }
}
