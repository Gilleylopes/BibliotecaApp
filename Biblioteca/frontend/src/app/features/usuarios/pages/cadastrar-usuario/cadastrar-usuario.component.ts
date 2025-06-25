import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { UsuarioService } from '../../services/usuario.service';
import { LoadingService } from 'src/app/core/services/loading.service';

@Component({
  standalone: true,
  selector: 'app-cadastrar-usuario',
  templateUrl: './cadastrar-usuario.component.html',
  imports: [CommonModule, ReactiveFormsModule, RouterModule]
})
export class CadastrarUsuarioComponent implements OnInit {
  form!: FormGroup;
  id?: number;
  editando = false;
  papeis = ['ADMIN', 'USER'];

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private usuarioService: UsuarioService,
    private loading: LoadingService
  ) {}

  ngOnInit(): void {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    this.editando = !!this.id;

    this.form = this.fb.group({
      nome: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      papel: ['', Validators.required],
      senha: [''] 
    });

    if (this.editando) {
      this.loading.exibir();
      this.usuarioService.buscarPorId(this.id).subscribe({
        next: (res) => {
          this.form.patchValue(res);
          this.form.get('senha')?.clearValidators(); 
          this.form.get('senha')?.updateValueAndValidity();
        },
        complete: () => this.loading.ocultar()
      });
    } else {
      this.form.get('senha')?.setValidators([Validators.required, Validators.minLength(6)]);
    }
  }

  salvar() {
    if (this.form.invalid) return;

    this.loading.exibir();
    const dados = this.form.value;

    const requisicao = this.editando
      ? this.usuarioService.atualizar(this.id!, dados)
      : this.usuarioService.salvar(dados);

    requisicao.subscribe({
      next: () => this.router.navigate(['/usuarios']),
      complete: () => this.loading.ocultar()
    });
  }
}
