import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router,RouterModule } from '@angular/router';
import { LivroService } from '../../services/livro.service';
import { Livro } from '../../models/livro.model';
import { LoadingService } from 'src/app/core/services/loading.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  standalone: true,
  selector: 'app-listar-livros',
  imports: [CommonModule, RouterModule],
  templateUrl: './listar-livros.component.html'
})
export class ListarLivrosComponent implements OnInit {
  livros: Livro[] = [];

  constructor(
    private livroService: LivroService,
    private loading: LoadingService,
    private router: Router,
    private toastr: ToastrService 
  ) {}

  ngOnInit(): void {
    this.carregar();
  }

  carregar() {
    this.loading.exibir();
    this.livroService.listar().subscribe({
      next: (res) => this.livros = res,
      error: () => this.loading.ocultar(),
      complete: () => this.loading.ocultar()
    });
  }

  excluir(id: number) {
    const ok = confirm('Deseja realmente excluir este livro?');
    if (!ok) return;

    this.loading.exibir();
    this.livroService.excluir(id).subscribe({
      next: () => {
        this.toastr.success('Livro excluído com sucesso!');
        this.carregar();
      },
      error: () => {
        this.toastr.error('Erro ao excluir o livro.');
        this.loading.ocultar();
      },
      complete: () => this.loading.ocultar()
    });
  }

  obterNomesAutores(livro: Livro): string {
    return livro.autores?.map(a => a.nome).join(', ') || '-';
  }

  obterNomesAssuntos(livro: Livro): string {
    return livro.assuntos?.map(a => a.descricao).join(', ') || '-';
  }
  
  voltarParaHome() {
    this.router.navigate(['/home']);
  }

}
