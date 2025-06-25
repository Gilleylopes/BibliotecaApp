import { Routes } from '@angular/router';
import { ListarAssuntosComponent } from './pages/listar-assuntos/listar-assuntos.component';

export const routes: Routes = [
  {
    path: '',
    component: ListarAssuntosComponent
  },
  {
    path: 'novo',
    loadComponent: () =>
      import('./pages/cadastrar-assunto/cadastrar-assunto.component').then(m => m.CadastrarAssuntoComponent)
  },
  {
    path: 'editar/:id',
    loadComponent: () =>
      import('./pages/cadastrar-assunto/cadastrar-assunto.component').then(m => m.CadastrarAssuntoComponent)
  }
];
