import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';

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
    return this.http.post<any>('/api/ad/create', advertisementData);
  }

  uploadFile(file: File): Observable<any> {
    const formData = new FormData();
    const uniqueFileName = `${Date.now()}-${file.name}`; // Generate a unique file name
    formData.append('file', file, uniqueFileName);
    return this.http.post('/api/files/upload', formData);
  }

  getAdvertisementsByCategory(category: string): Observable<Advertisement[]> {
    return this.http.get<Advertisement[]>(`/api/ad?category=${category}`);
  }

  getAdvertisementById(id: string): Observable<Advertisement> {
    return this.http.get<Advertisement>(`/api/ad/${id}`);
  }
}
