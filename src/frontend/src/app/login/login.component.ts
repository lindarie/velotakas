import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { FormsModule } from "@angular/forms";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  email: string = '' ;
  password: string = '';


  constructor(private authService: AuthService, private router: Router) {}

login() {
  this.authService.login({ email: this.email, password: this.password })
    .subscribe(
      (response) => {
        // Save token and user in local storage
        console.log('User logged in successfully', response);

        localStorage.setItem('user', JSON.stringify(response));

        this.router.navigate(['/sludinajumi']);
      },
      (error) => {
        console.error(error);
      }
    );
}

}
