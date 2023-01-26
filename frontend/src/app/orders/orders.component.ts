import { Component } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Order} from '../model/order';
import {Observable} from 'rxjs';
import {Part} from '../model/part';
import {Favor} from '../model/favor';
import {GlobalConstants} from '../model/globalConstants';
import {FormControl, FormGroup} from '@angular/forms';

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
    const carId = this.reactiveForm.controls.carId.value
    const problemDescription = this.reactiveForm.controls.problemDescription.value;
    const acceptanceDate = this.reactiveForm.controls.acceptanceDate.value;
    if (!problemDescription || !acceptanceDate || !carId) {
      return;
    }
    return this.createOrder({carId, problemDescription, acceptanceDate} as Order)
      .subscribe(order => this.orders.push(order));
  }

  updateOrder(order: Order): Observable<Order> {
    return this.http.put<Order>(this.url+'/'+order.id, order, this.httpOptions);
  }

  createOrder(order: Order): Observable<Order>  {
    return this.http.post<Order>(this.url, order, this.httpOptions);
  }

  updateStatus() {
    const id = this.statusOrderForm.controls.id.value;
    const status = this.statusOrderForm.controls.status.value;
    this.http.put<Order>(this.url+'/'+id+'/status?newStatus='+status, this.httpOptions)
      .subscribe(order => this.orders.push(order));
  }

  addPart() {
    const id = this.orderForm.controls.id.value;
    const name = this.orderForm.controls.name.value;
    const price = this.orderForm.controls.price.value;
    if (!id || !name || !price) {
      return;
    }
    return this.http.post<Order>(this.url+'/'+id+'/goods', {name, price} as Part, this.httpOptions)
      .subscribe(order => this.orders.push(order));
  }

  addFavor() {
    const id = this.favorForm.controls.orderId.value;
    const favorName = this.favorForm.controls.favorName.value;
    const workerId = this.favorForm.controls.workerId.value;
    const price = this.favorForm.controls.price.value;
    if (!id || !favorName || !workerId || !price) {
      return;
    }
    return this.http.post<Order>(this.url+'/'+id+'/favors', {favorName, workerId, price} as Favor, this.httpOptions)
      .subscribe(order => this.orders.push(order));
  }

  getTotalPrice() {
    const id = this.orderPriceForm.controls.id.value;
    const bonus = this.orderPriceForm.controls.bonus.value;
    if (!id) {
      return;
    }
    if (bonus == null) {return;}
    this.http.get<number>(this.url+'/'+id+'/price?bonus='+bonus)
      .subscribe(price => this.totalPrice = price);
  }
}
