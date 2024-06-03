import { CanActivate, Router } from '@angular/router';

class guardGuard implements CanActivate {

  constructor(private router: Router) {}

  canActivate() {
    if (localStorage.getItem('token')) {
      return true;
    }

    this.router.navigate(['/login']);
    return false;

  }
};
