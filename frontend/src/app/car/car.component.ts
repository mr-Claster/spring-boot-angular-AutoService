import { Component } from '@angular/core';
import {Car} from "../model/car";
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Location} from "@angular/common";
import {GlobalConstants} from "../model/globalConstants";
import {FormBuilder, FormControl, FormGroup, NgForm} from "@angular/forms";

@Component({
  selector: 'app-car',
  templateUrl: './car.component.html',
  styleUrls: ['./car.component.css']
})
export class CarComponent {
  private url = GlobalConstants.apiURL + '/cars';
  cars: Array<Car> = [];

  reactiveForm = new FormGroup({
    id: new FormControl(0),
    brand: new FormControl(''),
    model: new FormControl(''),
    year: new FormControl(0),
    serialNumber: new FormControl(''),
    ownerId: new FormControl(0),
  })

  constructor(
    private http: HttpClient,
    private location: Location,
    private formBuilder: FormBuilder
  ) {}

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  save() {
    let id = this.reactiveForm.controls.id.value;
    let brand = this.reactiveForm.controls.brand.value;
    let model = this.reactiveForm.controls.model.value;
    let year = this.reactiveForm.controls.year.value;
    let serialNumber = this.reactiveForm.controls.serialNumber.value;
    let ownerId = this.reactiveForm.controls.ownerId.value;
    if(!brand || !model || !year
      || !serialNumber || !ownerId) {
      return;
    }
    if (!this.reactiveForm.value.id) {
      this.createCar({brand, model, year, serialNumber, ownerId} as Car)
        .subscribe(car => {this.cars.push(car);});
    } else {
      this.updateCar({id, brand, model, year, serialNumber, ownerId} as Car)
        .subscribe(car => {this.cars.push(car);});
    }
  }

  private createCar(car: Car): Observable<Car> {
    return this.http.post<Car>(this.url, car, this.httpOptions);
  }

  private updateCar(car: Car): Observable<Car> {
    return this.http.put<Car>(this.url+'/'+car.id, car, this.httpOptions);
  }
}
