export interface Conductor {
  id: number;
  nombres: string;
  apellidos: string;
  cedula: string;
  numeroLicencia: string;
  tipoLicencia: string;
  fechaVencimientoLicencia: string;
  telefono: string;
  email: string;
  estado: string;
  activo: boolean;
  licenciaPorVencer: boolean;
  creadoEn: string;
  actualizadoEn: string;
}

export interface ConductorRequest {
  nombres: string;
  apellidos: string;
  cedula: string;
  numeroLicencia: string;
  tipoLicencia: string;
  fechaVencimientoLicencia: string;
  telefono: string;
  email: string;
  estado: string;
}
