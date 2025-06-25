import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { AutorService } from '../../services/autor.service';
import { Autor } from '../../models/autor.model';
import { LoadingService } from 'src/app/core/services/loading.service';
import { ToastrService } from 'ngx-toastr';


@Component({
  standalone: true,
  selector: 'app-listar-autores',
  imports: [CommonModule, RouterModule],
  templateUrl: './listar-autores.component.html'
})
export class ListarAutoresComponent implements OnInit {
  autores: Autor[] = [];

  constructor(
    private autorService: AutorService,
    private loading: LoadingService,
    private router: Router,
    private toastr: ToastrService 
  ) {}

  ngOnInit(): void {
    this.carregarAutores();
  }

  carregarAutores() {
    this.loading.exibir();
    this.autorService.listar().subscribe({
      next: (res) => this.autores = res,
      error: () => this.loading.ocultar(),
      complete: () => this.loading.ocultar()
    });
  }

  excluir(id: number) {
    const ok = confirm('Tem certeza que deseja excluir este autor?');
    if (!ok) return;

    this.loading.exibir();
    this.autorService.excluir(id).subscribe({
      next: () => {
        this.toastr.success('Autor excluído com sucesso!');
        this.carregarAutores();
      },
      error: () => {
        this.toastr.error('Erro ao excluir o autor.');
        this.loading.ocultar();
      },
      complete: () => this.loading.ocultar()
    });
  }

  voltarParaHome() {
    this.router.navigate(['/home']);
  }
}
