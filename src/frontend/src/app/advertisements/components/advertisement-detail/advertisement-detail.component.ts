import { Component } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AdvertisementsService} from "../../services/advertisements.service";
import {Advertisement} from "../../shared/advertisement";

@Component({
  selector: 'app-advertisement-detail',
  templateUrl: './advertisement-detail.component.html',
  styleUrl: './advertisement-detail.component.scss'
})
export class AdvertisementDetailComponent {
  advertisement: Advertisement | undefined;

  constructor(
    private route: ActivatedRoute,
    private adsService: AdvertisementsService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const id = params['id'];
      this.adsService.getAdvertisementById(id).subscribe(data => {
        this.advertisement = data;
      });
    });
  }

  getImageUrl(): string {
    return this.advertisement ? `/api/files/${this.advertisement.filePath}` : '';
  }
}
