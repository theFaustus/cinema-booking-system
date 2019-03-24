export class Hall {
  id: number;
  name: string;
  description: string;
  numberOfSeats: number;
  imagePath: string;


  constructor(id: number, name: string, description: string, numberOfSeats: number, imagePath: string) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.numberOfSeats = numberOfSeats;
    this.imagePath = imagePath;
  }
}
