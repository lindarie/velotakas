import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: 'sludinajumi', loadChildren: () => import('./advertisements/advertisements.module').then(m => m.AdvertisementsModule) }
];
