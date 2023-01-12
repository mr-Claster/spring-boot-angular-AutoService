import { Component } from '@angular/core';
import {Favor} from "../model/favor";
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {GlobalConstants} from "../model/globalConstants";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-favors',
  templateUrl: './favors.component.html',
  styleUrls: ['./favors.component.css']
})
export class FavorsComponent {
  private url = GlobalConstants.apiURL + '/favors';
  favors: Array<Favor> = [];

  reactiveForm = new FormGroup({
    id: new FormControl(0),
    favorName: new FormControl(''),
    workerId: new FormControl(0),
    price: new FormControl(0)
  })

  updateStatusForm = new FormGroup({
    id: new FormControl(0),
    status: new FormControl('')
  })

  constructor(
    private http: HttpClient,
  ) {}

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  toNumber(value: string) {
    return Number(value);
  }

  save() {
    const id = this.reactiveForm.controls.id.value;
    const favorName = this.reactiveForm.controls.favorName.value;
    const workerId = this.reactiveForm.controls.workerId.value;
    const price = this.reactiveForm.controls.workerId.value
    if (!favorName || !workerId || !price) {
      return;
    }
    if (!id) {
      this.createFavor({favorName, workerId, price} as Favor)
        .subscribe(favor => {this.favors.push(favor);});
    } else {
      this.updateFavor({id, favorName, workerId} as Favor)
        .subscribe(favor => {this.favors.push(favor);});
    }
  }

  createFavor(favor: Favor): Observable<Favor>{
    return this.http.post<Favor>(this.url, favor, this.httpOptions);
  }

  updateFavor(favor: Favor): Observable<Favor> {
    return this.http.put<Favor>(this.url+'/'+favor.id, favor, this.httpOptions);
  }

  updateStatus() {
    const id = this.updateStatusForm.controls.id.value;
    const status = this.updateStatusForm.controls.status.value;
    this.http.put<Favor>(this.url+'/'+id+'/status?newStatus='+status, this.httpOptions)
      .subscribe(favor => {
        this.favors.push(favor)
      });
  }
}
