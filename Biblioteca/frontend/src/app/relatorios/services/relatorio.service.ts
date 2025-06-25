import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({ providedIn: 'root' })
export class RelatorioService {
  private api = `${environment.apiUrl}/relatorios`;

  constructor(private http: HttpClient) {}

  gerarRelatorioLivros(): Observable<Blob> {
    return this.http.get(`${this.api}/livros`, { responseType: 'blob' });
  }
}
