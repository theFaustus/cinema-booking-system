import {Measurement} from "./measurement";

export class ProcessUptime {
  name: string;
  description: string;
  baseUnit: string;
  measurements: Measurement[];


  constructor(name: string, description: string, baseUnit: string, measurements: Measurement[]) {
    this.name = name;
    this.description = description;
    this.baseUnit = baseUnit;
    this.measurements = measurements;
  }
}
