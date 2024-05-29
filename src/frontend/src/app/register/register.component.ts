import { Component } from '@angular/core';
import { FormsModule, FormGroup, FormControl, ReactiveFormsModule } from "@angular/forms";
import { AuthService } from '../services/auth.service';
import { MaterialModule} from "../material/material.module";
import {DateAdapter} from "@angular/material/core";
import { formatDate} from "@angular/common";
import {MatDatepickerInputEvent} from "@angular/material/datepicker";


@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, MaterialModule, ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  registerForm = new FormGroup({
    name: new FormControl(''),
    surname: new FormControl(''),
    dateOfBirth: new FormControl<Date | null>(null),
    email: new FormControl(''),
    password: new FormControl(''),
    description: new FormControl('')
  });

  constructor(private authService: AuthService, private dateAdapter: DateAdapter<Date>) {
    this.dateAdapter.setLocale(navigator.language);

  }

  register() {
    const dateOfBirth = this.registerForm.value.dateOfBirth;
    if (!dateOfBirth) {
      console.error('Date of birth is required');
      return;
    }
    const formatDate1 = formatDate(dateOfBirth, 'dd/MM/yyyy', 'en-US');
    console.log('Registering user...', this.registerForm.value);
    this.authService.register({
      name: this.registerForm.value.name,
      surname: this.registerForm.value.surname,
      birthDate: formatDate1,
      email: this.registerForm.value.email,
      password: this.registerForm.value.password,
      description: this.registerForm.value.description
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

  onDateChange(event: MatDatepickerInputEvent<Date>) {
  // @ts-ignore
    this.registerForm.get('dateOfBirth').setValue(event.value, {emitEvent: false});
    console.log('Date of birth changed', event.value, this.registerForm.value.dateOfBirth);
}
}
