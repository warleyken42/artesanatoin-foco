package br.com.revistainfoco.revista.errors.exceptions;

public class AnuncioJaCadastradoException extends RuntimeException {
    public AnuncioJaCadastradoException(String mensagem) {
        super(mensagem);
    }
}
