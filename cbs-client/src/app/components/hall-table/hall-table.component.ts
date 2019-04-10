import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatDialog, MatPaginator, MatSort, MatTableDataSource} from "@angular/material";
import {Hall} from "../../model/hall";
import {HallService} from "../../services/hall.service";
import {TokenStorageService} from "../../auth/token-storage.service";
import {Movie} from "../../model/movie";
import {AddMovieModalComponent} from "../add-movie-modal/add-movie-modal.component";
import {NotifierService} from "angular-notifier";
import {AddHallModalComponent} from "../add-hall-modal/add-hall-modal.component";

@Component({
  selector: 'app-hall-table',
  templateUrl: './hall-table.component.html',
  styleUrls: ['./hall-table.component.css']
})
export class HallTableComponent  implements OnInit, AfterViewInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  public authority: string;
  private info: any;
  private roles: string[];

  private readonly notifier: NotifierService;

  displayedColumns = ['name', 'description', 'numberOfSeats', 'poster', 'delete'];
  dataSource: MatTableDataSource<Hall>;
  halls: Hall[];

  constructor(notifierService: NotifierService, private hallService: HallService, public dialog: MatDialog, private tokenStorage: TokenStorageService) {

    this.dataSource = new MatTableDataSource();
    this.notifier = notifierService;
  }

  ngOnInit() {
    this.hallService.getHalls().subscribe(data => {
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

  deleteHall(hall: Hall) {
    this.hallService.deleteHall(hall).subscribe(data => {
        this.notifier.notify('success', 'Hall [' + hall.name + '] deleted!');
        this.redraw();
      },
      error => {
        this.notifier.notify('error', 'Hall [' + hall.name + '] not deleted! There are booked tickets!');
      });

  }

  openAddHallModal() {
    this.dialog.open(AddHallModalComponent, {
      data: {
        hallTableRef: this
      },
      width: "700px",
    });
    this.redraw();
  }

  redraw() {
    this.dataSource = new MatTableDataSource();
    this.dataSource.data.concat([]);
    this.hallService.getHalls().subscribe(data => {
      this.dataSource.data = data;
      console.log(data);
    });
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.dataSource._updateChangeSubscription();
    this.paginator._changePageSize(this.paginator.pageSize);
  }

}
