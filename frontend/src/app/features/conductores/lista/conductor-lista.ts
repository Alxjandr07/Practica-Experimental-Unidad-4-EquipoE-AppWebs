import { Component, signal, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ConductorService } from '../../../core/services/conductor.service';
import { Conductor } from '../../../core/models/conductor.model';

@Component({
  selector: 'app-conductor-lista',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './conductor-lista.html',
  styleUrl: './conductor-lista.scss',
})
export class ConductorLista implements OnInit {
  private service = inject(ConductorService);

  conductores = signal<Conductor[]>([]);
  loading = signal(true);
  totalPages = signal(0);
  currentPage = signal(0);
  errorMsg = signal<string | null>(null);

  ngOnInit(): void {
    this.cargar();
  }

  cargar(page = 0): void {
    this.loading.set(true);
    this.errorMsg.set(null);
    this.service.listar(page).subscribe({
      next: (res) => {
        this.conductores.set(res.content);
        this.totalPages.set(res.totalPages);
        this.currentPage.set(res.number);
        this.loading.set(false);
      },
      error: () => {
        this.errorMsg.set('Error al cargar conductores.');
        this.loading.set(false);
      },
    });
  }

  cambiarPagina(p: number): void {
    if (p >= 0 && p < this.totalPages()) {
      this.cargar(p);
    }
  }

  confirmarEliminar(id: number): void {
    if (confirm('¿Desactivar este conductor?')) {
      this.service.desactivar(id).subscribe({
        next: () => this.cargar(this.currentPage()),
        error: () => alert('No se pudo desactivar el conductor.'),
      });
    }
  }
}
