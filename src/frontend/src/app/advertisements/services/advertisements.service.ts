import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';

export interface Advertisement {
  category: string;
  description: string;
  price: number;
  timeLimit: string;
  filePath: string;
  userId: number;
}

@Injectable({
  providedIn: 'root'
})

export class AdvertisementsService {
  constructor(private http: HttpClient) {}

  submitAdvertisementData(advertisementData: any): Observable<any> {
    advertisementData.userEmail = JSON.parse(<string>localStorage.getItem('user')).user;
    return this.http.post<any>('/api/ad/create', advertisementData);
  }

  uploadFile(file: File): Observable<any> {
    const formData = new FormData();
    const uniqueFileName = `${Date.now()}-${file.name}`;
    formData.append('file', file, uniqueFileName);
    return this.http.post('/api/files/upload', formData);
  }

  getAdvertisementsByCategory(category?: string): Observable<Advertisement[]> {
    let apiUrl = '/api/ad';
    if (category && category.trim() !== '') {
      apiUrl += `?category=${category}`;
    }
    return this.http.get<Advertisement[]>(apiUrl);
  }

  getAdvertisementById(id: string): Observable<Advertisement> {
    return this.http.get<Advertisement>(`/api/ad/${id}`);
  }
}
