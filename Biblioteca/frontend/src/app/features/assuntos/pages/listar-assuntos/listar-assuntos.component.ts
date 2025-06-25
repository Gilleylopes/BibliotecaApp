import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { AssuntoService } from '../../services/assunto.service';
import { Assunto } from '../../models/assunto.model';
import { LoadingService } from 'src/app/core/services/loading.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  standalone: true,
  selector: 'app-listar-assuntos',
  imports: [CommonModule, RouterModule],
  templateUrl: './listar-assuntos.component.html'
})
export class ListarAssuntosComponent implements OnInit {
  assuntos: Assunto[] = [];

  constructor(
    private assuntoService: AssuntoService,
    private loading: LoadingService,
    private router: Router,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.carregarAssuntos();
  }

  carregarAssuntos() {
    this.loading.exibir();
    this.assuntoService.listar().subscribe({
      next: (res) => this.assuntos = res,
      error: () => this.loading.ocultar(),
      complete: () => this.loading.ocultar()
    });
  }

  excluir(id: number) {
    const ok = confirm('Tem certeza que deseja excluir este assunto?');
    if (!ok) return;

    this.loading.exibir();
    this.assuntoService.excluir(id).subscribe({
        next: () => {
          this.toastr.success('Assunto excluído com sucesso!');
          this.carregarAssuntos(); 
        },
        error: () => {
          this.toastr.error('Erro ao excluir o assunto.');
        },
        complete: () => this.loading.ocultar()
      });
  }

   voltarParaHome() {
    this.router.navigate(['/home']);
  }
}
