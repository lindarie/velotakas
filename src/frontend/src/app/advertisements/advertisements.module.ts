import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdvertisementsRoutingModule } from './advertisements-routing.module';
import {AdvertisementsService} from "./services/advertisements.service";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {RouterModule} from "@angular/router";
import {AdvertisementFormComponent} from "./components/advertisement-form/advertisement-form.component";
import {AdvertisementListComponent} from "./components/advertisement-list/advertisement-list.component";
import {CategoryListComponent} from "./components/category-list/category-list.component";
import {MaterialModule} from "../material/material.module";


@NgModule({
  declarations: [
    AdvertisementFormComponent,
    AdvertisementListComponent,
    CategoryListComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    RouterModule,
    AdvertisementsRoutingModule,
    MaterialModule
  ],
  providers: [AdvertisementsService]
})
export class AdvertisementsModule { }
