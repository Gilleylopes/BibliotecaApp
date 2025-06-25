import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, RouterModule, Router } from '@angular/router';
import { AssuntoService } from '../../services/assunto.service';
import { Assunto } from '../../models/assunto.model';
import { LoadingService } from 'src/app/core/services/loading.service';
import { ToastrService } from 'ngx-toastr';


@Component({
  standalone: true,
  selector: 'app-cadastrar-assunto',
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './cadastrar-assunto.component.html'
})
export class CadastrarAssuntoComponent implements OnInit {
  form!: FormGroup;
  id?: number;
  editando = false;
  carregando = false;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private assuntoService: AssuntoService,
    private loading: LoadingService,
    private toastr: ToastrService 
  ) {}

  ngOnInit(): void {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    this.editando = !!this.id;

    this.form = this.fb.group({
         descricao: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]]
    });

    if (this.editando) {
        this.loading.exibir();
        this.assuntoService.buscarPorId(this.id).subscribe({
        next: (res) => this.form.patchValue(res),
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
    ? this.assuntoService.atualizar(this.id!, dados)
    : this.assuntoService.salvar(dados);

  requisicao.subscribe({
      next: () => {
        this.toastr.success(
          this.editando ? 'Assunto atualizado com sucesso!' : 'Assunto cadastrado com sucesso!'
        );
        this.router.navigate(['/assuntos']);
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
