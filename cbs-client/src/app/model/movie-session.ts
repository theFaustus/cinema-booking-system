export class MovieSession {
  id: number;
  hallId: number;
  hallName: string;
  showTime: Date;


  constructor(id: number, hallId: number, hallName: string, showTime: Date) {
    this.id = id;
    this.hallId = hallId;
    this.hallName = hallName;
    this.showTime = showTime;
  }
}
