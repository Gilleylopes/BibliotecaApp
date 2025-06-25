import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Livro, LivroRequest } from '../models/livro.model';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';


@Injectable({ providedIn: 'root' })
export class LivroService {
  private api = `${environment.apiUrl}/livros`;

  constructor(private http: HttpClient) {}

  listar(): Observable<Livro[]> {
    return this.http.get<Livro[]>(this.api);
  }

  buscarPorId(id: number): Observable<Livro> {
    return this.http.get<Livro>(`${this.api}/${id}`);
  }

  salvar(dados: LivroRequest): Observable<Livro> {
    return this.http.post<Livro>(this.api, dados);
  }

  atualizar(id: number, dados: LivroRequest): Observable<Livro> {
    return this.http.put<Livro>(`${this.api}/${id}`, dados);
  }

  excluir(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }
}
