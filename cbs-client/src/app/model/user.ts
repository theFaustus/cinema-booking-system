export class User {
  id: number;
  username: string;
  email: string;
  role: string;
  enabled: number;
  firstName: string;
  lastName: string;
  telephoneNumber: string;


  constructor(id: number, username: string, email: string, role: string, enabled: number, firstName: string, lastName: string, telephoneNumber: string) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.role = role;
    this.enabled = enabled;
    this.firstName = firstName;
    this.lastName = lastName;
    this.telephoneNumber = telephoneNumber;
  }
}
