package br.com.revistainfoco.revista.errors.exceptions;

public class EstadoNaoEncontradoException extends RuntimeException {
    public EstadoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
