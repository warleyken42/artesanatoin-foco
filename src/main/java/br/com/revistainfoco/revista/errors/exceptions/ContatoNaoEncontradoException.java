package br.com.revistainfoco.revista.errors.exceptions;

public class ContatoNaoEncontradoException extends RuntimeException {
    public ContatoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
