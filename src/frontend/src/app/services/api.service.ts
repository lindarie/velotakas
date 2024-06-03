import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private trailList = '/api/trails';

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

}
