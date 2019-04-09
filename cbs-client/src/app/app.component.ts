import {Component, OnInit} from '@angular/core';
import {TokenStorageService} from './auth/token-storage.service';
import {ActuatorService} from "./services/actuator.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  private roles: string[];
  public authority: string;
  private info: any;


  constructor(private router: Router, private tokenStorage: TokenStorageService) { }


  logout() {
    this.tokenStorage.signOut();
    this.router.navigate([('auth/login')]);
  }

  ngOnInit() {
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

    console.log(this.authority);
  }
}
