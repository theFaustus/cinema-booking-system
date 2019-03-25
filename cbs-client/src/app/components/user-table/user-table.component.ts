import {Component, OnInit, ViewChild} from '@angular/core';
import {MatDialog, MatPaginator, MatSort, MatTableDataSource} from "@angular/material";
import {TokenStorageService} from "../../auth/token-storage.service";
import {User} from "../../model/user";
import {UserService} from "../../services/user.service";
import {BookingNotificationModalComponent} from "../booking-notification-modal/booking-notification-modal.component";

@Component({
  selector: 'app-user-table',
  templateUrl: './user-table.component.html',
  styleUrls: ['./user-table.component.css']
})
export class UserTableComponent  implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  private roles: string[];
  public authority: string;
  private info: any;

  displayedColumns = ['username', 'firstName', 'lastName', 'telephone', 'email', 'role', 'enabled', 'activity'];
  dataSource: MatTableDataSource<User>;
  users: User[];


  constructor(private userService: UserService, public dialog: MatDialog, private tokenStorage: TokenStorageService) {

    this.dataSource = new MatTableDataSource();
  }

  ngOnInit() {
    this.userService.getUsers().subscribe(data => {
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

  enableUser(user: User) {
    this.userService.enableUser(user).subscribe(data => {
    });
  }

  disableUser(user: User) {
    this.userService.disableUser(user).subscribe(data => {
    });
  }

  deleteUser(user: User) {

  }
}
