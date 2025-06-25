import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Usuario } from '../models/usuario.model';
import { BehaviorSubject } from 'rxjs';

interface AuthResponse {
  accessToken: string;
  refreshToken: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly api = `${environment.apiUrl}/auth`;

  public usuarioLogado$ = new BehaviorSubject<Usuario | null>(null);

  constructor(private http: HttpClient) {}

  login(credentials: { username: string; senha: string }): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.api}/login`, credentials);
  }

  salvarTokens(auth: AuthResponse) {
    localStorage.setItem('access_token', auth.accessToken);
    localStorage.setItem('refresh_token', auth.refreshToken);
  }

  getToken(): string | null {
    return localStorage.getItem('access_token');
  }

  getPerfil(): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.api}/me`);
  }

  isAutenticado(): boolean {
    return !!this.getToken();
  }

  getRefreshToken(): string | null {
    return localStorage.getItem('refresh_token');
  }  

  refresh(refreshToken: string): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.api}/refresh`, { refreshToken });
  }  

  logout() {
    localStorage.removeItem('access_token');
    localStorage.removeItem('refresh_token');
    this.usuarioLogado$.next(null); 
  }
}
