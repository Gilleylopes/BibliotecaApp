import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from 'src/app/auth/services/auth.service';
import { Router, RouterModule } from '@angular/router';
import { Usuario } from 'src/app/auth/models/usuario.model';

@Component({
  standalone: true,
  selector: 'app-navbar',
  imports: [CommonModule, RouterModule], 
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  username: string = '';

  constructor(private auth: AuthService, private router: Router) {}

  ngOnInit() {
    this.auth.usuarioLogado$.subscribe(usuario => {
      this.username = usuario?.username ?? '';
    });
  }

  logout() {
    if (!this.auth.isAutenticado()) return;

    const confirmar = confirm('Tem certeza que deseja sair?');
    if (!confirmar) return;
    
    this.auth.logout();
    this.router.navigate(['/auth/login']);
  }

  isLogado(): boolean {
    if(this.username.trim() === '') {
      return false;
    }
    return this.auth.isAutenticado();
  }

  
}
