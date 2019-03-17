import {Component, OnInit, ViewChild} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import {DataSource} from '@angular/cdk/collections';
import {MovieService} from "../../services/movie.service";
import {Movie} from "../../model/movie";
import {MatDialog, MatPaginator, MatSort, MatTableDataSource} from "@angular/material";
import {MovieSessionService} from "../../services/movie-session.service";
import {MovieSession} from "../../model/movie-session";
import {MovieSessionModalComponent} from "../movie-session-modal/movie-session-modal.component";

@Component({
  selector: 'app-movie-table',
  templateUrl: './movie-table.component.html',
  styleUrls: ['./movie-table.component.css']
})
export class MovieTableComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;


  displayedColumns = ['poster', 'name', 'description', 'imdbRating', 'duration', 'directors', 'actors', 'sessions'];
  dataSource: MatTableDataSource<Movie>;
  movies: Movie[];
  movieSessions: MovieSession[];


  constructor(private movieService: MovieService, private movieSessionService: MovieSessionService, public dialog: MatDialog) {

    this.dataSource = new MatTableDataSource();
  }

  ngOnInit() {
    this.movieService.getMovies().subscribe(data => {
      this.dataSource.data = data;
      console.log(data);
    });
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }

  viewSessions(movie: Movie) {
    console.log(movie.id);
    this.movieSessionService.getMovieSessionsByMovieId(movie.id).subscribe(data => {
      console.log(data);
      this.movieSessions = data;
      console.log(this.movieSessions);
      this.dialog.open(MovieSessionModalComponent, {
        data: {
          movieId: movie.id,
          movieName: movie.name,
          movieImagePath: movie.imagePath,
          movieDescription: movie.description
        },
        width: "700px",
      });
    });
  }

}
