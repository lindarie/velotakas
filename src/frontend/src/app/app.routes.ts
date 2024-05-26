import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import  { MainComponent} from "./main/main.component";


export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: '', component: MainComponent, children: [
    { path: '', redirectTo: 'sludinajumi', pathMatch: 'full' },
      { path: 'sludinajumi', loadChildren: () => import('./advertisements/advertisements.module').then(m => m.AdvertisementsModule) }
      ] },


];
