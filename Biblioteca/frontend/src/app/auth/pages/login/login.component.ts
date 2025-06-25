import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  imports: [CommonModule, ReactiveFormsModule, RouterModule] 
})
export class LoginComponent implements OnInit {
  form: FormGroup;
  loading = false;
  erroLogin = false;

  constructor(
    private fb: FormBuilder,
    private auth: AuthService,
    private router: Router
  ) {
    this.form = this.fb.group({
      username: ['', [Validators.required]],
      senha: ['', [Validators.required]]
    });
  }

   ngOnInit(): void {
    localStorage.removeItem('access_Token');
    localStorage.removeItem('refresh_Token');
  }

  login() {
    if (this.form.invalid) return;

    this.loading = true;
    this.auth.login(this.form.value).subscribe({
      next: (res) => {
        this.auth.salvarTokens({
          accessToken: res.accessToken,
          refreshToken: res.refreshToken
        });
        
        this.auth.getPerfil().subscribe(usuario => {
          this.auth.usuarioLogado$.next(usuario);
          this.router.navigate(['/home']); 
        });
      },
      error: () => {
        this.erroLogin = true;
        this.loading = false;
      }
    });
  }
}
