import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef, MatPaginator, MatSort, MatTableDataSource} from "@angular/material";
import {MovieSession} from "../../model/movie-session";
import {MovieService} from "../../services/movie.service";
import {MovieSessionService} from "../../services/movie-session.service";

@Component({
  selector: 'app-movie-session-modal',
  templateUrl: './movie-session-modal.component.html',
  styleUrls: ['./movie-session-modal.component.css']
})
export class MovieSessionModalComponent implements OnInit {


  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;


  displayedColumns = ['hallName', 'showTime', 'book'];
  dataSource: MatTableDataSource<MovieSession>;
  movieSessions: MovieSession[];


  constructor(private dialogRef: MatDialogRef<MovieSessionModalComponent>,
              @Inject(MAT_DIALOG_DATA) public matData : any,
              private movieService: MovieService,
              private movieSessionService: MovieSessionService) {

    this.dataSource = new MatTableDataSource();

  }


  public closeDialog(){
    this.dialogRef.close();
  }

  ngOnInit() {
    this.movieSessionService.getMovieSessionsByMovieId(this.matData.movieId).subscribe(data => {
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

}
