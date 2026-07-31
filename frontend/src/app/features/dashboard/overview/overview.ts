import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

interface SummaryCard {
  label: string;
  value: string;
  trend: string;
}

interface ModuleCard {
  icon: string;
  title: string;
  description: string;
  path: string;
}

@Component({
  selector: 'app-overview',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './overview.html',
  styleUrl: './overview.scss'
})
export class Overview {
  summary: SummaryCard[] = [
    { label: 'Unidades activas', value: '42', trend: '↑ 3 esta semana' },
    { label: 'Rutas en operación', value: '18', trend: '100% cubiertas' },
    { label: 'Incidentes abiertos', value: '2', trend: '↓ desde ayer' },
    { label: 'Socios registrados', value: '96', trend: '↑ 1 este mes' }
  ];

  modules: ModuleCard[] = [
    { icon: '👤', title: 'Usuarios y Roles', description: 'Administra socios, choferes y permisos de acceso.', path: 'usuarios' },
    { icon: '🚌', title: 'Flota Vehicular', description: 'Control de unidades, placas y mantenimiento.', path: 'flota' },
    { icon: '🛣️', title: 'Rutas y Frecuencias', description: 'Asignación de horarios y recorridos por unidad.', path: 'rutas' },
    { icon: '🛡️', title: 'Seguridad', description: 'Monitoreo, incidentes y alertas en tiempo real.', path: 'seguridad' },
    { icon: '📋', title: 'Administración', description: 'Documentos, finanzas y trámites de la cooperativa.', path: 'administracion' },
    { icon: '📊', title: 'Reportes', description: 'Estadísticas operativas y de seguridad exportables.', path: 'reportes' }
  ];
}