import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {categories} from "../../shared/categories";


@Component({
  selector: 'app-advertisement-by-category',
  templateUrl: './advertisement-list.component.html',
  styleUrls: ['./advertisement-list.component.scss']
})

export class AdvertisementListComponent implements OnInit {
  categoriesList: { value: string; url: string }[] = categories;
  category: string = '';
  categoryUrl: string = '';

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.categoryUrl = params['category'];
      this.category = this.getCategoryValue(this.categoryUrl);
    });
  }

  getCategoryValue(category: string): string {
    const categoryObj = this.categoriesList.find(cat => cat.url === category);
    return categoryObj ? categoryObj.value : '';
  }
}
