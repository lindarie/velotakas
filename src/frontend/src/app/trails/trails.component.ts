import { Component, OnInit, Inject } from '@angular/core';
import { FormsModule, FormGroup, FormControl, ReactiveFormsModule } from "@angular/forms";
import { ApiService } from '../services/api.service';
import  { MaterialModule } from "../material/material.module";
import  { MatDialog,
  MatDialogRef,
  MatDialogClose,
  MatDialogActions,
  MatDialogContent,
  MatDialogTitle,
  MAT_DIALOG_DATA} from "@angular/material/dialog";
import {MatInputModule} from "@angular/material/input";

export interface DialogData {
  trail: any;
}

@Component({
  selector: 'app-trails',
  standalone: true,
  imports: [ FormsModule, ReactiveFormsModule, MaterialModule],
  templateUrl: './trails.component.html',
  styleUrl: './trails.component.scss'
})
export class TrailsComponent implements OnInit{
    trails: any = null;

    constructor(private apiService: ApiService, private dialog: MatDialog) {
    }

  openDialog(trail: any): void {
  const dialogRef = this.dialog.open(DialogOverviewExampleDialog, {
    data: { trail: trail },
  });

      dialogRef.afterClosed().subscribe(result => {
        console.log('The dialog was closed');
      });
    }
// Add this method to TrailsComponent
    openAddTrailDialog(): void {
      const dialogRef = this.dialog.open(AddTrailDialog);

      dialogRef.afterClosed().subscribe(result => {
        if (result) {
          this.getTrails();
        }
      });
    }

    ngOnInit() {
      this.getTrails();
    }

    getTrails() {
      this.apiService.TrailList()
        .subscribe(
          (response) => {
            this.trails = response;
            console.log('Trails:', this.trails);

          },
          (error) => {
            console.error(error);
          }
        );
    }
}


@Component({
  selector: 'dialog-overview-example-dialog',
  templateUrl: 'dialog.html',
  standalone: true,
  imports: [
    MaterialModule,
    MatInputModule,
    FormsModule,
    ReactiveFormsModule,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatDialogClose,
  ],
})
export class DialogOverviewExampleDialog {
  trailDetail: any;

  constructor(
    public dialogRef: MatDialogRef<DialogOverviewExampleDialog>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private apiService: ApiService
  ) {
    this.getTrailDetail(data.trail.id);
  }

  getTrailDetail(id: string) {
    this.apiService.TrailDetail(id).subscribe(
      (response) => {
        this.trailDetail = response;
        console.log('Trail detail:', this.trailDetail);
      },
      (error) => {
        console.error(error);
      }
    );
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
@Component({
  selector: 'app-add-trail-dialog',
  templateUrl: 'add.html',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, MaterialModule,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatDialogClose,],
})
export class AddTrailDialog {
  trailForm = new FormGroup({
    name: new FormControl(''),
    surface: new FormControl(''),
    complexity: new FormControl(''),
    comment: new FormControl(''),
    // Add more form controls as needed
  });

  constructor(
    public dialogRef: MatDialogRef<AddTrailDialog>,
    private apiService: ApiService
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  addTrail(): void {
    this.apiService.addTrail(this.trailForm.value).subscribe(
      () => {
        this.dialogRef.close(true);
      },
      (error) => {
        console.error(error);
      }
    );
  }
}
