import {JavaVersion} from "./java-version";

export class App {
  name: string;
  description: string;
  version: string;
  encoding: string;
  java: JavaVersion;


  constructor(name: string, description: string, version: string, encoding: string, java: JavaVersion) {
    this.name = name;
    this.description = description;
    this.version = version;
    this.encoding = encoding;
    this.java = java;
  }
}
