export class Hall {
  id: number;
  name: string;
  description: string;
  numberOfSeats: number;


  constructor(id: number, name: string, description: string, numberOfSeats: number) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.numberOfSeats = numberOfSeats;
  }
}
