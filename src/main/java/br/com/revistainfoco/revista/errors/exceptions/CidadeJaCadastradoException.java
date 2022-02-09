package br.com.revistainfoco.revista.errors.exceptions;

public class CidadeJaCadastradoException extends RuntimeException {
    public CidadeJaCadastradoException(String mensagem) {
        super(mensagem);
    }
}
