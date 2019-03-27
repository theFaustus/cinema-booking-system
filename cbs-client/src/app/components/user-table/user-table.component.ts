import {AfterViewInit, ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import {MatDialog, MatPaginator, MatSort, MatTableDataSource} from "@angular/material";
import {TokenStorageService} from "../../auth/token-storage.service";
import {User} from "../../model/user";
import {UserService} from "../../services/user.service";
import {NotifierService} from "angular-notifier";
import {Seat} from "../../model/seat";
import {SeatBookingConfirmModalComponent} from "../seat-booking-confirm-modal/seat-booking-confirm-modal.component";
import {AddUserModalComponent} from "../add-user-modal/add-user-modal.component";

@Component({
  selector: 'app-user-table',
  templateUrl: './user-table.component.html',
  styleUrls: ['./user-table.component.css']
})
export class UserTableComponent implements OnInit, AfterViewInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  private roles: string[];
  public authority: string;
  private info: any;

  private readonly notifier: NotifierService;


  displayedColumns = ['username', 'firstName', 'lastName', 'telephone', 'email', 'role', 'enabled', 'activity'];
  dataSource: MatTableDataSource<User>;
  users: User[];


  constructor(private changeDetectorRefs: ChangeDetectorRef, notifierService: NotifierService, private userService: UserService, public dialog: MatDialog, private tokenStorage: TokenStorageService) {
    this.notifier = notifierService;
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
    this.notifier.notify('success', 'User [' + user.username + '] enabled!');
    this.redraw();
  }

  disableUser(user: User) {
    this.userService.disableUser(user).subscribe(data => {
    });
    this.notifier.notify('success', 'User [' + user.username + ']  disabled!');
    this.redraw();
  }

  deleteUser(user: User) {
    this.userService.deleteUser(user).subscribe(data => {
    });
    this.notifier.notify('success', 'User [' + user.username + '] deleted!');
    this.redraw();
  }

  openAddUserModal() {
    this.dialog.open(AddUserModalComponent, {
      data: {
        userTableRef: this
      },
      width: "700px",
    });
  }

  redraw() {
    this.dataSource = new MatTableDataSource();
    this.dataSource.data.concat([]);
    this.userService.getUsers().subscribe(data => {
      this.dataSource.data = data;
      console.log(data);
      this.changeDetectorRefs.detectChanges();
    });
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.changeDetectorRefs.detectChanges();
    this.dataSource._updateChangeSubscription();
    this.paginator._changePageSize(this.paginator.pageSize);
  }
}
