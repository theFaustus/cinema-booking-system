import {AfterViewInit, ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import {DataSource} from '@angular/cdk/collections';
import {MovieService} from '../../services/movie.service';
import {Movie} from '../../model/movie';
import {MatDialog, MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {MovieSessionService} from '../../services/movie-session.service';
import {MovieSession} from '../../model/movie-session';
import {MovieSessionModalComponent} from '../movie-session-modal/movie-session-modal.component';
import {TokenStorageService} from "../../auth/token-storage.service";
import {User} from "../../model/user";
import {AddUserModalComponent} from "../add-user-modal/add-user-modal.component";
import {NotifierService} from "angular-notifier";
import {AddMovieModalComponent} from "../add-movie-modal/add-movie-modal.component";

@Component({
  selector: 'app-movie-table',
  templateUrl: './movie-table.component.html',
  styleUrls: ['./movie-table.component.css']
})
export class MovieTableComponent implements OnInit, AfterViewInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  private roles: string[];
  public authority: string;
  private info: any;
  private readonly notifier: NotifierService;

  displayedColumns = ['poster', 'name', 'description', 'imdbRating', 'duration', 'directors', 'actors', 'sessions'];
  dataSource: MatTableDataSource<Movie>;
  movies: Movie[];
  movieSessions: MovieSession[];


  constructor(notifierService: NotifierService, private changeDetectorRefs: ChangeDetectorRef, private movieService: MovieService, private movieSessionService: MovieSessionService, public dialog: MatDialog, private tokenStorage: TokenStorageService) {

    this.dataSource = new MatTableDataSource();
    this.notifier = notifierService;
  }

  ngOnInit() {
    this.movieService.getMovies().subscribe(data => {
      this.dataSource.data = data;
      console.log(data);
    });
    this.info = {
      token: this.tokenStorage.getToken(),
      username: this.tokenStorage.getUsername(),
      authorities: this.tokenStorage.getAuthorities()
    };
    if (this.tokenStorage.getToken()) {
      this.roles = this.tokenStorage.getAuthorities();
      this.roles.every(role => {
        if (role === 'ROLE_ADMIN') {
          this.authority = 'admin';
          return false;
        }
        this.authority = 'user';
        return true;
      });
    }
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
        width: '700px',
      });
    });
  }

  deleteMovie(movie: Movie) {
    this.movieSessionService.deleteMovieSessionsByMovieId(movie).subscribe(data => {
    });
    this.notifier.notify('success', 'All movie sessions for [' + movie.name + '] deleted!');
    this.movieService.deleteMovie(movie).subscribe(data => {
      this.notifier.notify('success', 'Movie [' + movie.name + '] deleted!');
      this.redraw();
    });

  }

  openAddMovieModal() {
    this.dialog.open(AddMovieModalComponent, {
      data: {
        userTableRef: this
      },
      width: "700px",
    });
    this.redraw();
  }

  redraw() {
    this.dataSource = new MatTableDataSource();
    this.dataSource.data.concat([]);
    this.movieService.getMovies().subscribe(data => {
      this.dataSource.data = data;
      console.log(data);
    });
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.changeDetectorRefs.detectChanges();
    this.dataSource._updateChangeSubscription();
    this.paginator._changePageSize(this.paginator.pageSize);
  }

}
