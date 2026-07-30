import { Routes } from '@angular/router';
import { Login } from './features/auth/login/login';
import { Shell } from './features/dashboard/shell/shell';
import { Overview } from './features/dashboard/overview/overview';
import { authGuard } from './core/guards/auth-guard';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: Login },
  {
    path: 'dashboard',
    component: Shell,
    canActivate: [authGuard],
    children: [
      { path: '', component: Overview }
    ]
  }
];