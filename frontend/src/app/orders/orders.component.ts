import { Component } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Order} from "../model/order";
import {Observable} from "rxjs";
import {Part} from "../model/part";
import {Favor} from "../model/favor";
import {GlobalConstants} from "../model/globalConstants";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent {
  private url = GlobalConstants.apiURL + '/orders';
  orders: Array<Order>=[];
  totalPrice?: number;

  reactiveForm = new FormGroup({
    id: new FormControl(0),
    carId: new FormControl(0),
    problemDescription: new FormControl(''),
    acceptanceDate: new FormControl(''),
  })

  statusOrderForm = new FormGroup({
    id: new FormControl(0),
    status: new FormControl('')
  })

  orderForm = new FormGroup({
    id: new FormControl(0),
    name: new FormControl(''),
    price: new FormControl(0),
  })

  favorForm = new FormGroup({
    orderId: new FormControl(0),
    favorName: new FormControl(''),
    workerId: new FormControl(0),
    price: new FormControl(0),
  })

  orderPriceForm = new FormGroup({
    id: new FormControl(0),
    bonus: new FormControl(0),
  })

  constructor(private http: HttpClient){}

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  save() {
    let id = this.reactiveForm.controls.id.value;
    let carId = this.reactiveForm.controls.carId.value
    let problemDescription = this.reactiveForm.controls.problemDescription.value;
    let acceptanceDate = this.reactiveForm.controls.acceptanceDate.value;
    if (!problemDescription || !acceptanceDate || !carId) {
      return;
    }
    if (!id) {
      return this.createOrder({carId, problemDescription, acceptanceDate} as Order)
        .subscribe(order => this.orders.push(order));
    } else {
      return  this.updateOrder({id, carId, problemDescription, acceptanceDate} as Order)
        .subscribe(order => this.orders.push(order));
    }
  }

  updateOrder(order: Order): Observable<Order> {
    return this.http.put<Order>(this.url+'/'+order.id, order, this.httpOptions);
  }

  createOrder(order: Order): Observable<Order>  {
    return this.http.post<Order>(this.url, order, this.httpOptions);
  }

  updateStatus() {
    let id = this.statusOrderForm.controls.id.value;
    let status = this.statusOrderForm.controls.status.value;
    this.http.put<Order>(this.url+'/'+id+'/status?newStatus='+status, this.httpOptions)
      .subscribe(order => this.orders.push(order));
  }

  addPart() {
    let id = this.orderForm.controls.id.value;
    let name = this.orderForm.controls.name.value;
    let price = this.orderForm.controls.price.value;
    if (!id || !name || !price) {
      return;
    }
    return this.http.post<Order>(this.url+'/'+id+'/goods', {name, price} as Part, this.httpOptions)
      .subscribe(order => this.orders.push(order));
  }

  addFavor() {
    let id = this.favorForm.controls.orderId.value;
    let favorName = this.favorForm.controls.favorName.value;
    let workerId = this.favorForm.controls.workerId.value;
    let price = this.favorForm.controls.price.value;
    if (!id || !favorName || !workerId || !price) {
      return;
    }
    return this.http.post<Order>(this.url+'/'+id+'/favors', {favorName, workerId, price} as Favor, this.httpOptions)
      .subscribe(order => this.orders.push(order));
  }

  getTotalPrice() {
    let id = this.orderPriceForm.controls.id.value;
    let bonus = this.orderPriceForm.controls.bonus.value;
    if (!id) {
      return;
    }
    if (bonus == null) {return;}
    this.http.get<number>(this.url+'/'+id+'/price?bonus='+bonus)
      .subscribe(price => this.totalPrice = price);
  }
}
