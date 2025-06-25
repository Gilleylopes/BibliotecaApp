import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { RelatorioService } from 'src/app/relatorios/services/relatorio.service';

@Component({
  selector: 'app-relatorio',
  templateUrl: './relatorio.component.html'
})
export class RelatorioComponent {
  constructor(private relatorioService: RelatorioService,
              private router: Router
  ) {}

  abrirRelatorio() {
    this.relatorioService.gerarRelatorioLivros().subscribe({
      next: (blob) => {
        const fileURL = URL.createObjectURL(blob);
        window.open(fileURL, '_blank');
      },
      error: (err) => {
        const reader = new FileReader();
        reader.onload = () => {
          console.error('Erro ao gerar relatório:', reader.result);
          alert(`Erro no relatório: ${reader.result}`);
        };
        reader.readAsText(err.error);
      }
    });
  }

  voltarParaHome() {
    this.router.navigate(['/home']);
  }
}
