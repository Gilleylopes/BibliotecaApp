import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Assunto } from '../models/assunto.model';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';


@Injectable({ providedIn: 'root' })
export class AssuntoService {
  private api = `${environment.apiUrl}/assuntos`;

  constructor(private http: HttpClient) {}

  listar(): Observable<Assunto[]> {
    return this.http.get<Assunto[]>(this.api);
  }

  buscarPorId(id: number): Observable<Assunto> {
    return this.http.get<Assunto>(`${this.api}/${id}`);
  }

  salvar(dados: Omit<Assunto, 'id'>): Observable<Assunto> {
    return this.http.post<Assunto>(this.api, dados);
  }

  atualizar(id: number, dados: Omit<Assunto, 'id'>): Observable<Assunto> {
    return this.http.put<Assunto>(`${this.api}/${id}`, dados);
  }

  excluir(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }
}
