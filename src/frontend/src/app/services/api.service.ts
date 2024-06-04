import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private trailList = '/api/trails';
  private bikeService = '/api/service';
  private advertisement = '/api/ad';

  constructor(private http: HttpClient) {}

  TrailList(): Observable<any> {
    return this.http.get<any>(this.trailList);
  }

  TrailDetail(id: string): Observable<any> {
    return this.http.get<any>(this.trailList + '/' + id);
  }

  // Add this method to ApiService
  addTrail(trail: any): Observable<any> {
    return this.http.post<any>(this.trailList + '/' + 'createTrail', trail);
  }

  deleteTrail(id: string): Observable<any> {
    return this.http.delete<any>(this.trailList + '/delete/' +  id);
  }

  getBikeService(): Observable<any> {
    return this.http.get<any>(this.bikeService);
  }

  uploadFile(file: File): Observable<any> {
    const formData = new FormData();
    const uniqueFileName = `${Date.now()}-${file.name}`;
    formData.append('file', file, uniqueFileName);
    return this.http.post('/api/files/upload', formData);
  }

  createBikeService(serviceData: any): Observable<any> {
    return this.http.post<any>(this.bikeService + '/create', serviceData);
  }

  deleteBikeService(id: string): Observable<any> {
    return this.http.delete<any>(this.bikeService + '/' + id);
  }

  serviceDetail(id: string): Observable<any> {
    return this.http.get<any>(this.bikeService + '/' + id);
  }

  getAdvertisements(category: any): Observable<any> {
    let params = new HttpParams().set('category', category);
    return this.http.get<any>(this.advertisement, { params: params });
  }

  submitAdvertisementData(advertisementData: any): Observable<any> {
    advertisementData.userEmail = JSON.parse(<string>localStorage.getItem('user')).user;
    return this.http.post<any>('/api/ad/create', advertisementData);
  }

  getAdvertisementById(id: string): Observable<any> {
    return this.http.get<any>(this.advertisement + '/' + id);
  }

  deleteAdvertisement(id: string): Observable<any> {
    return this.http.delete<any>(this.advertisement + '/' + id);
  }
}
