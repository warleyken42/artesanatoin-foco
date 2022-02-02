package br.com.revistainfoco.revista.errors.exceptions;

public class EstadoJaCadastradoException extends RuntimeException {
    public EstadoJaCadastradoException(String mensagem) {
        super(mensagem);
    }
}
