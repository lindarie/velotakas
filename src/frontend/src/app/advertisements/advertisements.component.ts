import { Component } from '@angular/core';
import {CommonModule} from "@angular/common";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {AdvertisementsService} from "../services/advertisements.service";

@Component({
  selector: 'app-advertisements',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './advertisements.component.html',
  styleUrl: './advertisements.component.scss'
})
export class AdvertisementsComponent {
  message: string = '';
  submitted: boolean = false;

  adsForm = new FormGroup({
    category: new FormControl('', Validators.required),
    description: new FormControl('', [Validators.required, Validators.maxLength(500)]),
    price: new FormControl('', [Validators.required, Validators.min(0.01), Validators.max(100000)])
  });

  constructor(private adsService: AdvertisementsService) {}

  onSubmit() {
    this.submitted = true;
    if (this.adsForm.valid) {
      const advertisementData = this.adsForm.value;
      this.adsService.submitAdvertisementData(advertisementData).subscribe(
        response => {
          this.message = 'Sludinājums tika pievienots veiksmīgi!';
          this.adsForm.reset();
          this.submitted = false;
        },
        error => {
          this.message = 'Radās kļūda, lūdzu mēģiniet vēlreiz!';
        }
      );
    } else {
      this.message = 'Kļūda ievadē! Pārliecinieties, vai ir ievadīti pareizi dati!';
    }
  }
}
