import {Component, Inject} from '@angular/core';
import  {Router} from "@angular/router";
import {RouterLink, RouterLinkActive, RouterOutlet} from "@angular/router";
import {categories} from "../shared/categories";
import { MaterialModule} from "../material/material.module";
import { AdvertisementComponent} from "../advertisement/advertisement.component";
import {
  MAT_DIALOG_DATA, MatDialog,
  MatDialogActions,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle
} from "@angular/material/dialog";
import {DialogData} from "../trails/trails.component";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {ApiService} from "../services/api.service";
import {FileInput} from "ngx-custom-material-file-input";

@Component({
  selector: 'app-categories',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, AdvertisementComponent, RouterOutlet],
  templateUrl: './categories.component.html',
  styleUrl: './categories.component.scss'
})
export class CategoriesComponent {
  categories = categories;

  constructor(private router: Router, private dialog: MatDialog){
    console.log(this.categories);
  }

  resetCategory() {
  this.router.navigate(['reklamas']);
}

navigate(categoryUrl: string, categoryValue: string) {
  this.router.navigate(['reklamas', categoryUrl], { state: { category: categoryValue } });
}


}
