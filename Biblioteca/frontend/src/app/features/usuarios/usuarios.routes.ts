import { Routes } from '@angular/router';
import { ListarUsuariosComponent } from './pages/listar-usuarios/listar-usuarios.component';

export const routes: Routes = [
  {
    path: '',
    component: ListarUsuariosComponent
  },
  {
    path: 'novo',
    loadComponent: () =>
      import('./pages/cadastrar-usuario/cadastrar-usuario.component').then(m => m.CadastrarUsuarioComponent)
  },
  {
    path: 'editar/:id',
    loadComponent: () =>
      import('./pages/cadastrar-usuario/cadastrar-usuario.component').then(m => m.CadastrarUsuarioComponent)
  }
];
