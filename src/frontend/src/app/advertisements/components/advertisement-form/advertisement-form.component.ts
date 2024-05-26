import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {categories} from "../../shared/categories";
import {ActivatedRoute, Router} from "@angular/router";
import {AdvertisementsService} from "../../services/advertisements.service";
import {AlertService} from "../../../alert/alert.service";
import {FileInput} from "ngx-custom-material-file-input";


@Component({
  selector: 'app-create-advertisement',
  templateUrl: './advertisement-form.component.html',
  styleUrls: ['./advertisement-form.component.scss']
})
export class AdvertisementFormComponent {
  message: string = '';
  submitted: boolean = false;
  categories: { value: string; url: string }[] = categories;
  category: string = '';

  adsForm = new FormGroup({
    category: new FormControl('', Validators.required),
    description: new FormControl('', [Validators.required, Validators.maxLength(500)]),
    price: new FormControl('', [Validators.required, Validators.min(0.01), Validators.max(100000)]),
    file: new FormControl(null, Validators.required)
  });

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private adsService: AdvertisementsService,
    private alertService: AlertService
  ) {}

  onSubmit() {
    this.submitted = true;
    if (this.adsForm.valid) {

      const fileControl = this.adsForm.get('file');

      if (fileControl && fileControl.value) {
        const fileInput = fileControl.value as FileInput;
        const file = fileInput.files[0];
        this.adsService.uploadFile(file).subscribe(
          imageResponse => {
            const advertisementData = {
              category: this.adsForm.get('category')?.value,
              description: this.adsForm.get('description')?.value,
              price: this.adsForm.get('price')?.value,
              filePath: imageResponse.filePath
            };
            this.adsService.submitAdvertisementData(advertisementData).subscribe(
              response => {
                this.alertService.success('Sludinājums tika pievienots veiksmīgi!');
                const url = this.categories.find(cat => cat.value === advertisementData.category)?.url || '';
                this.router.navigate(['/sludinajumi', url]);
              },
              error => {
                this.alertService.danger('Radās kļūda, lūdzu mēģiniet vēlreiz!');
              }
            );
          },
          error => {
            this.alertService.danger('Radās kļūda faila ielādē, lūdzu mēģiniet vēlreiz!');
            console.error(error);
          }
        );
      }
    } else {
      this.alertService.danger('Kļūda ievadē! Pārliecinieties, vai ir ievadīti pareizi dati!');
    }
  }
}
