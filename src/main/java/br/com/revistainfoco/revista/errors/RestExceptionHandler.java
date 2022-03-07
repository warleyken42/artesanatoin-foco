package br.com.revistainfoco.revista.errors;

import br.com.revistainfoco.revista.errors.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    private ErrorDetail errorDetail;

    @ExceptionHandler(EstadoJaCadastradoException.class)
    public ResponseEntity<ErrorDetail> handleEstadoJaCadastradoException(EstadoJaCadastradoException estadoJaCadastradoException) {
        errorDetail = ErrorDetail.builder().mensagem(estadoJaCadastradoException.getMessage()).build();
        return new ResponseEntity<>(errorDetail, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EstadoNaoEncontradoException.class)
    public ResponseEntity<ErrorDetail> handleEstadoNaoEncontradoException(EstadoNaoEncontradoException estadoNaoEncontradoException) {
        errorDetail = ErrorDetail.builder().mensagem(estadoNaoEncontradoException.getMessage()).build();
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetail> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        FieldError fieldError = methodArgumentNotValidException.getBindingResult().getFieldError();
        if (fieldError != null) {
            errorDetail = ErrorDetail.builder().mensagem(fieldError.getDefaultMessage()).build();
        }
        return new ResponseEntity<>(errorDetail, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(CidadeJaCadastradaException.class)
    public ResponseEntity<ErrorDetail> handleCidadeJaCadastradaException(CidadeJaCadastradaException cidadeJaCadastradoException) {
        errorDetail = ErrorDetail.builder().mensagem(cidadeJaCadastradoException.getMessage()).build();
        return new ResponseEntity<>(errorDetail, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CidadeNaoEncontradaException.class)
    public ResponseEntity<ErrorDetail> handleCidadeNaoEncontradaException(CidadeNaoEncontradaException cidadeNaoEncontradoException) {
        errorDetail = ErrorDetail.builder().mensagem(cidadeNaoEncontradoException.getMessage()).build();
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AnuncianteJaCadastradoException.class)
    public ResponseEntity<ErrorDetail> handleAnuncianteJaCadastradoException(AnuncianteJaCadastradoException anuncianteJaCadastradoException) {
        errorDetail = ErrorDetail.builder().mensagem(anuncianteJaCadastradoException.getMessage()).build();
        return new ResponseEntity<>(errorDetail, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AnuncianteNaoEncontradoException.class)
    public ResponseEntity<ErrorDetail> handleAnuncianteNaoEncontradoException(AnuncianteNaoEncontradoException anuncianteNaoEncontradoException) {
        errorDetail = ErrorDetail.builder().mensagem(anuncianteNaoEncontradoException.getMessage()).build();
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ContatoNaoEncontradoException.class)
    public ResponseEntity<ErrorDetail> handleContatoNaoEncontradoException(ContatoNaoEncontradoException contatoNaoEncontradoException) {
        errorDetail = ErrorDetail.builder().mensagem(contatoNaoEncontradoException.getMessage()).build();
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AnuncioNaoEncontradoException.class)
    public ResponseEntity<ErrorDetail> handleAnuncioNaoEncontradoException(AnuncioNaoEncontradoException anuncioNaoEncontradoException) {
        errorDetail = ErrorDetail.builder().mensagem(anuncioNaoEncontradoException.getMessage()).build();
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }
}
