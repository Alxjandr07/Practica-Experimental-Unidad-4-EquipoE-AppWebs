import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { LoginRequest, LoginResponse } from '../models/auth.model';

@Injectable({
  providedIn: 'root'
})
export class Auth {
  private readonly apiUrl = `${environment.apiUrl}/auth`;

  // signal para saber en cualquier parte de la app si hay sesión activa
  currentUser = signal<LoginResponse | null>(null);

  constructor(private http: HttpClient) {}

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http
      .post<LoginResponse>(`${this.apiUrl}/login`, credentials, {
        withCredentials: true // OBLIGATORIO: para que el navegador guarde y envíe la cookie HttpOnly
      })
      .pipe(
        tap((response) => this.currentUser.set(response))
      );
  }

  logout(): Observable<void> {
    return this.http
      .post<void>(`${this.apiUrl}/logout`, {}, { withCredentials: true })
      .pipe(tap(() => this.currentUser.set(null)));
  }

  isAuthenticated(): boolean {
    return this.currentUser() !== null;
  }
}