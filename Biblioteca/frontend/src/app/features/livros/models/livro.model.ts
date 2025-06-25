import { Autor } from '../../autores/models/autor.model';
import { Assunto } from '../../assuntos/models/assunto.model';

export interface Livro {
  id: number;
  titulo: string;
  editora: string;
  edicao: number;
  anoPublicacao: string;
  valor: number;
  assuntos: {
    id: number;
    descricao: string;
  }[];
  autores: {
    id: number;
    nome: string;
  }[];
}


export interface LivroRequest {
  titulo: string;
  editora: string;
  edicao: number | null;
  anoPublicacao: string;
  valor: number;
  assuntosIds: number[];
  autoresIds: number[];
}

