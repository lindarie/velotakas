import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AdvertisementsService {

  constructor(private http: HttpClient) { }

  submitAdvertisementData(advertisementData: any) {
    return this.http.post<any>('/api/ad', advertisementData);
  }
}
