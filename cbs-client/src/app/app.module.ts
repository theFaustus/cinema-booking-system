import {BrowserModule} from '@angular/platform-browser';

import {BrowserAnimationsModule, NoopAnimationsModule} from '@angular/platform-browser/animations';
import {AppComponent} from './app.component';
import {NgModule} from '@angular/core';
import {HttpClientModule} from "@angular/common/http";
import {HttpModule} from "@angular/http";
import {FacebookModule} from "ngx-facebook";
import {AppRoutingModule} from './app-routing.module';
import {FormsModule} from "@angular/forms";
import {LoginComponent} from './components/login/login.component';
import {RegisterComponent} from './components/register/register.component';
import {HomeComponent} from './components/home/home.component';
import {UserComponent} from './components/user/user.component';
import {AdminComponent} from './components/admin/admin.component';
import {httpInterceptorProviders} from './auth/auth-interceptor';
import {MovieTableComponent} from './components/movie-table/movie-table.component';
import { ReactiveFormsModule }         from '@angular/forms';
import { RouterModule } from '@angular/router';

import 'hammerjs';


import {
  MatTabsModule,
  MatToolbarModule,
  MatCardModule,
  MatSelectModule,
  MatInputModule,
  MatIconModule,
  MatButtonModule,
  MatSnackBarModule,
  MatTooltipModule,
  MatDialogModule,
  MatProgressSpinnerModule,
  MatProgressBarModule,
  MatFormFieldModule,
  MatTableModule,
  MatPaginatorModule,
  MatSortModule,
  MatCheckboxModule,
  MatSidenavModule,
  MatListModule
} from '@angular/material';
import {FlexLayoutModule} from "@angular/flex-layout";
import { MovieSessionModalComponent } from './components/movie-session-modal/movie-session-modal.component';



@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    UserComponent,
    AdminComponent,
    MovieTableComponent,
    MovieSessionModalComponent
  ],
  imports: [
    HttpModule,
    BrowserAnimationsModule,
    NoopAnimationsModule,
    HttpClientModule,
    BrowserModule,
    MatTabsModule,
    MatToolbarModule,
    MatCardModule,
    MatSelectModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatSnackBarModule,
    MatTooltipModule,
    FormsModule,
    MatDialogModule,
    MatProgressSpinnerModule,
    MatProgressBarModule,
    BrowserModule,
    FormsModule,
    FacebookModule.forRoot(),
    AppRoutingModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatCheckboxModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatSidenavModule,
    MatListModule,
    FlexLayoutModule,
    RouterModule

  ],
  exports: [],
  entryComponents: [MovieSessionModalComponent],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule {
}
