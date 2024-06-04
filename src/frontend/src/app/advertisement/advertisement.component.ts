import {Component, Inject, Input, OnInit} from '@angular/core';
import { ApiService} from "../services/api.service";
import {MaterialModule} from "../material/material.module";
import { ActivatedRoute} from "@angular/router";
import {Advertisement} from "../advertisements/shared/advertisement";
import {
  MAT_DIALOG_DATA, MatDialog,
  MatDialogActions,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle
} from "@angular/material/dialog";
import {NgForOf, NgIf} from "@angular/common";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {FileInput} from "ngx-custom-material-file-input";
import {categories} from "../shared/categories";
import { AdsService} from "../services/ads.service";

export interface DialogData {
  advertisement: any;
}

@Component({
  selector: 'app-advertisement',
  templateUrl: './advertisement.component.html',
  standalone: true,
  styleUrls: ['./advertisement.component.scss'],
  imports: []
})
export class AdvertisementComponent implements OnInit {
  @Input() category?: string;
  advertisements: any[] = [];
  currentCategory: string = '';

  constructor(private apiService: ApiService, private route: ActivatedRoute, private dialog: MatDialog, private adsService: AdsService) {
    this.adsService.refreshAds.subscribe(() => {
      console.log('Refreshing ads');
      this.getAdvertisements();
    });
  }

ngOnInit() {
  this.route.params.subscribe(params => {
    this.category = history.state.category || '';
    this.getAdvertisements();
  });
}

getAdvertisements() {
  this.apiService.getAdvertisements(this.category).subscribe((advertisements: any[]) => {
    this.advertisements = advertisements;
  });
}

  getImageUrl(advertisement: Advertisement): string {
    return `/api/files/${advertisement.filePath}`;
  }

  openAddDialog(): void {
    const dialogRef = this.dialog.open(AddAdvertisementDialog);
  }

  openDetailsDialog(advertisement: any): void {
    const dialogRef = this.dialog.open(DetailsAdvertisementComponent, { data: { advertisement: advertisement } });
  }
}

@Component({
  selector:  'add-advertisement-dialog',
  standalone: true,
  imports: [MaterialModule, MatDialogActions, MatDialogContent, MatDialogTitle, NgIf, ReactiveFormsModule, NgForOf],
  templateUrl: './add.html',
})
export class AddAdvertisementDialog {
  adsForm = new FormGroup({
    category: new FormControl('', Validators.required),
    description: new FormControl('', [Validators.required, Validators.maxLength(500)]),
    price: new FormControl('', [Validators.required, Validators.min(0.01), Validators.max(100000)]),
    file: new FormControl(null, Validators.required)
  });
  private alertService: any;
  categories = categories;

  constructor(public dialogRef: MatDialogRef<AddAdvertisementDialog>, @Inject(MAT_DIALOG_DATA) public data: DialogData, private apiService: ApiService, private adsService: AdsService) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  addAdvertisement() {
    this.apiService.submitAdvertisementData(this.adsForm.value).subscribe(() => {
      this.dialogRef.close();
    });
  }

onSubmit() {
  if (this.adsForm.valid) {
    const fileControl = this.adsForm.get('file');

    if (fileControl && fileControl.value) {
      const fileInput = fileControl.value as FileInput;
      const file = fileInput.files[0];
      this.apiService.uploadFile(file).subscribe(
        imageResponse => {
          const advertisementData = {
            category: this.adsForm.get('category')?.value,
            description: this.adsForm.get('description')?.value,
            price: this.adsForm.get('price')?.value,
            filePath: imageResponse.filePath,
            userEmail: JSON.parse(<string>localStorage.getItem('user')).user
          };
          this.apiService.submitAdvertisementData(advertisementData).subscribe(
            response => {
              console.log('Advertisement added:', response);
              this.adsService.triggerRefresh(); // Move this line here
              this.dialogRef.close();
            },
            error => {
              console.error(error);
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

@Component({
  selector: 'details-advertisement',
  standalone: true,
  imports: [MaterialModule, MatDialogTitle, MatDialogContent, MatDialogActions],
  templateUrl: './details.html',
})
export class DetailsAdvertisementComponent {
  advertisementDetail: any;
  userEmail: string = '';

  constructor(public dialogRef: MatDialogRef<DetailsAdvertisementComponent>, @Inject(MAT_DIALOG_DATA) public data: DialogData, private apiService:ApiService) {
    console.log(data);
    this.getAdvertisementDetail(data.advertisement.id);
    let user = localStorage.getItem('user');
    if (user) {
      let parsedUser = JSON.parse(user);
      if (parsedUser && parsedUser.user) {
        this.userEmail = parsedUser.user;
      }
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  getAdvertisementDetail(id: string) {
    this.apiService.getAdvertisementById(id).subscribe(
      (response) => {
        this.advertisementDetail = response;
        console.log('Advertisement detail:', this.advertisementDetail);
      },
      (error) => {
        console.error(error);
      }
    );
  }

  onDeleteClick(id: string): void {
    this.apiService.deleteAdvertisement(id).subscribe(
      () => {
        this.dialogRef.close(true);
      },
      (error) => {
        console.error(error);
      }
    );
  }

  getImageUrl(advertisement: Advertisement): string {
    return `/api/files/${advertisement.filePath}`;
  }
}
