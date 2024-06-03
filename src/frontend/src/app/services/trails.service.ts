import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TrailService {
  refreshTrails = new Subject<void>();

  constructor() { }
}
