import { NgModule } from '@angular/core';
import {OrdersComponent} from "./orders/orders.component";
import {RouterModule, Routes} from '@angular/router';
import {CustomerComponent} from "./customer/customer.component";
import {FavorsComponent} from "./favors/favors.component";
import {PartsComponent} from "./parts/parts.component";
import {WorkersComponent} from "./workers/workers.component";
import {CarComponent} from "./car/car.component";

const routes: Routes = [{
    path: 'auto_service',
    children: [
      {
        path: 'orders',
        component: OrdersComponent
      },
      {
        path: 'customers',
        component: CustomerComponent
      },
      {
        path: 'favors',
        component: FavorsComponent
      },
      {
        path: 'parts',
        component: PartsComponent
      },
      {
        path: 'workers',
        component: WorkersComponent
      },
      {
        path: 'cars',
        component: CarComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
