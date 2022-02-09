package br.com.revistainfoco.revista.errors;

import br.com.revistainfoco.revista.errors.exceptions.CidadeJaCadastradoException;
import br.com.revistainfoco.revista.errors.exceptions.CidadeNaoEncontradoException;
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

    @ExceptionHandler(CidadeJaCadastradoException.class)
    public ResponseEntity<?> handleCidadeCadastradoException(CidadeJaCadastradoException cidadeJaCadastradoException) {
        ErrorDetail errorDetail = ErrorDetail.builder().mensagem(cidadeJaCadastradoException.getMessage()).build();
        return new ResponseEntity<>(errorDetail, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CidadeNaoEncontradoException.class)
    public ResponseEntity<?> handleCidadeNaoEncontradoException(CidadeNaoEncontradoException cidadeNaoEncontradoException) {
        ErrorDetail errorDetail = ErrorDetail.builder().mensagem(cidadeNaoEncontradoException.getMessage()).build();
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

}
