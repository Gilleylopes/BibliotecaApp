import { Routes } from '@angular/router';
import { ListarAutoresComponent } from './pages/listar-autores/listar-autores.component';

export const routes: Routes = [
  {
    path: '',
    component: ListarAutoresComponent
  },
  {
    path: 'novo',
    loadComponent: () =>
      import('./pages/cadastrar-autor/cadastrar-autor.component').then(m => m.CadastrarAutorComponent)
  },
  {
    path: 'editar/:id',
    loadComponent: () =>
      import('./pages/cadastrar-autor/cadastrar-autor.component').then(m => m.CadastrarAutorComponent)
  }
];
    