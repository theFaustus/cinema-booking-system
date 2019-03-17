import {Hall} from "./hall";

export class Seat {
  id: number;
  price: number;
  seatNumber: string;
  seatStatus: string;


  constructor(id: number, price: number, seatNumber: string, seatStatus: string) {
    this.id = id;
    this.price = price;
    this.seatNumber = seatNumber;
    this.seatStatus = seatStatus;
  }
}
