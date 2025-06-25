import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoadingService } from 'src/app/core/services/loading.service';

@Component({
  standalone: true,
  selector: 'app-loading',
  imports: [CommonModule],
  template: `
    <div
      class="position-fixed top-0 start-0 w-100 h-100 d-flex justify-content-center align-items-center bg-dark bg-opacity-50"
      *ngIf="(loading.estado() | async)"
      style="z-index: 1050;"
    >
      <div class="spinner-border text-light" role="status">
        <span class="visually-hidden">Carregando...</span>
      </div>
    </div>
  `
})
export class LoadingComponent {
  constructor(public loading: LoadingService) {}
}
