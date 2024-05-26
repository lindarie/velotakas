import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdvertisementsService {
  private adApiUrl = '/api/ad';
  private imageUploadUrl = '/api/files/upload';

  constructor(private http: HttpClient) {}

  submitAdvertisementData(advertisementData: any): Observable<any> {
    return this.http.post<any>(this.adApiUrl, advertisementData);
  }

  uploadFile(file: File): Observable<any> {
    const formData = new FormData();
    const uniqueFileName = `${Date.now()}-${file.name}`; // Generate a unique file name
    formData.append('file', file, uniqueFileName);
    return this.http.post(this.imageUploadUrl, formData);
  }
}
