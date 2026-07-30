import { Routes } from '@angular/router';
import { Login } from './features/auth/login/login';
import { Shell } from './features/dashboard/shell/shell';
import { Overview } from './features/dashboard/overview/overview';
import { Placeholder } from './shared/placeholder/placeholder';
import { authGuard } from './core/guards/auth-guard';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: Login },
  {
    path: 'dashboard',
    component: Shell,
    canActivate: [authGuard],
    children: [
      { path: '', component: Overview },
      { path: 'usuarios', component: Placeholder, data: { title: 'Usuarios y Roles' } },
      { path: 'flota', component: Placeholder, data: { title: 'Flota Vehicular' } },
      { path: 'rutas', component: Placeholder, data: { title: 'Rutas y Frecuencias' } },
      { path: 'seguridad', component: Placeholder, data: { title: 'Seguridad' } },
      { path: 'administracion', component: Placeholder, data: { title: 'Administración' } },
      { path: 'reportes', component: Placeholder, data: { title: 'Reportes' } }
    ]
  },
  { path: '**', redirectTo: 'login' }
];