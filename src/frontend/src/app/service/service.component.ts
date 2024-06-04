import { Component, OnInit, Inject } from '@angular/core';
import { ApiService } from '../services/api.service';
import {MatProgressSpinner} from "@angular/material/progress-spinner";
import {MatDialog, MatDialogRef,
  MatDialogClose, MatDialogActions,
  MatDialogContent, MatDialogTitle,
MAT_DIALOG_DATA} from "@angular/material/dialog";
import { MaterialModule } from '../material/material.module';
import {FormsModule, ReactiveFormsModule, FormGroup, FormControl} from '@angular/forms';

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

}




@Component({
  selector: 'add-service',
  standalone: true,
  imports: [
    FormsModule, ReactiveFormsModule, MaterialModule, MatDialogTitle, MatDialogContent, MatDialogActions, MatDialogClose
  ],
  templateUrl: './add.html',
})
export class AddServiceComponent implements OnInit {
  serviveForm: FormGroup = new FormGroup({
    name: new FormControl(''),
    comment: new FormControl(''),
    filePath: new FormControl(''),
    userEmail: new FormControl(''),
  });

  ngOnInit(): void {
  }

  constructor(private apiService: ApiService, public dialogRef: MatDialogRef<AddServiceComponent>) {
    let user = localStorage.getItem('user');
    if (user) {
      let parsedUser = JSON.parse(user);
      if (parsedUser && parsedUser.user) {
        this.serviveForm.controls['userEmail'].setValue(parsedUser.user);
      }
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }


  addService() {
    this.apiService.createBikeService(this.serviveForm.value).subscribe(
      () => {
        this.dialogRef.close(true);
      },
      (error) => {
        console.error(error);
      }
    );
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
}
