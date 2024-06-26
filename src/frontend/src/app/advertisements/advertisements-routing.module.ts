import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AdvertisementFormComponent} from "./components/advertisement-form/advertisement-form.component";
import {CategoryListComponent} from "./components/category-list/category-list.component";
import {AdvertisementListComponent} from "./components/advertisement-list/advertisement-list.component";
import {AdvertisementDetailComponent} from "./components/advertisement-detail/advertisement-detail.component";

const routes: Routes = [
  { path: '', component: CategoryListComponent },
  { path: 'jauns', component: AdvertisementFormComponent },
  { path: ':category/:id', component: AdvertisementDetailComponent },
  { path: ':category', component: AdvertisementListComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdvertisementsRoutingModule { }
