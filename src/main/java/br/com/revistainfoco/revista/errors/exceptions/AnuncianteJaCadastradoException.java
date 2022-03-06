package br.com.revistainfoco.revista.errors.exceptions;

public class AnuncianteJaCadastradoException extends RuntimeException {
    public AnuncianteJaCadastradoException(String mensagem) {
        super(mensagem);
    }
}
