import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {categories} from "../../shared/categories";
import {AdvertisementsService} from "../../services/advertisements.service";
import {Advertisement} from "../../shared/advertisement";


@Component({
  selector: 'app-advertisement-by-category',
  templateUrl: './advertisement-list.component.html',
  styleUrls: ['./advertisement-list.component.scss']
})

export class AdvertisementListComponent implements OnInit {
  categoriesList: { value: string; url: string }[] = categories;
  category: string = '';
  categoryUrl: string = '';
  advertisements: Advertisement[] = [];
  displayedColumns: string[] = ['image', 'description', 'price'];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private adsService: AdvertisementsService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.categoryUrl = params['category'];
      this.category = this.getCategoryFromRoute(this.categoryUrl);
      this.fetchAdvertisements();
    });
  }

  getCategoryFromRoute(category: string): string {
    const categoryObj = this.categoriesList.find(cat => cat.url === category);
    return categoryObj ? categoryObj.value : '';
  }

  fetchAdvertisements(): void {
    if (this.category) {
      this.adsService.getAdvertisementsByCategory(this.category).subscribe(data => {
        this.advertisements = data;
      });
    }
  }

  navigateToDetail(advertisement: Advertisement): void {
    this.router.navigate(['/sludinajumi', this.categoryUrl, advertisement.id]);
  }

  getImageUrl(advertisement: Advertisement): string {
    return `/api/files/${advertisement.filePath}`;
  }
}
