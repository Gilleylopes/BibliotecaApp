import { Routes } from '@angular/router';
import { ListarLivrosComponent } from './pages/listar-livros/listar-livros.component';

export const routes: Routes = [
  {
    path: '',
    component: ListarLivrosComponent
  },
  {
    path: 'novo',
    loadComponent: () =>
      import('./pages/cadastrar-livro/cadastrar-livro.component').then(m => m.CadastrarLivroComponent)
  },
  {
    path: 'editar/:id',
    loadComponent: () =>
      import('./pages/cadastrar-livro/cadastrar-livro.component').then(m => m.CadastrarLivroComponent)
  }
];
