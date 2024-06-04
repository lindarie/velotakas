import { Component } from '@angular/core';
import {RouterLink, RouterLinkActive} from "@angular/router";
import  {Router} from "@angular/router";

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent {
  isLogged: boolean = false;

  constructor(private router: Router) {
    let user = localStorage.getItem('user');
    if(user) {
      let parsedUser = JSON.parse(user);
      if(parsedUser && parsedUser.user) {
        this.isLogged = true;
      }
    }
  }

  logout() {
    localStorage.removeItem('user');
    this.router.navigate(['/login']);

  }

  login() {
    this.router.navigate(['/login']);
  }

}
