package br.com.revistainfoco.revista.errors;

import br.com.revistainfoco.revista.errors.exceptions.EstadoJaCadastradoException;
import br.com.revistainfoco.revista.errors.exceptions.EstadoNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

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
}
