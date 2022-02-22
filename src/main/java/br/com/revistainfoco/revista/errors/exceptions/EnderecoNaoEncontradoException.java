package br.com.revistainfoco.revista.errors.exceptions;

public class EnderecoNaoEncontradoException extends RuntimeException {
    public EnderecoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
