export class SignUpInfo {
  username: string;
  email: string;
  role: string[];
  password: string;
  telephoneNumber: string;
  firstName: string;
  lastName: string;

  constructor(username: string, email: string, password: string, telephoneNumber: string, firstName: string, lastName: string) {
    this.username = username;
    this.email = email;
    this.role = ['user'];
    this.password = password;
    this.telephoneNumber = telephoneNumber;
    this.firstName = firstName;
    this.lastName = lastName;
  }
}
