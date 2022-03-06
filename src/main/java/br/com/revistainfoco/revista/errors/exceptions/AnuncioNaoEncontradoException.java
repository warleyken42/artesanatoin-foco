package br.com.revistainfoco.revista.errors.exceptions;

public class AnuncioNaoEncontradoException extends RuntimeException {
    public AnuncioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
