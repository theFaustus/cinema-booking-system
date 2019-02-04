import { Component, OnInit } from '@angular/core';
import {UserService} from "../../services/user.service";
import {MovieService} from "../../services/movie.service";
import {Movie} from "../../model/movie";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  board: Movie[];
  errorMessage: string;

  constructor(private userService: UserService, private movieService: MovieService) { }

  ngOnInit() {
    this.movieService.getMovies().subscribe(
      data => {
        this.board = data;
      },
      error => {
        this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`;
      }
    );
  }
}
