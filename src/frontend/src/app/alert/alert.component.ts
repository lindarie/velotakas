import {Component, OnDestroy, OnInit} from '@angular/core';
import {Alert, AlertService} from "./alert.service";
import {Subscription} from "rxjs";
import {CommonModule} from "@angular/common";

@Component({
  selector: 'app-alert',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './alert.component.html',
  styleUrl: './alert.component.scss'
})
export class AlertComponent implements OnInit, OnDestroy {
  alerts: Alert[] = [];
  private subscription: Subscription = new Subscription();

  constructor(private alertService: AlertService) {}

  ngOnInit() {
    this.subscription = this.alertService.alerts$.subscribe(alert => {
      const existingAlert = this.alerts.find(a => a.message === alert.message);

      if (!existingAlert) {
        this.alerts.push(alert);
        setTimeout(() => this.removeAlert(alert), 5000);
      } else {
        existingAlert.type = alert.type;
        setTimeout(() => this.removeAlert(existingAlert), 5000);
      }
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  removeAlert(alert: Alert) {
    const index = this.alerts.indexOf(alert);
    if (index !== -1) {
      this.alerts.splice(index, 1);
    }
  }

  alertClass(alert: Alert) {
    switch (alert.type) {
      case 'success': return 'alert alert-success';
      case 'info': return 'alert alert-info';
      case 'warning': return 'alert alert-warning';
      case 'danger': return 'alert alert-danger';
    }
  }
}
