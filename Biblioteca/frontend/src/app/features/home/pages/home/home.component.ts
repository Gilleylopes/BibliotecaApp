import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/services/auth.service';

@Component({
  standalone: true,
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  constructor(private router: Router, private auth: AuthService) {}

  navegar(destino: string) {
    this.router.navigate([destino]);
  }

  logout() {
    this.auth.logout();
    this.router.navigate(['/auth/login']);
  }
}
