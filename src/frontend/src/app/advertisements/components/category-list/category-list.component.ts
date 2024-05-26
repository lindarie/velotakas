import { Component } from '@angular/core';;
import {categories} from "../../shared/categories";


@Component({
  selector: 'app-advertisement-list',
  templateUrl: './category-list.component.html',
  styleUrl: './category-list.component.scss'
})
export class CategoryListComponent {
  categories: { value: string; url: string}[] = categories;
}
