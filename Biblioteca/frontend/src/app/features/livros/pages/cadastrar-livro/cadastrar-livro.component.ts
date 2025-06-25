import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { LivroService } from '../../services/livro.service';
import { AutorService } from 'src/app/features/autores/services/autor.service';
import { AssuntoService } from 'src/app/features/assuntos/services/assunto.service';
import { Autor } from 'src/app/features/autores/models/autor.model';
import { Assunto } from 'src/app/features/assuntos/models/assunto.model';
import { LoadingService } from 'src/app/core/services/loading.service';
import { NgxMaskDirective } from 'ngx-mask';
import { LivroRequest } from '../../models/livro.model';
import { ToastrService } from 'ngx-toastr';
import { NgSelectModule } from '@ng-select/ng-select';

@Component({
  standalone: true,
  selector: 'app-cadastrar-livro',
  templateUrl: './cadastrar-livro.component.html',
  imports: [CommonModule, ReactiveFormsModule, RouterModule, NgxMaskDirective, NgSelectModule]
})
export class CadastrarLivroComponent implements OnInit {
  form!: FormGroup;
  id?: number;
  editando = false;
  autores: Autor[] = [];
  assuntos: Assunto[] = [];
  submitted = false;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private livroService: LivroService,
    private autorService: AutorService,
    private assuntoService: AssuntoService,
    private loading: LoadingService,
    private toastr: ToastrService 
  ) {}

  ngOnInit(): void {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    this.editando = !!this.id;

    this.form = this.fb.group({
      titulo: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(150)]],
      autoresIds: [[], Validators.required],
      assuntosIds: [[], Validators.required],
      editora: ['', [Validators.required, Validators.maxLength(40)]],
      edicao: [null, [Validators.required, Validators.maxLength(1), Validators.pattern(/^\d{1}$/)]],
      anoPublicacao: ['', [Validators.required, Validators.maxLength(4), Validators.pattern(/^\d{4}$/)]],
      valor: ['', [Validators.required, Validators.maxLength(16), Validators.pattern(/^\d+(\.\d{1,2})?$/)]]
    });

    this.carregarDropdowns();

    if (this.editando) {
      this.loading.exibir();
      this.livroService.buscarPorId(this.id).subscribe({
        next: (livro) => {
          this.form.patchValue({
            titulo: livro.titulo,
            editora: livro.editora,
            edicao: livro.edicao,
            anoPublicacao: livro.anoPublicacao,
            valor: livro.valor,
            assuntosIds: livro.assuntos.map(a => a.id),
            autoresIds: livro.autores.map(a => a.id)
          });
        },
        complete: () => this.loading.ocultar()
      });
    }
  }

  carregarDropdowns() {
    this.autorService.listar().subscribe(res => this.autores = res);
    this.assuntoService.listar().subscribe(res => this.assuntos = res);
  }

  salvar() {
    this.submitted = true;
    
    if (this.form.invalid) return;

    this.loading.exibir();
    const dados = this.form.value;
    //console.log(this.form.value);

    const payload = this.mapFormToLivroRequest(this.form);

    const requisicao = this.editando
      ? this.livroService.atualizar(this.id!, payload)
      : this.livroService.salvar(payload);

    requisicao.subscribe({
      next: () => {
        this.toastr.success(
          this.editando ? 'Livro atualizado com sucesso!' : 'Livro cadastrado com sucesso!'
        );
        this.router.navigate(['/livros']);
      },
      error: (err) => {
        const mensagem = err.error?.mensagem || 'Erro inesperado ao salvar.';
        this.toastr.error(mensagem);
        this.loading.ocultar();
      },
      complete: () => this.loading.ocultar()
    });
  }

  private mapFormToLivroRequest(form: FormGroup): LivroRequest {
      const raw = form.value;

      return {
        titulo: (raw.titulo ?? '').trim(),
        editora: (raw.editora ?? '').trim(),
        edicao: Number(raw.edicao) || null,
        anoPublicacao: (raw.anoPublicacao ?? '').toString().trim(),
        valor: parseFloat(
          typeof raw.valor === 'string'
            ? raw.valor.replace(/\./g, '').replace(',', '.')
            : String(raw.valor)
        ) || 0,
        autoresIds: Array.isArray(raw.autoresIds)
          ? raw.autoresIds.map(Number)
          : [],
        assuntosIds: Array.isArray(raw.assuntosIds)
          ? raw.assuntosIds.map(Number)
          : []
      };
    }

    permitirSomenteNumeros(event: KeyboardEvent): void {
      const charCode = event.key.charCodeAt(0);
      if (charCode < 48 || charCode > 57) {
        event.preventDefault();
      }
    }

    permitirSomenteUmNumero(event: KeyboardEvent): void {
      const inputChar = event.key;
      const value = (event.target as HTMLInputElement).value;

      // Permitir apenas um único dígito
      if (!/^\d$/.test(inputChar) || value.length >= 1) {
        event.preventDefault();
    }
}

}

