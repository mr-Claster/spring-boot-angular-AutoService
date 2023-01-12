import { Component } from '@angular/core';
import {Customer} from "../model/owner";
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Location} from "@angular/common";
import {Order} from "../model/order";
import {GlobalConstants} from "../model/globalConstants";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})
export class CustomerComponent {
  private url = GlobalConstants.apiURL + '/owners';
  customers: Array<Customer> = [];
  orders: Array<Order> = [];

  constructor(
    private http: HttpClient
  ) {}

  reactiveForm = new FormGroup({
    firstName: new FormControl(''),
    lastName: new FormControl('')
  })

  idForm = new FormGroup({
    id: new FormControl(0),
  })

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  save() {
    let firstName = this.reactiveForm.controls.firstName.value;
    let lastName = this.reactiveForm.controls.lastName.value;
    if (!firstName || !lastName) {
      return;
    }
    firstName = firstName.trim();
    lastName = lastName.trim();
    this.createCustomer({firstName, lastName} as Customer)
      .subscribe(customer => {this.customers.push(customer);});
  }

  private createCustomer(customer: Customer): Observable<Customer> {
    return this.http.post<Customer>(this.url, customer, this.httpOptions)
  }

  private updateCustomer(customer: Customer): Observable<Customer> {
    return this.http.put<Customer>(this.url+'/'+customer.id, customer, this.httpOptions);
  }

  getAllOrderById() {
    const id = this.idForm.controls.id.value;
    this.http.get<Array<Order>>(this.url+'/'+id+'/orders')
      .subscribe(responseOrders => this.orders = responseOrders);
  }
}
