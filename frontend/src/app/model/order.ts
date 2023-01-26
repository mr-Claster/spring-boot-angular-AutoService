import {Favor} from './favor';
import {Part} from './part';

export interface Order {
  id: number;
  carId: number;
  problemDescription: string;
  acceptanceDate: string;
  favors: Array<Favor>;
  parts: Array<Part>;
  status: string;
  finalPrice: number;
  endDate: string;
}
