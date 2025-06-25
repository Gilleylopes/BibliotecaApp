import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { AutorService } from '../../services/autor.service';
import { LoadingService } from 'src/app/core/services/loading.service';
import { ToastrService } from 'ngx-toastr';


@Component({
  standalone: true,
  selector: 'app-cadastrar-autor',
  templateUrl: './cadastrar-autor.component.html',
  imports: [CommonModule, ReactiveFormsModule, RouterModule]
})
export class CadastrarAutorComponent implements OnInit {
  form!: FormGroup;
  id?: number;
  editando = false;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private autorService: AutorService,
    private loading: LoadingService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    this.editando = !!this.id;

    this.form = this.fb.group({
      nome: ['', [Validators.required, Validators.minLength(3),, Validators.maxLength(80)]]
    });

    if (this.editando) {
      this.loading.exibir();
      this.autorService.buscarPorId(this.id).subscribe({
        next: autor => this.form.patchValue(autor),
        error: () => this.loading.ocultar(),
        complete: () => this.loading.ocultar()
      });
    }
  }

  salvar() {
    if (this.form.invalid) return;

    this.loading.exibir();
    const dados = this.form.value;

    const requisicao = this.editando
      ? this.autorService.atualizar(this.id!, dados)
      : this.autorService.salvar(dados);

    requisicao.subscribe({
      next: () => {
        this.toastr.success(
          this.editando ? 'Autor atualizado com sucesso!' : 'Autor cadastrado com sucesso!'
        );
        this.router.navigate(['/autores']);
      },
      error: (err) => {
        const mensagem = err.error?.mensagem || 'Erro inesperado ao salvar.';
        this.toastr.error(mensagem);
        this.loading.ocultar();
      },
      complete: () => this.loading.ocultar()
    });
  }
}
