package br.com.revistainfoco.revista.errors.exceptions;

public class CidadeNaoEncontradoException extends RuntimeException {
    public CidadeNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
