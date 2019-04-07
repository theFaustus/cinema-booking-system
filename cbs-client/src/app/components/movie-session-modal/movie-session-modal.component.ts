import {AfterViewInit, Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef, MatPaginator, MatSort, MatTableDataSource} from "@angular/material";
import {MovieSession} from "../../model/movie-session";
import {MovieService} from "../../services/movie.service";
import {MovieSessionService} from "../../services/movie-session.service";
import {HallService} from "../../services/hall.service";
import {Movie} from "../../model/movie";
import {SeatBookingModalComponent} from "../seat-booking-modal/seat-booking-modal.component";
import {AddMovieModalComponent} from "../add-movie-modal/add-movie-modal.component";
import {NotifierService} from "angular-notifier";
import {AddMovieSessionModalComponent} from "../add-movie-session-modal/add-movie-session-modal.component";
import {TokenStorageService} from "../../auth/token-storage.service";

@Component({
  selector: 'app-movie-session-modal',
  templateUrl: './movie-session-modal.component.html',
  styleUrls: ['./movie-session-modal.component.css']
})
export class MovieSessionModalComponent implements OnInit, AfterViewInit {


  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  private roles: string[];
  public authority: string;
  private info: any;

  displayedColumns = ['hallName', 'showTime', 'book'];
  dataSource: MatTableDataSource<MovieSession>;
  movieSessions: MovieSession[];

  private readonly notifier: NotifierService;



  constructor(private dialogRef: MatDialogRef<MovieSessionModalComponent>,
              @Inject(MAT_DIALOG_DATA) public matData: any,
              private movieService: MovieService,
              private movieSessionService: MovieSessionService,
              private hallService: HallService,
              public dialog: MatDialog,
              notifierService: NotifierService,
              private tokenStorage: TokenStorageService) {

    this.dataSource = new MatTableDataSource();
    this.notifier = notifierService;

  }


  public closeDialog() {
    this.dialogRef.close();
  }

  ngOnInit() {
    this.movieSessionService.getMovieSessionsByMovieId(this.matData.movieId).subscribe(data => {
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

  bookMovieSession(movieSession: MovieSession) {
    console.log(movieSession.movieSessionId);
    this.hallService.getSeatsByHallId(movieSession.hallId).subscribe(data => {
      console.log(data);
      this.dialog.open(SeatBookingModalComponent, {
        data: {
          hallId: movieSession.hallId,
          movieName: movieSession.hallName,
          showTime: movieSession.showTime,
          movieSessionId: movieSession.movieSessionId
        },
        width: "700px",
      });
    });
  }

  deleteMovieSession(movieSession: MovieSession) {
    this.movieSessionService.deleteMovieSessionById(movieSession).subscribe(data => {
      console.log(data);
      this.redraw();
        this.notifier.notify('success', 'Movie session [' + movieSession.movieSessionId + '] deleted!');
      },
      error => {
        this.notifier.notify('error', 'Movie session [' + movieSession.movieSessionId + '] not deleted! There are booked tickets!');
      });
  }

  openAddMovieSessionModal() {
    this.dialog.open(AddMovieSessionModalComponent, {
      data: {
        movieSessionTableRef: this,
        movie: this.matData.movie,
      },
      width: "700px",
    });
    this.redraw();
  }

  redraw() {
    this.dataSource = new MatTableDataSource();
    this.dataSource.data.concat([]);
    this.movieSessionService.getMovieSessionsByMovieId(this.matData.movieId).subscribe(data => {
      this.dataSource.data = data;
      console.log(data);
    });
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.dataSource._updateChangeSubscription();
    this.paginator._changePageSize(this.paginator.pageSize);
  }

}
