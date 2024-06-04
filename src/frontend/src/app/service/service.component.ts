import { Component, OnInit, Inject } from '@angular/core';
import { ApiService } from '../services/api.service';
import {MatProgressSpinner} from "@angular/material/progress-spinner";
import {MatDialog, MatDialogRef,
  MatDialogClose, MatDialogActions,
  MatDialogContent, MatDialogTitle,
MAT_DIALOG_DATA} from "@angular/material/dialog";
import { MaterialModule } from '../material/material.module';
import {FormsModule, ReactiveFormsModule, FormGroup, FormControl, Validators} from '@angular/forms';
import {CommonModule} from "@angular/common";
import {FileInput} from "ngx-custom-material-file-input";
import {AlertService} from "../alert/alert.service";
import {Advertisement} from "../advertisements/shared/advertisement";

export interface DialogData {
  service: any;
}

@Component({
  selector: 'app-service',
  standalone: true,
  imports: [
    MatProgressSpinner, MaterialModule, FormsModule, ReactiveFormsModule
  ],
  templateUrl: './service.component.html',
  styleUrl: './service.component.scss'
})
export class ServiceComponent implements OnInit {
  services: any;

  constructor(private apiService: ApiService, private dialog: MatDialog) {}

  ngOnInit() {
    this.apiService.getBikeService().subscribe((data) => {
      console.log(data);
      this.services = data;
    });
  }
  openAddServiceDialog(): void {
    const dialogRef = this.dialog.open(AddServiceComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.apiService.getBikeService().subscribe((data) => {
          this.services = data;
        });
      }
    });
  }
  openServiceDetailDialog(service: any): void {
    const dialogRef = this.dialog.open(DetailsServiceComponent, {
      data: { service: service },
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.apiService.getBikeService().subscribe((data) => {
          this.services = data;
        });
      }
    });

  }

  getShortComment(comment: string) {
    return comment.length > 50 ? comment.slice(0, 50) + '...' : comment;
  }
}




@Component({
  selector: 'add-service',
  standalone: true,
  imports: [
    FormsModule, ReactiveFormsModule, MaterialModule, MatDialogTitle, MatDialogContent, MatDialogActions, MatDialogClose,
    CommonModule
  ],
  templateUrl: './add.html',
})
export class AddServiceComponent implements OnInit {
  serviceForm = new FormGroup({
    name: new FormControl('', Validators.required),
    comment: new FormControl('', [Validators.required, Validators.maxLength(300)]),
    file: new FormControl('', Validators.required),
    userEmail: new FormControl('')
  });

  ngOnInit(): void {
  }

  constructor(private apiService: ApiService, public dialogRef: MatDialogRef<AddServiceComponent>, private alertService: AlertService) {
    let user = localStorage.getItem('user');
    if (user) {
      let parsedUser = JSON.parse(user);
      if (parsedUser && parsedUser.user) {
        this.serviceForm.controls['userEmail'].setValue(parsedUser.user);
      }
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  addService() {
    if (this.serviceForm.valid) {
      const fileControl = this.serviceForm.get('file');
      if (fileControl && fileControl.value) {
        const fileInput = fileControl.value as unknown as FileInput;
        const file = fileInput.files[0];
        let user = localStorage.getItem('user');
        this.apiService.uploadFile(file).subscribe(
            imageResponse => {
              const serviceData = {
                name: this.serviceForm.get('name')?.value,
                comment: this.serviceForm.get('comment')?.value,
                userEmail: this.serviceForm.get('userEmail')?.value,
                filePath: imageResponse.filePath
              };
                this.apiService.createBikeService(serviceData).subscribe(
                    () => {
                      this.alertService.success('Pakalpojums tika pievienots veiksmīgi!');
                      this.dialogRef.close(true);
                      },
                    (error) => {console.error(error);}
                );
            },
            error => {this.alertService.danger('Radās kļūda faila ielādē, lūdzu mēģiniet vēlreiz!');}
        );
      }
    } else {this.alertService.danger('Kļūda ievadē! Pārliecinieties, vai ir ievadīti pareizi dati!');}
  }
}

@Component({
  selector: 'details-service',
  standalone: true,
  imports: [
    MaterialModule, MatDialogTitle, MatDialogContent, MatDialogActions, MatDialogClose
  ],
  templateUrl: './details.html',
})
export class DetailsServiceComponent {
  serviceDetail: any;
  userEmail: string = '';

  constructor(public dialogRef: MatDialogRef<DetailsServiceComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData, private apiService: ApiService) {
    console.log(data);
    this.getServiceDetail(data.service.id);
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

  onDeleteClick(id: string): void {
    this.apiService.deleteBikeService(id).subscribe(
      () => {
        this.dialogRef.close(true);
      },
      (error) => {
        console.error(error);
      }
    );

  }

  getServiceDetail(id: string) {
    this.apiService.serviceDetail(id).subscribe((response) => {
        this.serviceDetail = response;
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
