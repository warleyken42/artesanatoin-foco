package br.com.revistainfoco.revista.errors.exceptions;

public class CidadeNaoEncontradaException extends RuntimeException {
    public CidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
