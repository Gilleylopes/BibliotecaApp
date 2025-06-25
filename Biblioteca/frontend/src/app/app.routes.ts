import { authGuard } from './core/guards/auth.guard';
import { Routes } from '@angular/router';
import { RelatorioComponent } from './relatorios/pages/relatorio.component';

export const routes: Routes = [
  {
    path: 'auth/login',
    loadComponent: () =>
      import('./auth/pages/login/login.component').then((m) => m.LoginComponent)
  },
  {
    path: '',
    redirectTo: 'auth/login',
    pathMatch: 'full'
  },
  {
    path: 'usuarios',
    canActivate: [authGuard],
    loadChildren: () =>
      import('./features/usuarios/usuarios.routes').then(m => m.routes)
  },  
  {
    path: 'assuntos',
    canActivate: [authGuard],
    loadChildren: () =>
      import('./features/assuntos/assuntos.routes').then(m => m.routes)
  },
  {
    path: 'autores',
    canActivate: [authGuard],
    loadChildren: () =>
      import('./features/autores/autores.routes').then(m => m.routes)
  },
  {
    path: 'livros',
    canActivate: [authGuard],
    loadChildren: () =>
      import('./features/livros/livros.routes').then(m => m.routes)
  },
  {
    path: 'home',
    canActivate: [authGuard],
    loadChildren: () =>
      import('./features/home/home.routes').then(m => m.routes)
  },
  {
  path: 'relatorio',
  component: RelatorioComponent
  }
  
];

