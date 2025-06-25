import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class LoadingService {
  private carregando$ = new BehaviorSubject<boolean>(false);

  exibir() {
    this.carregando$.next(true);
  }

  ocultar() {
    this.carregando$.next(false);
  }

  estado(): Observable<boolean> {
    return this.carregando$.asObservable();
  }
}
