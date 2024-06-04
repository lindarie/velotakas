import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import  { MainComponent} from "./main/main.component";
import  { TrailsComponent} from "./trails/trails.component";
import  { ServiceComponent} from "./service/service.component";
import { AdvertisementComponent} from "./advertisement/advertisement.component";
import  { CategoriesComponent} from "./categories/categories.component";


export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  {
    path: '', component: MainComponent, children: [
      {path: '', redirectTo: 'sludinajumi', pathMatch: 'full'},
      { path: 'sludinajumi', component: AdvertisementComponent},
      {
        path: 'sludinajumi',
        loadChildren: () => import('./advertisements/advertisements.module').then(m => m.AdvertisementsModule)
      },
      {path: 'takas', component: TrailsComponent},
      {path: 'pakalpojumi', component: ServiceComponent},
      {
        path: 'reklamas', component: CategoriesComponent, children: [
          {path: '', component: AdvertisementComponent},
          {path: ':category', component: AdvertisementComponent},
        ]
      },
      {path: "**", redirectTo: 'takas'}


    ]
  }];
