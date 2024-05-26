import { Component } from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterOutlet} from "@angular/router";
import {NavbarComponent} from "./navbar/navbar.component";
import {AlertComponent} from "./alert/alert.component";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  standalone: true,
  imports: [CommonModule, RouterOutlet, NavbarComponent, AlertComponent]
})

export class AppComponent {
  title = 'velotakas';
}
