import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { UsuarioService } from '../../services/usuario.service';
import { Usuario } from '../../models/usuario.model';
import { LoadingService } from 'src/app/core/services/loading.service';

@Component({
  standalone: true,
  selector: 'app-listar-usuarios',
  imports: [CommonModule, RouterModule],
  templateUrl: './listar-usuarios.component.html'
})
export class ListarUsuariosComponent implements OnInit {
  usuarios: Usuario[] = [];

  constructor(
    private usuarioService: UsuarioService,
    private loading: LoadingService
  ) {}

  ngOnInit(): void {
    this.carregar();
  }

  carregar() {
    this.loading.exibir();
    this.usuarioService.listar().subscribe({
      next: (res) => this.usuarios = res,
      error: () => this.loading.ocultar(),
      complete: () => this.loading.ocultar()
    });
  }

  excluir(id: number) {
    const ok = confirm('Deseja remover este usuário?');
    if (!ok) return;

    this.loading.exibir();
    this.usuarioService.excluir(id).subscribe({
      next: () => this.carregar(),
      error: () => this.loading.ocultar(),
      complete: () => this.loading.ocultar()
    });
  }
}
