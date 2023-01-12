import { Component } from '@angular/core';
import {Part} from "../model/part";
import {Observable} from "rxjs";
import { Location } from '@angular/common';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {GlobalConstants} from "../model/globalConstants";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-parts',
  templateUrl: './parts.component.html',
  styleUrls: ['./parts.component.css']
})
export class PartsComponent {
  parts: Array<Part> =[]

  partForm = new FormGroup({
    id: new FormControl(0),
    name: new FormControl(''),
    price: new FormControl(0),
  })

  private url = GlobalConstants.apiURL + '/goods';

  constructor(
    private http: HttpClient,
    private location: Location
  ) {}

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  save(): void {
    const id = this.partForm.controls.id.value;
    let name = this.partForm.controls.name.value;
    const price = this.partForm.controls.price.value;
    if (!name || !price) {
      return;
    }
    name = name.trim();
    if (!id) {
      this.createPart({name, price} as Part)
        .subscribe(part => {this.parts.push(part)});
    } else {
      this.updatePart({id, name, price} as Part)
        .subscribe(part => {this.parts.push(part)});
    }
  }

  createPart(part: Part): Observable<Part> {
    return this.http.post<Part>(this.url, part, this.httpOptions).pipe();
  }

  updatePart(part: Part): Observable<Part> {
    return this.http.put<Part>(this.url+'/'+part.id, part, this.httpOptions).pipe();
  }
}
