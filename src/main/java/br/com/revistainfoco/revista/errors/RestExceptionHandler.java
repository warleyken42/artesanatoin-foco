package br.com.revistainfoco.revista.errors;

import br.com.revistainfoco.revista.errors.exceptions.CidadeJaCadastradaException;
import br.com.revistainfoco.revista.errors.exceptions.CidadeNaoEncontradaException;
import br.com.revistainfoco.revista.errors.exceptions.EstadoJaCadastradoException;
import br.com.revistainfoco.revista.errors.exceptions.EstadoNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EstadoJaCadastradoException.class)
    public ResponseEntity<?> handleEstadoCadastradoException(EstadoJaCadastradoException estadoJaCadastradoException) {
        ErrorDetail errorDetail = ErrorDetail.builder().mensagem(estadoJaCadastradoException.getMessage()).build();
        return new ResponseEntity<>(errorDetail, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EstadoNaoEncontradoException.class)
    public ResponseEntity<?> handleEstadoNaoEncontradoException(EstadoNaoEncontradoException estadoNaoEncontradoException) {
        ErrorDetail errorDetail = ErrorDetail.builder().mensagem(estadoNaoEncontradoException.getMessage()).build();
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        ErrorDetail errorDetail = ErrorDetail.builder().mensagem(methodArgumentNotValidException.getBindingResult().getFieldError().getDefaultMessage()).build();
        return new ResponseEntity<>(errorDetail, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(CidadeJaCadastradaException.class)
    public ResponseEntity<?> handleCidadeCadastradoException(CidadeJaCadastradaException cidadeJaCadastradoException) {
        ErrorDetail errorDetail = ErrorDetail.builder().mensagem(cidadeJaCadastradoException.getMessage()).build();
        return new ResponseEntity<>(errorDetail, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleCidadeNaoEncontradoException(CidadeNaoEncontradaException cidadeNaoEncontradoException) {
        ErrorDetail errorDetail = ErrorDetail.builder().mensagem(cidadeNaoEncontradoException.getMessage()).build();
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

}
