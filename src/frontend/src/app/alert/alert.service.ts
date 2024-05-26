import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

export interface Alert {
  type: 'success' | 'info' | 'warning' | 'danger';
  message: string;
}

@Injectable({
  providedIn: 'root'
})
export class AlertService {
  private alertSubject = new Subject<Alert>();
  alerts$ = this.alertSubject.asObservable();

  success(message: string) {
    this.alert('success', message);
  }

  info(message: string) {
    this.alert('info', message);
  }

  warning(message: string) {
    this.alert('warning', message);
  }

  danger(message: string) {
    this.alert('danger', message);
  }

  private alert(type: 'success' | 'info' | 'warning' | 'danger', message: string) {
    this.alertSubject.next({ type, message });
  }
}
