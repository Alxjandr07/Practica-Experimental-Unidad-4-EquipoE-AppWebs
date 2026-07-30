export interface Usuario {
  id: number;
  nombre: string;
  email: string;
  rol: string;
  activo: boolean;
  creadoEn: string;
  actualizadoEn: string;
}

export interface UsuarioRequest {
  nombre: string;
  email: string;
  password: string;
  rol: string;
}
