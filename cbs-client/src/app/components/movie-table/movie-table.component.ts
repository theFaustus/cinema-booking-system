import {Component, OnInit, ViewChild} from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import {DataSource} from '@angular/cdk/collections';
import {MovieService} from "../../services/movie.service";
import {Movie} from "../../model/movie";
import {MatPaginator, MatSort, MatTableDataSource} from "@angular/material";

@Component({
  selector: 'app-movie-table',
  templateUrl: './movie-table.component.html',
  styleUrls: ['./movie-table.component.css']
})
export class MovieTableComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;


  displayedColumns = ['poster', 'name', 'description', 'imdbRating', 'duration','directors', 'actors', 'sessions'];
  dataSource: MatTableDataSource<Movie>;
  movies: Movie[];


  constructor(private movieService: MovieService) {

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

}
