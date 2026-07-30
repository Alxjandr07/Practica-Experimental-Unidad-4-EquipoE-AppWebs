import { Routes } from '@angular/router';
import { Login } from './features/auth/login/login';
import { Shell } from './features/dashboard/shell/shell';
import { Overview } from './features/dashboard/overview/overview';
import { ConductorLista } from './features/conductores/lista/conductor-lista';
import { ConductorFormulario } from './features/conductores/formulario/conductor-formulario';
import { UsuarioLista } from './features/usuarios/lista/usuario-lista';
import { UsuarioFormulario } from './features/usuarios/formulario/usuario-formulario';
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
      {
        path: 'usuarios',
        children: [
          { path: '', component: UsuarioLista },
          { path: 'nuevo', component: UsuarioFormulario },
          { path: 'editar/:id', component: UsuarioFormulario },
        ],
      },
      {
        path: 'flota',
        children: [
          { path: '', component: ConductorLista },
          { path: 'nuevo', component: ConductorFormulario },
          { path: 'editar/:id', component: ConductorFormulario },
        ],
      },
    ],
  },
];
