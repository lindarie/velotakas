import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdsService {
  refreshAds = new Subject<void>();

  constructor() { }

  triggerRefresh() {
    console.log('Triggering refresh');
    this.refreshAds.next();
  }
}
