export interface Usuario {
    id: number;
    username: string;
    nome: string;
    email: string;
    papel: 'ADMIN' | 'USER' | string;
  }
  
  export interface UsuarioRequest {
    nome: string;
    username: string;
    email: string;
    senha: string;
    papel: string;
  }