import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Autor } from '../models/autor.model';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({ providedIn: 'root' })
export class AutorService {
  private api = `${environment.apiUrl}/autores`;

  constructor(private http: HttpClient) {}

  listar(): Observable<Autor[]> {
    return this.http.get<Autor[]>(this.api);
  }

  buscarPorId(id: number): Observable<Autor> {
    return this.http.get<Autor>(`${this.api}/${id}`);
  }

  salvar(dados: Omit<Autor, 'id'>): Observable<Autor> {
    return this.http.post<Autor>(this.api, dados);
  }

  atualizar(id: number, dados: Omit<Autor, 'id'>): Observable<Autor> {
    return this.http.put<Autor>(`${this.api}/${id}`, dados);
  }

  excluir(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }
}
