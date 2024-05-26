import { Component } from '@angular/core';
import { FormsModule } from "@angular/forms";
import { AuthService } from '../services/auth.service';
import { MaterialModule} from "../material/material.module";
import {DateAdapter} from "@angular/material/core";
import { formatDate} from "@angular/common";


@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, MaterialModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  name: string = '';
  surname: string = '';
  dateOfBirth: Date = new Date();
  email: string = '';
  password: string = '';
  description: string = '';

  constructor(private authService: AuthService, private dateAdapter: DateAdapter<Date>) {
    this.dateAdapter.setLocale(navigator.language);

  }

  register() {
    const formatDate1 = formatDate(this.dateOfBirth, 'dd/MM/yyyy', 'en-US');
    console.log('Registering user...', this.name, this.surname, this.dateOfBirth, this.email, this.password, this.description);
    this.authService.register({
      name: this.name,
      surname: this.surname,
      birthDate: formatDate1,
      email: this.email,
      password: this.password,
      description: this.description
    })
      .subscribe(
        () => {
          console.log('User registered successfully');
        },
        (error) => {
          console.error(error);
        }
      );
  }
}
