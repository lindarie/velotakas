import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private trailList = '/api/trails';
  private bikeService = '/api/service';

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

  createBikeService(service: any): Observable<any> {
    return this.http.post<any>(this.bikeService + '/create', service);
  }

  deleteBikeService(id: string): Observable<any> {
    return this.http.delete<any>(this.bikeService + '/delete/' + id);
  }

  serviceDetail(id: string): Observable<any> {
    return this.http.get<any>(this.bikeService + '/' + id);
  }

}
