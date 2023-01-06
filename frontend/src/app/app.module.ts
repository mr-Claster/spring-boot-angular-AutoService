import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatDividerModule} from "@angular/material/divider";
import { OrdersComponent } from './orders/orders.component';
import { CustomerComponent } from './customer/customer.component';
import { FavorsComponent } from './favors/favors.component';
import { WorkersComponent } from './workers/workers.component';
import {PartsComponent} from "./parts/parts.component";
import { CarComponent } from './car/car.component';

@NgModule({
  declarations: [
    AppComponent,
    OrdersComponent,
    CustomerComponent,
    FavorsComponent,
    PartsComponent,
    WorkersComponent,
    CarComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatIconModule,
    FormsModule,
    ReactiveFormsModule,
    MatDividerModule,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
