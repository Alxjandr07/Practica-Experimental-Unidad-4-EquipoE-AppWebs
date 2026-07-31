import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-reportes-placeholder',
  standalone: true,
  imports: [RouterLink],
  template: `
    <div class="placeholder">
      <h1>Reportes</h1>
      <p>Modulo en construccion. Aqui se generaran estadisticas operativas y de seguridad exportables.</p>
      <a class="btn-back" routerLink="/dashboard">Volver al inicio</a>
    </div>
  `,
  styles: [`
    .placeholder {
      background: white;
      border: 1px solid var(--color-border);
      border-radius: 8px;
      padding: 3rem;
      text-align: center;
    }
    h1 { font-family: var(--font-heading); margin: 0 0 1rem; }
    p { color: var(--color-text-muted); margin-bottom: 2rem; }
    .btn-back {
      display: inline-block;
      padding: 0.6rem 1.2rem;
      background: var(--color-teal);
      color: white;
      border-radius: 6px;
      text-decoration: none;
      font-weight: 600;
    }
  `]
})
export class ReportesPlaceholder {}
