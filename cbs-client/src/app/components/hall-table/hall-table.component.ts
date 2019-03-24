import {Component, OnInit, ViewChild} from '@angular/core';
import {MatDialog, MatPaginator, MatSort, MatTableDataSource} from "@angular/material";
import {Hall} from "../../model/hall";
import {HallService} from "../../services/hall.service";
import {TokenStorageService} from "../../auth/token-storage.service";

@Component({
  selector: 'app-hall-table',
  templateUrl: './hall-table.component.html',
  styleUrls: ['./hall-table.component.css']
})
export class HallTableComponent  implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  public authority: string;
  private info: any;
  private roles: string[];


  displayedColumns = ['name', 'description', 'numberOfSeats', 'poster'];
  dataSource: MatTableDataSource<Hall>;
  halls: Hall[];

  constructor(private hallService: HallService, public dialog: MatDialog, private tokenStorage: TokenStorageService) {

    this.dataSource = new MatTableDataSource();
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

}
