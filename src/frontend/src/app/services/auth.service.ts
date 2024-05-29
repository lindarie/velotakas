import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private registerUrl = '/api/user/register';
  private loginUrl = '/api/user/authenticate';

  constructor(private http: HttpClient) {}

  register(userData: any): Observable<any> {
    console.log('Registering user.aaa..', userData);
    return this.http.post<any>(this.registerUrl, userData);
  }

  login(userData: any): Observable<any> {
    return this.http.post<any>(this.loginUrl, userData);
  }

}
