import { Component } from '@angular/core';
import {Observable} from "rxjs";
import {Order} from "../model/order";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Worker} from "../model/worker";
import {GlobalConstants} from "../model/globalConstants";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-workers',
  templateUrl: './workers.component.html',
  styleUrls: ['./workers.component.css']
})
export class WorkersComponent {
  private url = GlobalConstants.apiURL + '/workers';
  workers: Array<Worker>=[]
  orders: Array<Order>=[]
  salary?: number;

  workerForm = new FormGroup({
    id: new FormControl(0),
    firstName: new FormControl(''),
    lastName: new FormControl(''),
  })

  workerIdForm = new FormGroup({
    id: new FormControl(0),
  })

  salaryForm = new FormGroup({
    workerId: new FormControl(0),
    orderId: new FormControl(0),
  })

  constructor(private http: HttpClient){}

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' ,
    'Access-Control-Allow-Origin': '*'})
  };

  toNumber(value: string) {
    return Number(value);
  }

  save() {
    let id = this.workerForm.controls.id.value;
    let firstName = this.workerForm.controls.firstName.value;
    let lastName = this.workerForm.controls.lastName.value;
    if (!firstName || !lastName) {
      return;
    }
    firstName = firstName.trim();
    lastName = lastName.trim();
    if (!id) {
      this.createWorker({firstName, lastName} as Worker)
        .subscribe(worker => this.workers.push(worker));
    } else {
      this.updateWorker({id, firstName, lastName} as Worker)
        .subscribe(worker => this.workers.push(worker));
    }
  }

  private createWorker(worker: Worker): Observable<Worker> {
    return this.http.post<Worker>(this.url, worker, this.httpOptions).pipe();
  }

  private updateWorker(worker: Worker): Observable<Worker> {
    return this.http.put<Worker>(this.url+'/'+worker.id, worker, this.httpOptions);
  }

  getOrder() {
    let id = this.workerIdForm.controls.id.value;
    if (!id) {return;}
    this.http.get<Array<Order>>(this.url+'/'+id+'/orders')
      .subscribe(orders => this.orders = orders);
  }

  getSalary() {
    let workerId = this.salaryForm.controls.workerId.value;
    let orderId = this.salaryForm.controls.orderId.value;
    if (!workerId) {return;}
    this.http.get<number>(this.url+'/'+workerId+'/orders/'+orderId+'/salary')
      .subscribe(salary => this.salary = salary);
  }
}
